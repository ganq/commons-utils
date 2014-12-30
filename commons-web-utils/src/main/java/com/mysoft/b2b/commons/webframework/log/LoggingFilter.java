package com.mysoft.b2b.commons.webframework.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by luis.lau on 2014/7/24.
 */
public class LoggingFilter implements Filter {

    private static Log logger = LogFactory.getLog(LoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (RuntimeException ex) {
            logger.error("Unknown error.", ex);
            throw ex;
        }
    }

    @Override
    public void destroy() {

    }
}
