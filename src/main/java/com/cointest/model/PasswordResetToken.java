package com.cointest.model;

import com.cointest.model.customer.Customer;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by edward on 6/28/17.
 */
@Entity(name = "passreset")
@Table(name = "passreset")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)//needed for not returning null values as json response
public class PasswordResetToken {


    private static final int EXPIRATION = 1;//TODO:60 mintues too long? 60*1



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String token;
//
//    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "user_id")
//    private User user;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @OneToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userid")
    private Customer customer;
    private Date expiryDate;
    public PasswordResetToken()
    {
        super();
    }

    public PasswordResetToken(Customer customer, String token) {
        this.token = token;
        this.customer = customer;
        this.expiryDate = calculateExpiry(EXPIRATION);
    }

    private Date calculateExpiry(final int expiration) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiration);
        return new Date(cal.getTime().getTime());
    }

    public Date getExpirydate() {
        return expiryDate;
    }

    public void setExpirydate(Date expirydate) {
        this.expiryDate = expirydate;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }


}

