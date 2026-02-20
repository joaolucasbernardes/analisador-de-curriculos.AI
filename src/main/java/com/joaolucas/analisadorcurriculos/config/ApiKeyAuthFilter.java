package com.joaolucas.analisadorcurriculos.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

public class ApiKeyAuthFilter extends GenericFilterBean {

    private static final String API_KEY_HEADER = "x-api-key";

    private final String expectedApiKey;

    public ApiKeyAuthFilter(String expectedApiKey) {
        this.expectedApiKey = expectedApiKey;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.getRequestURI().startsWith("/api/curriculos")) {
            String apiKeyHeaderValue = httpRequest.getHeader(API_KEY_HEADER);

            if (apiKeyHeaderValue == null || !apiKeyHeaderValue.equals(expectedApiKey)) {

                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType("application/json");
                httpResponse.setCharacterEncoding("UTF-8");

                String jsonErrorResponse = "{\"status\": 401, \"error\": \"Não Autorizado\", \"message\": \"Header 'x-api-key' inválido ou ausente. Acesso negado!\"}";

                PrintWriter writer = httpResponse.getWriter();
                writer.print(jsonErrorResponse);
                writer.flush();
                return;
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    apiKeyHeaderValue, null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
