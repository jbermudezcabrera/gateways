package com.jbermudezcabrera.gateways.domain;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Gateway {
  @Id
  @GeneratedValue
  private Long id;

  private String serialNumber;
  private String name;
  private String ipv4Address;

  @OneToMany(cascade = CascadeType.ALL)
  private Collection<Device> devices = new ArrayList<>();

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

  public Collection<Device> getDevices() {
    return devices;
  }

  public void addDevice(@NonNull Device device) {
    devices.add(device);
  }

  public void removeDevice(@NonNull Device device) {
    devices.remove(device);
  }
}
