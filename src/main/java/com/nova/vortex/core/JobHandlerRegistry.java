package com.nova.vortex.core;

import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

public class JobHandlerRegistry {

    private static final Map<JobType, JobHandler> JOB_HANDLER_MAP = new HashMap<>();

    public JobHandlerRegistry() { }

    private static void register(JobType jobType, JobHandler handler) {
        JOB_HANDLER_MAP.put(jobType, handler);
    }

    public static JobHandler resolve(JobType jobType) {
        return JOB_HANDLER_MAP.get(jobType);
    }

    @PostConstruct
    public void init() {
        // Static registration of all handlers here
    }
}
