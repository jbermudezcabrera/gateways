package com.jbermudezcabrera.gateways.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateDeviceValidator")
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
