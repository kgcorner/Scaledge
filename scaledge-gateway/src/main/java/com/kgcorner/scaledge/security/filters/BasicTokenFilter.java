package com.kgcorner.scaledge.security.filters;

/*
Description : Filter for Basic Token
Author: kumar
Created on : 1/6/19
*/

import com.kgcorner.scaledge.security.tokens.BasicToken;
import com.kgcorner.scaledge.util.Strings;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

public class BasicTokenFilter extends AbstractFilter {

    private static final Logger LOGGER = Logger.getLogger(BasicTokenFilter.class);

    public static final String AUTHORIZATION = "Authorization";
    public static final String BASIC = "basic ";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String auth = ((HttpServletRequest) servletRequest).getHeader(AUTHORIZATION);
        if(Strings.isNullOrEmpty(auth) || !auth.toLowerCase().startsWith(BASIC)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        try {
            auth = auth.substring(BASIC.length() - 1);
            auth = new String(Base64.getDecoder().decode(auth.getBytes()));
            String[] values = auth.split(":");
            String userName = values[0];
            String password = values[1];
            BasicToken basicToken = new BasicToken(userName, password);
            SecurityContextHolder.getContext().setAuthentication(basicToken);
        } catch (Exception x) {
            LOGGER.error(x.getMessage());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}