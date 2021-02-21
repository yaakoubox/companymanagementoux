package com.hedjerciyakoub.companymanagementwebapp.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = AgerLimitesConstrainValidator.class )
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeLimites {

    int min() default 18;
    int max() default 60;
    String message() default "age must be older than 18 and younger than 60";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload()default{};
}
