package de.zettsystems.netzfilm.configuration;

import de.zettsystems.netzfilm.movie.application.CopyDeliveryToCopyMapper;
import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.CopyRepository;
import de.zettsystems.netzfilm.movie.domain.MovieRepository;
import de.zettsystems.netzfilm.movie.values.CopyDelivery;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope // needs to be created for every job instance to get the current resources
    public ItemReader<CopyDelivery> reader(@Value("#{jobParameters[pathToFiles]}") String pathToFiles) {
        Resource[] resources = null;
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            resources = patternResolver.getResources(pathToFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MultiResourceItemReaderBuilder<CopyDelivery>()
                .name("copyItemMultiReader")
                .resources(resources)
                .delegate(itemReader())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<CopyDelivery> itemReader() {
        return new FlatFileItemReaderBuilder<CopyDelivery>()
                .name("copyItemReader")
                .delimited()
                .names(new String[]{"title", "type"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(CopyDelivery.class);
                }})
                .build();
    }

    @Bean
    public ItemWriter<Copy> writer(CopyRepository repository) {
        return new RepositoryItemWriterBuilder<Copy>()
                .repository(repository)
                .build();
    }

    @Bean
    public Job importDeliveries(JobExecutionListenerSupport listener, Step step1) {
        return jobBuilderFactory.get("importDeliveries")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemWriter<Copy> writer, MovieRepository movieRepository) {
        return stepBuilderFactory.get("step1")
                .<CopyDelivery, Copy>chunk(10)
                .reader(reader(null))
                .processor(processor(movieRepository))
                .writer(writer)
                .build();
    }

    @Bean
    public ItemProcessor<CopyDelivery, Copy> processor(MovieRepository movieRepository) {
        return new CopyDeliveryToCopyMapper(movieRepository);
    }

}