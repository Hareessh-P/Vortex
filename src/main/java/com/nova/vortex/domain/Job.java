package com.nova.vortex.domain;

import com.nova.vortex.core.JobType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Job {

    @Id
    @GeneratedValue
    private Long id;
    private String tenantId;
    private JobType jobType;
    private String status; // SUBMITTED, QUEUED, RUNNING, SUCCEEDED, FAILED, RETRYING
    private String payload;
    private int attempts;
    private Instant createdAt;
    private Instant updatedAt;

    public abstract void submit();

}
