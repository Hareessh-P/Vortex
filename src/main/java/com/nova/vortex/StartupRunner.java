package com.nova.vortex;

import com.nova.vortex.domain.Job;
import com.nova.vortex.persistance.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class StartupRunner implements CommandLineRunner {

    private final JobRepository jobRepository;

    public StartupRunner(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void run(String... args) {
        // Insert a job
        Job job = Job.builder()
                .tenantId("test-tenant")
                .jobType("TEST")
                .status("SUBMITTED")
                .payload("dummy-payload")
                .attempts(0)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Job savedJob = jobRepository.save(job);
        System.out.println("✅ Job saved with ID: " + savedJob.getId());

        jobRepository.findById(savedJob.getId()).ifPresentOrElse(
                found -> System.out.println("📥 Verified job in DB: " + found),
                () -> System.out.println("❌ Job not found in DB")
        );
    }
}
