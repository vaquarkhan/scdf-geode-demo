package io.enfuse.pipeline.geodeprocessor.listener;

import io.enfuse.pipeline.geodeprocessor.domain.Telemetry;
import io.enfuse.pipeline.geodeprocessor.domain.TelemetryRepository;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.geode.pdx.internal.PdxInstanceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@EnableBinding(Processor.class)
public class GeodeProcessorApplicationListener {

  private static final Logger logger =
      LogManager.getLogger(GeodeProcessorApplicationListener.class);


  private TelemetryRepository telemetryRepository;

  private MeterRegistry meterRegistry;

  @Autowired
  public GeodeProcessorApplicationListener(
      TelemetryRepository telemetryRepository, MeterRegistry meterRegistry) {
    this.telemetryRepository = telemetryRepository;
    this.meterRegistry = meterRegistry;
  }

  @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
  public GenericMessage<Telemetry> handle(GenericMessage<String> incomingMessage) {
    JSONObject jsonPayload = new JSONObject(incomingMessage.getPayload());
    logger.debug("incoming payload " + jsonPayload.toString());

    String vehicleId;
    vehicleId = jsonPayload.get("VehicleId").toString();

    meterRegistry.counter("custom.metrics.for.transform").increment();

    Telemetry vehicleValue = lookup(vehicleId);


    //    vehicleValue = telemetryRepository.findById(vehicleId).toString();
    Telemetry telemetry =
        new Telemetry.Builder()
            .withVehicleId(jsonPayload.get("VehicleId").toString())
            .withLatitude(jsonPayload.get("Latitude").toString())
            .withLongitude(jsonPayload.get("Longitude").toString())
            .withSpeed(jsonPayload.get("Speed").toString())
            .withValue(vehicleValue.getValue())
            .build();

    logger.info("publishing to kafka: " + telemetry.toString());
    return new GenericMessage<>(telemetry);
  }

  @Timed(value = "Geode.speed.throughput", extraTags = {"Geode.Speed.Tag","Time"})
  protected Telemetry lookup(String key){

   return convertFromPdx(telemetryRepository.findById(key).orElse(new Telemetry.Builder().build()));
  }

  private static Telemetry convertFromPdx(Object obj) {
    if (obj instanceof Telemetry) {
      return (Telemetry) obj;
    } else if (obj instanceof PdxInstanceImpl) {
      return convertFromPdx((PdxInstanceImpl) obj);
    } else {
      return null;
    }
  }

  private static Telemetry convertFromPdx(PdxInstanceImpl pdxInstance) {
    String id = pdxInstance.getField("id").toString();
    String value = pdxInstance.getField("value").toString();
    return new Telemetry.Builder().withValue(value).withVehicleId(id).build();
  }
}
