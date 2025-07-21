package com.nova.vortex.core;


import com.nova.vortex.domain.Job;
import com.nova.vortex.persistance.JobRepository;

import java.time.Instant;

public class JobExecutor {
    private final JobRepository repository;

    public JobExecutor(JobRepository repository) {
        this.repository = repository;
    }

    public void execute(Job job) {
        JobHandler jobHandler = JobHandlerRegistry.resolve(job.getJobType());
        job.setStatus("RUNNING");
        job.setUpdatedAt(Instant.now());
        repository.save(job);

        try {
            // Simulated job execution (in real system use reflection or strategy pattern)
            Thread.sleep(2000); // Simulate work
            // handle job
            jobHandler.handle();
            job.setStatus("SUCCEEDED");
        } catch (Exception e) {
            job.setAttempts(job.getAttempts() + 1);
            job.setStatus("FAILED");
        } finally {
            job.setUpdatedAt(Instant.now());
            repository.save(job);
        }
    }
}
