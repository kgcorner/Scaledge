package com.kgcorner.scaledge.security.filters;

/*
Description : <Write class Description>
Author: kumar
Created on : 1/6/19
*/

import javax.servlet.*;
import java.io.IOException;

public abstract class AbstractFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    abstract public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException;

    @Override
    public void destroy() {

    }
}