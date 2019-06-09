package com.cointest.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity(name = "user")//introduced this so queries will be mapped when using jpql. <- edd
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)//needed for not returning null values as json response
public class User {


    private Long id;
    private String email;
    private String password;
    private String passwordConfirm;
    private String firstname;
    private String lastname;
    private String mobile;
    private String mifosaccountnumber;
    private String country;
    private String securityquestion;
    private String securityanswer;
    private Set<Role> roles;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Transient
    public String getFullName() {
        return Stream.of(firstname," ",lastname)
                .filter(s -> s != null)
                .collect(Collectors.joining()).trim();
    }

    @Transient
    public String getUsername() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMifosaccountnumber() {
        return mifosaccountnumber;
    }

    public void setMifosaccountnumber(String mifosaccountnumber) {
        this.mifosaccountnumber = mifosaccountnumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSecurityquestion() {
        return securityquestion;
    }

    public void setSecurityquestion(String securityquestion) {
        this.securityquestion = securityquestion;
    }

    public String getSecurityanswer() {
        return securityanswer;
    }

    public void setSecurityanswer(String securityanswer) {
        this.securityanswer = securityanswer;
    }

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
