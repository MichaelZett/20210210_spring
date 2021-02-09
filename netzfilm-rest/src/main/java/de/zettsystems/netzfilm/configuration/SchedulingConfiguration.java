package de.zettsystems.netzfilm.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertyResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulingConfiguration implements SchedulingConfigurer {
    protected static final int AWAIT_TERMINATION_SECONDS = 30;
    private final int corePoolSize;

    public SchedulingConfiguration(PropertyResolver propertyResolver) {
        this.corePoolSize = propertyResolver.getProperty("scheduling.threadexecuter.corePoolSize", Integer.class, 3);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(AWAIT_TERMINATION_SECONDS);
        threadPoolTaskScheduler.setPoolSize(corePoolSize);
        threadPoolTaskScheduler.setThreadNamePrefix("netzfilm-scheduled-");

        threadPoolTaskScheduler.initialize();

        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}
