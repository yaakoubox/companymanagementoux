package com.hedjerciyakoub.companymanagementwebapp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class AgerLimitesConstrainValidator  implements ConstraintValidator<AgeLimites , String> {


    private int ageLimitesMin;
    private int ageLimitesMax;
    private String message;
    @Override
    public void initialize(AgeLimites constraintAnnotation) {
        ageLimitesMin = constraintAnnotation.min();
        ageLimitesMax = constraintAnnotation.max();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd") ;

            Date date = format.parse(value);

            Instant instant = date.toInstant();

            ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());

            LocalDate bornDate = zone.toLocalDate();

            Period age = Period.between(bornDate, LocalDate.now());

            System.out.println(age.getYears()+"------------------");

            if(age.getYears()>ageLimitesMax || age.getYears()<ageLimitesMin) {
                return false;
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }
}
