package com.cointest.security;

import com.cointest.model.JwtUser;
import com.cointest.model.User;
import com.cointest.model.customer.Customer;
import com.cointest.service.JwtService;
import com.cointest.service.UserService;
import com.cointest.service.customer.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Service
public class FromToken {
    public static final Logger logger = LoggerFactory.getLogger(FromToken.class);
    @Value("${jwt.auth.header}")
    String authHeader;

    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
//
//    @Autowired
//    private SecurityService securityService;

    @Autowired
    private JwtService jwtService;


    public User getUser(ServletRequest request)
    {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String authHeaderVal = httpRequest.getHeader(authHeader);
        JwtUser user1 = jwtService.getUser(authHeaderVal);
        if(user1 == null)
        {
            logger.info("Fetching failed because");
            return null;
        }
        logger.info("Fetching User with  id {}", user1.getUserid());
        //Was vulnerable to delete username and register with same username..use id.
        //User user = userService.findByUsername(user1.getUserName().trim());
      //  User user = userService.findByUsername(user1.getRole());
        return  null;
    }
    //TODO::what happens when the user changes username will being authenticated,will it log in as another user?likely
    //fetch email and username together.aand match to see
    //username will be different..
    public Customer getCustomer(ServletRequest request)
    {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String authHeaderVal = httpRequest.getHeader(authHeader);
        JwtUser user1 = jwtService.getUser(authHeaderVal);
        if(user1 == null)
        {
            logger.info("Fetching failed because");
            return null;
        }
        logger.info("Fetching User with name {}", user1.getUserid());
        Customer user = customerService.findById(user1.getUserid());
        return  user;
    }


}
