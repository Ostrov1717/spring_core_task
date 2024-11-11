package org.example.gym.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@WebFilter(urlPatterns = "/*")
@Slf4j
public class RequestLoggingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // Получаем transactionId из заголовков (или создаем новый, если его нет)
        String transactionId = httpRequest.getHeader("transactionId");
        if (transactionId == null) {
            transactionId = java.util.UUID.randomUUID().toString(); // Генерация нового ID
        }
        // Устанавливаем transactionId в MDC
        MDC.put("transactionId", transactionId);
        log.info("Incoming request: {} {} with transactionId: {}", httpRequest.getMethod(), httpRequest.getRequestURI(), transactionId);
        try {
            chain.doFilter(request, response);
        }
        finally {
            log.info("Response for request: {} {} with transactionId: {} status: {}", httpRequest.getMethod(), httpRequest.getRequestURI(), transactionId, httpResponse.getStatus());
            MDC.remove("transactionId");
        }
    }

    @Override
    public void destroy() {
    }
}
