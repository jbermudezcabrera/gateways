package com.jbermudezcabrera.gateways.controllers;

import com.jbermudezcabrera.gateways.services.DeviceNotFoundException;
import com.jbermudezcabrera.gateways.services.GatewayNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundAdvice {

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({GatewayNotFoundException.class, DeviceNotFoundException.class})
  String notFoundHandler(Exception ex) {
    return ex.getMessage();
  }
}
