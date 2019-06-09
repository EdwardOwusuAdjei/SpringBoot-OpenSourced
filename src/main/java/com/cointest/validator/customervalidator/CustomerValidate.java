package com.cointest.validator.customervalidator;

import com.cointest.model.User;
import com.cointest.model.customer.Customer;
import com.cointest.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CustomerValidate implements Validator {
    @Autowired
    private CustomerService customerService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer user = (Customer) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (StringUtils.isEmpty(user.getEmail())||user.getEmail().length() < 6 || user.getEmail().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
//        if (customerService.findByUsername(user.getUsername()) != null ) {
//            errors.rejectValue("email", "Duplicate.userForm.email");
//        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (StringUtils.isEmpty(user.getPassword())||user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

//        if (StringUtils.isEmpty(user.getPassword())||!user.getPasswordConfirm().equals(user.getPassword())) {
//            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
//        }
        if(StringUtils.isEmpty(user.getFirstname())||StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getLastname())){
            errors.rejectValue("firstname,username,lastname","NotEmpty");
        }
    }
}
