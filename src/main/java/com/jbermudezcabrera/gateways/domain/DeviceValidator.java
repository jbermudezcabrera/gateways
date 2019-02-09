package com.jbermudezcabrera.gateways.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DeviceValidator implements Validator {
  @Override
  public boolean supports(Class<?> aClass) {
    return Device.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    Device device = (Device) o;

    if (device.getUid() <= 0) {
      errors.rejectValue("uid", "UID is required");
    }
  }
}
