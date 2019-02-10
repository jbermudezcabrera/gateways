package com.jbermudezcabrera.gateways.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
public class Device {
  @Id
  @GeneratedValue
  private Long id;

  private long uid;
  private String vendor;
  private ZonedDateTime created;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  private Status status;

  @JsonIgnore
  @ManyToOne
  private Gateway gateway;

  public Device() {
    created = ZonedDateTime.now();
  }

  public Device(long uid, String vendor, Status status) {
    this();
    this.uid = uid;
    this.vendor = vendor;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }

  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public ZonedDateTime getCreated() {
    return created;
  }

  public void setCreated(ZonedDateTime created) {
    this.created = created;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Gateway getGateway() {
    return gateway;
  }

  public void setGateway(Gateway gateway) {
    this.gateway = gateway;
  }
}
