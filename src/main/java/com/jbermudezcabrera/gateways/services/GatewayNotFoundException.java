package com.jbermudezcabrera.gateways.services;

public class GatewayNotFoundException extends RuntimeException {
  public GatewayNotFoundException(Long id) {
    super("Could not find gateway " + id);
  }
}
