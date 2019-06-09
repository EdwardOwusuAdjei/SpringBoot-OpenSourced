package com.cointest.web.Customer;

import com.cointest.helpers.UserMessages;
import com.cointest.model.JwtUser;
import com.cointest.model.customer.Customer;
import com.cointest.security.FromToken;
import com.cointest.service.JwtService;
import com.cointest.service.customer.CustomerService;
import com.cointest.util.CustomErrorType;
import com.cointest.util.CustomMessageType;
import com.cointest.validator.customervalidator.CustomerValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/api/")
public class CustomerController {

    //TODO::make sure no user input reach the query builder
    @Autowired
    private CustomErrorType customErrorType;
    @Autowired
    private CustomerValidate customerValidate;
    @Autowired
    private FromToken fromToken;
    @Autowired
    private CustomMessageType successMessage;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtService jwtService;

    @RequestMapping(value = "/createuser", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer,BindingResult bindingResult) {
        customerValidate.validate(customer, bindingResult);

        if (bindingResult.hasErrors()) {
           // logger.error("Unable to create. There are errors", bindingResult.toString());
            customErrorType.setErrorMessage(UserMessages.missingImportantParameter);
            return new ResponseEntity<>(customErrorType, HttpStatus.NOT_MODIFIED);
        }
        //be specific on which one exist
        if(customerService.isCustomerExist(customer)|| customerService.isUsernameExist(customer))
        {
            customErrorType.setErrorMessage(UserMessages.alreadyExist);
            return new ResponseEntity<>(customErrorType, HttpStatus.CONFLICT);
        }
        customer.setId(UUID.randomUUID().toString());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        Customer save = customerService.save(customer);
        if(save == null)
        {
            return  new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        successMessage.setSuccessMessage(UserMessages.accountCreated);
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @Async
    @RequestMapping(value = "/deleteaccount", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> deleteUser(@RequestBody Customer customer) {

        return null;
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> login(@RequestBody Customer login) {
        Map<String,String> returnValue  = new HashMap<>();
        String jwtToken = "";
        if (login.getUsername() == null || login.getPassword() == null) {
            return new ResponseEntity(new CustomErrorType(UserMessages.emailOrPasswordMessage), HttpStatus.BAD_REQUEST);
        }
        String username = login.getUsername().trim();
        String password = login.getPassword().trim();

        Customer user = customerService.findByUsername(username);

        if (user == null) {
            return  new ResponseEntity(new CustomErrorType(UserMessages.emailOrPasswordMessage), HttpStatus.BAD_REQUEST);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwd = user.getPassword().trim();
        boolean matches = bCryptPasswordEncoder.matches(password, pwd);
        if(!matches)
        {
            return new ResponseEntity<>(new CustomErrorType(UserMessages.emailOrPasswordMessage), HttpStatus.BAD_REQUEST);
        }
        JwtUser jwtUser = new JwtUser(user.getId(),"user");
        if(jwtService.getToken(jwtUser) != null)
        {
            returnValue.put("Token",jwtService.getToken(jwtUser));
            returnValue.put("Expiry",jwtService.getExpirationTime().toString());
            return new ResponseEntity(returnValue,HttpStatus.OK);
        }
        return new ResponseEntity<>(UserMessages.emailOrPasswordMessage,HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/userinfo", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> userInfo(ServletRequest request) {
        Customer customer = fromToken.getCustomer(request);
        //user.setRoles(null);//instead of null user1.getRole can be used to set role
        if (customer == null) {
           // logger.error("User with name {} not found.", user.getFullName());
            return new ResponseEntity(new CustomErrorType("Details not found"), HttpStatus.NOT_FOUND);
        }
        //Things nullified so they wont be sent in json.check class jackson annotation for notnull
        //  user.setPassword(null);
//        user.setId(null);
//        user.setPasswordConfirm(null);
//        user.setSecurityanswer(null);
//        user.setSecurityquestion(null);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Async
    @RequestMapping(value = "/updateuser", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> updateUser(@RequestBody Customer customer) {

        return null;
    }

}
