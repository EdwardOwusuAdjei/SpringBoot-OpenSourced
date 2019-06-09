package com.cointest.service;

import com.cointest.model.JwtUser;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService
{
    //TODO::Token must expire when asked to expire e.g. logout
    //values are in application.properties file
    @Value("${jwt.expire.hours}")
    private Long expireHours;

    @Value("${jwt.token.secret}")
    private String plainSecret;
    private String encodedSecret;

    @PostConstruct
    protected void init() {
        this.encodedSecret = generateEncodedSecret(this.plainSecret);
    }

    protected String generateEncodedSecret(String plainSecret)
    {
        if (StringUtils.isEmpty(plainSecret))
        {
            throw new IllegalArgumentException("JWT secret cannot be null or empty.");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return  bCryptPasswordEncoder.encode(this.plainSecret.trim());
//        return this.plainSecret;
    }

    public Date getExpirationTime()
    {
        Date now = new Date();
        Long expireInMilis = TimeUnit.HOURS.toMillis(expireHours);
        return new Date(expireInMilis + now.getTime());
    }

    protected JwtUser getUser(String encodedSecret, String token)
    {
        if(StringUtils.isEmpty(token)|| StringUtils.isEmpty(encodedSecret)) {
            return null;
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(encodedSecret)
                    .parseClaimsJws(token)
                    .getBody();
            String userid = claims.getSubject();
            String role = (String) claims.get("role");
            JwtUser securityUser = new JwtUser();
            securityUser.setUserid(userid);
            securityUser.setRole(role);
            return securityUser;
        }
        catch (Exception ex)
        {
           return  null;
        }


    }

    public JwtUser getUser(String token)
    {
        return getUser(this.encodedSecret, token);
    }

    public JwtUser getCustomer(String token)
    {
        return getUser(this.encodedSecret, token);
    }
    protected String getToken(String encodedSecret, JwtUser jwtUser)
    {
        Date now = new Date();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(String.valueOf(jwtUser.getUserid()))
                .claim("role", jwtUser.getRole())
                .setIssuedAt(now)
                .setExpiration(getExpirationTime())
                .signWith(SignatureAlgorithm.HS512, encodedSecret)
                .compact();
    }

    public String getToken(JwtUser jwtUser)
    {
        return getToken(this.encodedSecret, jwtUser);
    }
}
