package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.CopyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class CopyDeliveryAfterProcess extends JobExecutionListenerSupport {
    private static final Logger LOG = LoggerFactory.getLogger(CopyDeliveryAfterProcess.class);
    private final CopyRepository copyRepository;

    public CopyDeliveryAfterProcess(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOG.info("!!! JOB FINISHED! Time to verify the results");

            final List<Copy> all = copyRepository.findAll();
            LOG.info("Found copies: " + all);
            moveFileToArchive();
        }
    }

    private void moveFileToArchive() {
        try {
            ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = patternResolver.getResources("file:c:/temp/*_delivery.csv");
            for (Resource r : resources) {
                final File file = r.getFile();
                file.renameTo(new File("c:/temp/archived/" + file.getName()));
                LOG.info("movedFile");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
