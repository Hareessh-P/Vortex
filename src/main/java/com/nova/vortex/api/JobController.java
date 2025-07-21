package com.nova.vortex.api;

import com.nova.vortex.api.dto.JobRequest;
import com.nova.vortex.domain.Job;
import com.nova.vortex.impl.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<Long> submitJob(@RequestBody JobRequest request) {
        Long id = jobService.submitJob(request);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable Long id) {
        return ResponseEntity.of(jobService.getJob(id));
    }
}
