package com.amwell.commons;

import java.util.List;

import org.springframework.util.CollectionUtils;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

public class ValidateHelper {

	private ValidateHelper(){
		
	}
	
	public static List<ConstraintViolation> validate(Object validatedObject){
		if(null==validatedObject){
			throw new IllegalArgumentException("validatedObject can't be null.");
		}
		Validator validator = new Validator();
		return validator.validate(validatedObject);
	}
	
	public static boolean isValid(Object validatedObject){
		return CollectionUtils.isEmpty(validate(validatedObject));
	}
	
	public static boolean isInvalid(Object validatedObject){
		return !isValid(validatedObject);
	}
}
