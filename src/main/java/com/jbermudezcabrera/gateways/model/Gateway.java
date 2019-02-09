package com.jbermudezcabrera.gateways.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Gateway {
  @Id
  @GeneratedValue
  private Long id;

  @NotEmpty
  private String serialNumber;

  private String name;

  @IPv4Address
  private String ipv4Address;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "gateway")
  private List<Device> devices = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIpv4Address() {
    return ipv4Address;
  }

  public void setIpv4Address(String ipv4Address) {
    this.ipv4Address = ipv4Address;
  }

  public List<Device> getDevices() {
    return devices;
  }

  public void addDevice(@NonNull Device device) {
    devices.add(device);
    device.setGateway(this);
  }

  public void removeDevice(@NonNull Device device) {
    devices.remove(device);
    device.setGateway(null);
  }
}
