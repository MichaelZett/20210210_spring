package de.zettsystems.netzfilm.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertyResolver;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {

    private static final int SCHEDULING_THREADEXECUTER_CORE_POOL_SIZE_DEFAULT = 3;
    private static final int SCHEDULING_THREADEXECUTER_MAX_POOL_SIZE_DEFAULT = 10;
    private static final int SCHEDULING_THREADEXECUTER_QUEUE_CAPACITY_DEFAULT = 300;
    protected static final int AWAIT_TERMINATION_SECONDS = 30;

    private final int corePoolSize;
    private final int maxPoolSize;
    private final int queueCapacity;

    public AsyncConfiguration(PropertyResolver propertyResolver) {
        this.corePoolSize = propertyResolver.getProperty("scheduling.threadexecuter.corePoolSize", Integer.class, SCHEDULING_THREADEXECUTER_CORE_POOL_SIZE_DEFAULT);
        this.maxPoolSize = propertyResolver.getProperty("scheduling.threadexecuter.maxPoolSize", Integer.class, SCHEDULING_THREADEXECUTER_MAX_POOL_SIZE_DEFAULT);
        this.queueCapacity = propertyResolver.getProperty("scheduling.threadexecuter.queueCapacity", Integer.class, SCHEDULING_THREADEXECUTER_QUEUE_CAPACITY_DEFAULT);
    }

    /**
     * Wir konfigurieren einen eigenen TaskExecutor für async Prozesse.
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(AWAIT_TERMINATION_SECONDS);
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("netzfilm-async-");
        executor.initialize();
        return executor;
    }

}
