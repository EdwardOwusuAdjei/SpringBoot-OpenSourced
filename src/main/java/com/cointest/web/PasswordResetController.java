package com.cointest.web;

import com.cointest.helpers.PasswordTokenMessages;
import com.cointest.json.Token;
import com.cointest.model.PasswordResetToken;
import com.cointest.model.customer.Customer;
import com.cointest.repo.PasswordTokenRepository;
import com.cointest.repo.UserRepository;
import com.cointest.service.PasswordResetService;
import com.cointest.service.UserService;
import com.cointest.service.customer.CustomerService;
import com.cointest.util.CustomErrorType;
import com.cointest.util.CustomMessageType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

/**
 * Created by edward on 6/28/17.
 */
//TODO::ceck if its email or doesnt carry any sql injectable stuff
@RestController
public class PasswordResetController {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    private CustomErrorType customErrorType;
    private CustomMessageType customMessageType;

    //Send a post request to this with {"email":"example@ecg.com"} and token will be sent.
    @RequestMapping(value = "/user/sendtoken", method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(@RequestBody Customer users) {

        Customer customer = null;
        if(users.getEmail() != null){
//           User user = userRepository.findByEmail(users.getEmail());
           customer = customerService.findByEmail(users.getEmail().trim());

        }


        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String token = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        try {
            PasswordResetToken p = passwordResetService.findById(customer.getId());
            p.getExpirydate();//needed for some weird reason..because theres some null..hasnt been assigned?
            //Existing record was found here
            //So token will be updated.
            customMessageType = new CustomMessageType();
            customMessageType.setSuccessMessage(PasswordTokenMessages.TOKEN_SENT);
            passwordTokenRepository.delete(p);//delete old token
            createPasswordResetToken(customer, token);//create new one
            return new ResponseEntity<>(customMessageType, HttpStatus.OK);
        } catch (NullPointerException ex)//This exception is thrown when a methodquery doesnt find any record..
        {
            customMessageType = new CustomMessageType();
            customMessageType.setSuccessMessage(PasswordTokenMessages.TOKEN_SENT);
            createPasswordResetToken(customer, token);
            return new ResponseEntity<>(customMessageType, HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException ex) {
            // System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        //TODO:Call which ever method which will mail;or text

    }

    //TODO:BCRYPT password here & successmessage came null
    @RequestMapping(value = "/user/changepassword", method = RequestMethod.POST)
    public ResponseEntity<?> validateTokenAndResetPassword(@RequestBody Token jsonToken) {
        String token = jsonToken.getToken();
        String newpassword = jsonToken.getNewPassword();
        String confirmPassword = jsonToken.getConfirmNewPassword();

        if (StringUtils.isEmpty(token) || token == "" || token.length() < 10)
        //Could be longer than ten even..this is to throw away silly attempts at API
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        customMessageType = new CustomMessageType();
        customErrorType = new CustomErrorType("");
        if (validateToken(token).equals("INVALID")) {
            customErrorType.setErrorMessage(PasswordTokenMessages.INVALID_TOKEN);
            return new ResponseEntity<>(customErrorType, HttpStatus.OK);
        } else if (validateToken(token).equals("EXPIRED")) {
            customErrorType.setErrorMessage(PasswordTokenMessages.EXPIRED_TOKEN);
            PasswordResetToken passwordResetToken = passwordResetService.findByToken(token);
            passwordTokenRepository.delete(passwordResetToken);
            return new ResponseEntity<>(customErrorType, HttpStatus.OK);
        }
        if (!newpassword.equals(confirmPassword)) {
            customErrorType.setErrorMessage(PasswordTokenMessages.DOES_NOT_MATCH);
            return new ResponseEntity<>(customErrorType, HttpStatus.OK);
        }
        //Gotten from validator...User Validator
        //user.getPassword().length() < 8 || user.getPassword().length() > 32
        if (newpassword.length() < 8) {
            customErrorType.setErrorMessage(PasswordTokenMessages.PASSWORD_TOO_SHORT);
            return new ResponseEntity<>(customErrorType, HttpStatus.OK);
        } else if (newpassword.length() > 32) {
            customErrorType.setErrorMessage(PasswordTokenMessages.PASSWORD_TOO_LONG);
            return new ResponseEntity<>(customErrorType, HttpStatus.OK);
        }

        Customer passwordResetUser = passwordResetService.findByToken(token).getCustomer();
        passwordResetUser.setPassword(bCryptPasswordEncoder.encode(newpassword));
        Customer saved = customerService.updateCustomer(passwordResetUser);

        if(saved == null)
        {
            PasswordResetToken passwordResetToken = passwordResetService.findByToken(token);
            passwordTokenRepository.delete(passwordResetToken);
            return new ResponseEntity<>(customMessageType, HttpStatus.NOT_MODIFIED);
        }
        PasswordResetToken passwordResetToken = passwordResetService.findByToken(token);
        passwordTokenRepository.delete(passwordResetToken);
        customMessageType.setSuccessMessage(PasswordTokenMessages.PASSWORD_CHANGE_SUCCESSFUL);

        return new ResponseEntity<>(customMessageType, HttpStatus.OK);
    }


    //My non-API methods
    public String validateToken(String token) {
        PasswordResetToken mToken = passwordResetService.findByToken(token);
        Map<String, String> retVal = new HashMap<>();

        if (mToken == null) {
            return "INVALID";
        }
        final Calendar calc = Calendar.getInstance();
        if (mToken.getExpirydate().getTime() - calc.getTime().getTime() <= 0) {
            return "EXPIRED";
        }
        return "VALID";

    }

    public void createPasswordResetToken(Customer user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(user, token);
        passwordResetService.save(myToken);
        System.out.println("Could save");

    }

    //TODO:For testing...remove this in production environment..this gives you clearer insight.
    //TODO:REMOVE FROM PRODUCTION!! TESTING ONLY.

    @RequestMapping(value = "/user/getalltokens", method = RequestMethod.GET)
    public ResponseEntity<?> listAllUsers() {

//         passwordTokenRepository.findAll();
//        if (users.isEmpty()) {
//            return new ResponseEntity(HttpStatus.NO_CONTENT);
//            // You many decide to return HttpStatus.NOT_FOUND
//        }
        return new ResponseEntity<>(passwordTokenRepository.findAll(), HttpStatus.OK);
    }


}
