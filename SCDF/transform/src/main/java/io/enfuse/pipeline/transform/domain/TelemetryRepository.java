package io.enfuse.pipeline.transform.domain;

import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.data.gemfire.repository.GemfireRepository;

@Region("telemetryRegion")
public interface TelemetryRepository extends GemfireRepository<Telemetry, String> {}
