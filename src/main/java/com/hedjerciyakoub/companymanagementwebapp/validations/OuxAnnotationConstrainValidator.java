package com.hedjerciyakoub.companymanagementwebapp.validations;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OuxAnnotationConstrainValidator implements ConstraintValidator<OuxAnnotation, String> {

	private boolean notBlankNotNullNotEmpty;
	private String notBlankNotNullNotEmptyMessage ;

	private String regex;
	private String regexMessage;

	private int minStringLength;
	private int maxStringLength;
	private String minMaxStringLengthMessage;

	private double min;
	private double max;
	private String minMaxMessage;


	private int minDate;
	private int maxDate;
	private String minMaxDateMessage;

	private String message;



	
	
	@Override
	public void initialize(OuxAnnotation ageLimites) {
		notBlankNotNullNotEmpty = ageLimites.notBlankNotNullNotEmpty();
		notBlankNotNullNotEmptyMessage = ageLimites.notBlankNotNullNotEmptyMessage();

		regex = ageLimites.regex();
		regexMessage=ageLimites.regexMessage();

		minStringLength = ageLimites.minStringLength();
		maxStringLength	= ageLimites.maxStringLength();
		minMaxStringLengthMessage = ageLimites.minMaxStringLengthMessage();

		min = ageLimites.min();
		max = ageLimites.max();
		minMaxMessage = ageLimites.minMaxMessage();

		minDate = ageLimites.minDate();
		maxDate = ageLimites.maxDate();
		minMaxDateMessage = ageLimites.minMaxDateMessage();

		message = ageLimites.message();

	}
	
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {		
		context.disableDefaultConstraintViolation();
		value = value.trim();

		if(notBlankNotNullNotEmpty
				&&
		  (value.isEmpty() || value==null || value.replace(" ","").equals("") )
		){
			context.buildConstraintViolationWithTemplate(notBlankNotNullNotEmptyMessage).addConstraintViolation();
			return false;
		}

		if(!regex.equals("")
				&&
		   !value.matches(regex)){
			context.buildConstraintViolationWithTemplate(regexMessage).addConstraintViolation();
			return false;
		}

		if((minStringLength!=Integer.MIN_VALUE && maxStringLength!=Integer.MAX_VALUE)
				&&
				(value.length()<minStringLength || maxStringLength<value.length())){
			context.buildConstraintViolationWithTemplate(minMaxStringLengthMessage).addConstraintViolation();
			return false;
		}


		return true;
	}

}
