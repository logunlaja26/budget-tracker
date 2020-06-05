package com.lyomann.budgettracker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtHandler jwtHandler;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtHandler.resolveToken((HttpServletRequest) req);
        try {
            if (token != null) {
                jwtHandler.validateToken(token);
            }
        } catch (Exception ex) {
            req.setAttribute("expired", ex.getMessage());
        } finally {
            filterChain.doFilter(req, res);
        }
    }
}
