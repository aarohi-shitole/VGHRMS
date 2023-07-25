package com.techvg.hrms.service;

import com.techvg.hrms.domain.FormValidator;
import com.techvg.hrms.repository.FormValidatorRepository;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    @Autowired
    private FormValidatorRepository formValidatorRepository;

    public List<String> validateField(String formName, String fieldName, Object value) {
        List<FormValidator> validators = formValidatorRepository.findByFormNameAndFieldNameAndStatus(formName, fieldName, "A");

        List<String> errorMessages = new ArrayList<String>();
        for (FormValidator validator : validators) {
            try {
                if ("REQUIRED".equalsIgnoreCase(validator.getType())) {
                    if (value == null || value.toString().isEmpty()) {
                        errorMessages.add(fieldName + " is required in form " + formName + ",");
                    }
                } else if ("minlength".equalsIgnoreCase(validator.getType())) {
                    if (((String) value).length() < Long.parseLong(validator.getValue())) {
                        errorMessages.add(
                            fieldName + " length is less than minlength " + validator.getValue() + " in form " + formName + ","
                        );
                    }
                } else if ("maxlength".equalsIgnoreCase(validator.getType())) {
                    if (((String) value).length() > Long.parseLong(validator.getValue())) {
                        errorMessages.add(fieldName + " length exceeds maxlength " + validator.getValue() + " in form " + formName + ",");
                    }
                } else if ("pattern".equalsIgnoreCase(validator.getType())) {
                    Pattern pattern = Pattern.compile(validator.getValue());
                    if (!pattern.matcher((CharSequence) value).matches()) {
                        errorMessages.add(fieldName + " does not match pattern " + validator.getValue() + " in form " + formName);
                    }
                }
            } catch (Exception e) {
                // Handle any exception that occur during validation
                errorMessages.add("Error occurred while validating " + fieldName + " in form " + formName);
            }
        }

        //        System.out.println("!!!!!!!!!!!!!!!!!!!errorMessages: " + errorMessages.toString());
        //        if (!errorMessages.isEmpty()) {
        //            throw new BadRequestAlertException(errorMessages.toString(), "", "");
        //        }
        return errorMessages;
    }

    public void validateAndThrowException(String formName, String fieldName, Object value) {
        List<String> errorMessages = validateField(formName, fieldName, value);
        System.out.println("!!!!!!!!!!!!!!!!!!!errorMessages: " + errorMessages.toString());
        if (!errorMessages.isEmpty()) {
            throw new BadRequestAlertException(errorMessages.toString(), "", "");
        }
    }

    public void validateMethod(Object dtoClass) {
        Field[] fields = dtoClass.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(dtoClass);
                String dtoName = dtoClass.getClass().getSimpleName();
                this.validateAndThrowException(dtoName, field.getName(), value);
            } catch (ValidationException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
