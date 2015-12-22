package com.leanstacks.ws.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * The SimpleCORSFilter class is a standard web Filter which intercepts all
 * inbound HTTP requests. The filter sets several Headers on the HTTP response
 * which inform a browser that the web services handle Cross-Origin requests.
 * 
 * @author Matt Warman
 */
@Component
public class SimpleCORSFilter extends GenericFilterBean {

    /**
     * The Logger for this class.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        logger.info("> doFilter");

        HttpServletResponse response = (HttpServletResponse) resp;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "DELETE, GET, OPTIONS, PATCH, POST, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "x-requested-with, content-type");

        chain.doFilter(req, resp);
        logger.info("< doFilter");
    }

}
