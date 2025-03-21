package com.studyplanner.taskservice.SecurityUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPointUtil implements AuthenticationEntryPoint {

    public static Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPointUtil.class);

   @Override
      public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
          logger.info("inside Auth entry point...");
          response.setContentType("application/json");
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getOutputStream().println("{ \"message\": \"Unauthorized access: " + authException.getMessage() + "\" }");
      }
}