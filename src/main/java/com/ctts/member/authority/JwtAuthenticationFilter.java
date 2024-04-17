package com.ctts.member.authority;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.http.HttpRequest;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
;

public class JwtAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpRequest httpRequest = (HttpRequest) servletRequest;
    }
}
