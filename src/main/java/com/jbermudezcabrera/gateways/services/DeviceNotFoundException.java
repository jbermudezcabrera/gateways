package com.jbermudezcabrera.gateways.services;

public class DeviceNotFoundException extends RuntimeException {
  public DeviceNotFoundException(Long id, Long gatewayId) {
    super(String.format("Could not find device %d in gateway %d", id, gatewayId));
  }
}
