package com.nova.vortex.impl;

import com.nova.vortex.api.dto.JobRequest;
import com.nova.vortex.core.JobScheduler;
import com.nova.vortex.domain.Job;
import com.nova.vortex.persistance.JobRepository;

import java.time.Instant;
import java.util.Optional;

public class JobService {
    private final JobRepository repository;
    private final JobScheduler scheduler;

    public JobService(JobRepository repository, JobScheduler scheduler) {
        this.repository = repository;
        this.scheduler = scheduler;
    }

    public Long submitJob(JobRequest request) {
        Job job = Job.builder()
                .tenantId(request.tenantId)
                .jobType(request.jobType)
                .payload(request.payload)
                .status("SUBMITTED")
                .attempts(0)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Job saved = repository.save(job);
        scheduler.enqueue(saved);
        return saved.getId();
    }


    public Optional<Job> getJob(Long id) {
        return repository.findById(id);
    }
}
