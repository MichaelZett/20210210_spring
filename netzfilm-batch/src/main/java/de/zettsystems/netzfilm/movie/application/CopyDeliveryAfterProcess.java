package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.CopyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

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
        }
    }
}
