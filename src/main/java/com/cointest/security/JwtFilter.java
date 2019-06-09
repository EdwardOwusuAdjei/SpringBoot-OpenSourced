package com.cointest.security;

import com.cointest.service.JwtService;
import com.cointest.model.JwtUser;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = { "/api/v1/*" })
public class JwtFilter implements Filter
{

    @Autowired
    private JwtService jwtService;

    @Value("${jwt.auth.header}")
    String authHeader;

    @Override public void init(FilterConfig filterConfig) throws ServletException  {}
    @Override public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String authHeaderVal = httpRequest.getHeader(authHeader);
        if (authHeaderVal == null)
        {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try
        {
            JwtUser jwtUser = jwtService.getUser(authHeaderVal);
            if(jwtUser != null) {
                httpRequest.setAttribute("jwtUser", jwtUser);
            }else{
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        catch(JwtException e)
        {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
        }

        chain.doFilter(httpRequest, httpResponse);
    }
}