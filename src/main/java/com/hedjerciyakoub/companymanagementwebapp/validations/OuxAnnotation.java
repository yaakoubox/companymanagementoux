package com.hedjerciyakoub.companymanagementwebapp.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = OuxAnnotationConstrainValidator.class )
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OuxAnnotation {

	 boolean notBlankNotNullNotEmpty();
	 String notBlankNotNullNotEmptyMessage() default "";

	 String regex() default "" ;
	 String regexMessage() default "";

	 int minStringLength() default Integer.MIN_VALUE;
	 int maxStringLength() default Integer.MAX_VALUE;
	 String minMaxStringLengthMessage() default "";



	 double min() default Double.MIN_VALUE;
	 double max() default  Double.MAX_VALUE;
	 String minMaxMessage() default "";

	 int minDate() default Integer.MIN_VALUE;
	 int maxDate() default Integer.MAX_VALUE;
	 String minMaxDateMessage() default "";

	 String message() default "";



	 Class<?>[] groups() default{};
	 Class<? extends Payload>[] payload()default{};
	
}
