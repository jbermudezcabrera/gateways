package com.jbermudezcabrera.gateways.model;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class GatewayValidator implements Validator {
  @Override
  public boolean supports(Class<?> aClass) {
    return Gateway.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serialNumber", "Serial Number is required");

    Gateway gateway = (Gateway) o;

    boolean isEmpty = StringUtils.isEmpty(gateway.getIpv4Address());
    boolean validIPv4 = InetAddressValidator.getInstance().isValidInet4Address(gateway.getIpv4Address());

    if (!isEmpty && !validIPv4) {
      errors.rejectValue("ipv4Address", "Invalid IPv4 address");
    }
  }
}
