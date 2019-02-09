package com.jbermudezcabrera.gateways.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = IPv4AddressValidator.class)
public @interface IPv4Address {
  String message() default "Not a valid IPv4 address";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
