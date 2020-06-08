package com.sda.validator;

import com.sda.entity.User;
import com.sda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class EmailExistValidator implements ConstraintValidator<EmailExistConstrains, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(EmailExistConstrains constraintAnnotation) {

    }

    @Override
    public boolean isValid(String emailField, ConstraintValidatorContext constraintValidatorContext) {
        User foundUser = userRepository.findUsersByEmail(emailField);
        if (foundUser != null) {
            return false;
        } else {
            return true;
        }
    }
}
