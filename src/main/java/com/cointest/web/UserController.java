package com.cointest.web;

import com.cointest.helpers.UserMessages;
import com.cointest.model.User;
import com.cointest.security.FromToken;
import com.cointest.service.JwtService;
import com.cointest.service.SecurityService;
import com.cointest.service.UserService;
import com.cointest.util.CustomErrorType;
import com.cointest.util.CustomMessageType;
import com.cointest.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private CustomMessageType successMessage;
    @Value("${jwt.auth.header}")
    String authHeader;
    @Autowired
    private FromToken fromToken;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private ApplicationContext applicationContext;






    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> registration(@RequestBody User user,BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error("Unable to create. There are errors", bindingResult.toString());
            return new ResponseEntity<>(new CustomErrorType("Unable to create. A User with account"), HttpStatus.CONFLICT);
        }
        user.setFirstname(user.getFirstname().trim());
        user.setPassword(user.getPassword().trim());
        user.setEmail(user.getEmail().trim());
        user.setMobile(user.getMobile().trim());
        userService.save(user);
        successMessage = new CustomMessageType();
        successMessage.setSuccessMessage(UserMessages.accountCreated);
        // TODO::send email to the user confirming registration, send sms verification code which is randomly generated
        return new ResponseEntity<>(successMessage,HttpStatus.CREATED);

    }


    @RequestMapping(value = "/api/user/details", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getUser(ServletRequest request) {
        User user = fromToken.getUser(request);
        user.setRoles(null);//instead of null user1.getRole can be used to set role
        if (user == null) {
            logger.error("User with name {} not found.", user.getFullName());
            return new ResponseEntity(new CustomErrorType("Details not found"), HttpStatus.NOT_FOUND);
        }
        //Things nullified so they wont be sent in json.check class jackson annotation for notnull
      //  user.setPassword(null);
        user.setId(null);
        user.setPasswordConfirm(null);
        user.setSecurityanswer(null);
        user.setSecurityquestion(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //TODO::A better way to update user details // Use validator

    @RequestMapping(value = "/api/user/details", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> updateUser(ServletRequest request,@RequestBody User user) {

        User currentUser = fromToken.getUser(request);
        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.",currentUser.getEmail());
            return new ResponseEntity(new CustomErrorType(UserMessages.updateError),HttpStatus.NOT_MODIFIED);
        }

        currentUser.setFirstname(user.getFirstname());
        currentUser.setLastname(user.getLastname());
        currentUser.setCountry(user.getCountry());
        currentUser.setSecurityquestion(user.getSecurityquestion());
        currentUser.setSecurityanswer(user.getSecurityanswer());
        currentUser.setPasswordConfirm(user.getPasswordConfirm());
        currentUser.setPassword(user.getPassword());
        currentUser.setEmail(user.getEmail());
        currentUser.setMifosaccountnumber(user.getMifosaccountnumber());

        User user1 = userService.updateUser(currentUser);
        if(user1 != null)
        {
            successMessage = new CustomMessageType();
            successMessage.setSuccessMessage(UserMessages.updateSuccessful);
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        }
        return new ResponseEntity<>(new CustomErrorType(UserMessages.updateError),HttpStatus.NOT_MODIFIED);


    }


    //TODO::when user logs in twice or more all tokens remain valid...

    //Same error message so that people dont use it to find existence of email or wrong password
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> login(@RequestBody User login) {
        Map<String,String> returnValue  = new HashMap<>();
        String jwtToken = "";
        if (login.getEmail() == null || login.getPassword() == null) {
            return new ResponseEntity(new CustomErrorType(UserMessages.emailOrPasswordMessage), HttpStatus.BAD_REQUEST);
        }
        String email = login.getEmail();
        String password = login.getPassword().trim();
        System.out.println(password);

        User user = userService.findByUsername(email);

        if (user == null) {
            return  new ResponseEntity(new CustomErrorType(UserMessages.emailOrPasswordMessage), HttpStatus.BAD_REQUEST);
        }

        String pwd = user.getPassword().trim();
        boolean matches = bCryptPasswordEncoder.matches(password, pwd);
        if(!matches)
        {
           return new ResponseEntity<>(new CustomErrorType(UserMessages.emailOrPasswordMessage), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UserMessages.emailOrPasswordMessage,HttpStatus.BAD_REQUEST);
    }

}
