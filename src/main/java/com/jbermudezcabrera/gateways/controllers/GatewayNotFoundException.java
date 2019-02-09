package com.jbermudezcabrera.gateways.controllers;

public class GatewayNotFoundException extends RuntimeException {
  public GatewayNotFoundException(Long id) {
    super("Could not find gateway " + id);
  }
}
