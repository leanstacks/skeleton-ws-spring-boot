package com.leanstacks.ws.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The SimpleCORSFilter class is a standard web Filter which intercepts all
 * inbound HTTP requests. The filter sets several Headers on the HTTP response
 * which inform a browser that the web services handle Cross-Origin requests.
 * 
 * @author Matt Warman
 */
@Component
public class SimpleCORSFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        logger.info("> doFilter");

        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "DELETE, GET, OPTIONS, PATCH, POST, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "x-requested-with, content-type");

        logger.info("< doFilter");
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
