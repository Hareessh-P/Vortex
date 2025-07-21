package com.nova.vortex.core;

import com.nova.vortex.domain.Job;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class JobScheduler {
    private final BlockingQueue<Job> queue = new LinkedBlockingQueue<>();
    private final JobExecutor executor;

    public JobScheduler(JobExecutor executor) {
        this.executor = executor;
        startScheduler();
    }

    public void enqueue(Job job) {
        job.setStatus("QUEUED");
        job.setUpdatedAt(Instant.now());
        queue.offer(job);
    }

    private void startScheduler() {
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                Job job = queue.take();
                executor.execute(job);
            }
        });
    }
}
