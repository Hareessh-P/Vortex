package com.nova.vortex.api.dto;

import com.nova.vortex.core.JobType;

public class JobRequest {
    public String tenantId;
    public JobType jobType;
    public String payload;
}
