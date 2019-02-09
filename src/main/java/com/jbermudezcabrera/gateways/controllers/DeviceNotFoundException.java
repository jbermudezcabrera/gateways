package com.jbermudezcabrera.gateways.controllers;

public class DeviceNotFoundException extends RuntimeException {
  public DeviceNotFoundException(Long id) {
    super("Could not find device " + id);
  }
}
