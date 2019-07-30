package io.enfuse.pipeline.geodesink.domain;

import org.springframework.data.gemfire.mapping.annotation.Region;

@Region("transmissionRegion")
public class Transmission {
  String vehicleId;
  // LOC_Latitude
  String latitude;
  // LOC_Longitude
  String longitude;
  // LOC_Speed
  String speed;

  private Transmission(Builder builder) {
    setVehicleId(builder.vehicleId);
    setLatitude(builder.latitude);
    setLongitude(builder.longitude);
    setSpeed(builder.speed);
  }

  public String getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getSpeed() {
    return speed;
  }

  public void setSpeed(String speed) {
    this.speed = speed;
  }

  public static final class Builder {
    private String vehicleId;
    private String latitude;
    private String longitude;
    private String speed;

    public Builder() {}

    public Builder withVehicleId(String val) {
      vehicleId = val;
      return this;
    }

    public Builder withLatitude(String val) {
      latitude = val;
      return this;
    }

    public Builder withLongitude(String val) {
      longitude = val;
      return this;
    }

    public Builder withSpeed(String val) {
      speed = val;
      return this;
    }

    public Transmission build() {
      return new Transmission(this);
    }
  }

  @Override
  public String toString() {
    return "Transmission{"
        + "vehicleId='"
        + vehicleId
        + '\''
        + ", latitude='"
        + latitude
        + '\''
        + ", longitude='"
        + longitude
        + '\''
        + ", speed='"
        + speed
        + '\''
        + '}';
  }
}