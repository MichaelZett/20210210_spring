package de.zettsystems.netzfilm.order.adapter;

import de.zettsystems.netzfilm.movie.domain.CopyType;
import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.movie.domain.MovieRepository;
import de.zettsystems.netzfilm.movie.values.CopyToOrder;
import de.zettsystems.netzfilm.order.application.OrderSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.event.EventListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class OrderSenderImpl implements OrderSender {
    private static final Logger LOG = LoggerFactory.getLogger(OrderSenderImpl.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final List<String> ORDERS = new LinkedList<>();
    private final String FILE_NAME = "deliveries/%s_delivery.csv";

    private final MovieRepository movieRepository;
    private final JobLauncher jobLauncher;
    private final Job importDeliveries;

    private final JmsTemplate jmsTemplate;

    public OrderSenderImpl(MovieRepository movieRepository, JobLauncher jobLauncher, Job importDeliveries, JmsTemplate jmsTemplate) {
        this.movieRepository = movieRepository;
        this.jobLauncher = jobLauncher;
        this.importDeliveries = importDeliveries;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendCopyOrder(String title, LocalDate releaseDate, CopyType type) {
        Map<String, Object> map = Map.of("title", title, "releaseDate", releaseDate.toString(), "type", type.toString());
        jmsTemplate.convertAndSend("order", map);
        LOG.info("Sent order message for title {}.", title);
    }

    @Async
    @EventListener
    public void onCopyOrder(CopyToOrder event) {
        final Movie movie = movieRepository.findById(event.getMovieId()).orElseThrow();
        sendCopyOrder(movie.getTitle(), movie.getReleaseDate(), CopyType.valueOf(event.getType()));
        ORDERS.add(movie.getTitle() + "," + event.getType());
        synchronized (this) {
            if (ORDERS.size() > 3) {
                fakeDelivery();
                ORDERS.clear();
            }
        }
    }

    private void fakeDelivery() {
        LOG.info("Time for a fake delivery.");
        Path newFilePath = Paths.get(String.format(FILE_NAME, LocalDateTime.now().format(FORMATTER)));
        try {
            Files.createFile(newFilePath);
            for (String o : ORDERS) {
                Files.writeString(newFilePath, o + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            }
            importJob();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void importJob() {
        // make unique JobParameters so now instance of job can be started
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("source", "Fake Delivery")
                .addString("pathToFiles", "/deliveries/*_delivery.csv")
                .addDate("date", Date.from(Instant.now())) // needed for uniqueness
                .toJobParameters();
        try {
            LOG.info("Look for a delivery.");
            JobExecution ex = jobLauncher.run(importDeliveries, jobParameters);
            LOG.info(String.format("Execution status-----> %s, Execution Start Time ------> %s, Execution End Time %s", ex.getStatus(), ex.getStartTime(), ex.getEndTime()));
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}
