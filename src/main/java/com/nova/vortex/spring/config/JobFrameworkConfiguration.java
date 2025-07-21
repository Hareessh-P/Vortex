package com.nova.vortex.spring.config;

import com.nova.vortex.api.JobController;
import com.nova.vortex.core.JobExecutor;
import com.nova.vortex.core.JobHandlerRegistry;
import com.nova.vortex.core.JobScheduler;
import com.nova.vortex.impl.JobService;
import com.nova.vortex.persistance.JobRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

@Configuration
public class JobFrameworkConfiguration {

    @Bean
    public JobRepository jobRepository(EntityManagerFactory factory) {
        JpaRepositoryFactory repoFactory = new JpaRepositoryFactory(factory.createEntityManager());
        return repoFactory.getRepository(JobRepository.class);
    }

    @Bean
    public JobExecutor jobExecutor(JobRepository jobRepository) {
        return new JobExecutor(jobRepository);
    }

    @Bean
    public JobScheduler jobScheduler(JobExecutor jobExecutor) {
        return new JobScheduler(jobExecutor);
    }

    @Bean
    public JobService jobService(JobRepository jobRepository, JobScheduler jobScheduler) {
        return new JobService(jobRepository, jobScheduler);
    }

    @Bean
    public JobController jobController(JobService jobService) {
        return new JobController(jobService);
    }

    @Bean(initMethod = "init")
    public JobHandlerRegistry jobHandlerRegistry() {
        return new JobHandlerRegistry();
    }
}

