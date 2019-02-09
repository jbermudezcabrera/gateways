package com.jbermudezcabrera.gateways.model;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class IPv4AddressValidator implements ConstraintValidator<IPv4Address, String> {

  @Override
  public boolean isValid(String address, ConstraintValidatorContext constraintValidatorContext) {
    return StringUtils.isEmpty(address) || InetAddressValidator.getInstance().isValidInet4Address(address);
  }
}
