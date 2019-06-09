package com.cointest.model;

public class JwtUser {

    private String userid;
    private String role;

    public JwtUser(String userid, String role)
   {
       this.userid = userid;
       this.role = role;
   }

   public  JwtUser(){}
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

