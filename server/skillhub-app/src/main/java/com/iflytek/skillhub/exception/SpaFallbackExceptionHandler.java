package com.iflytek.skillhub.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * SPA history-route fallback for the frontend bundled into this jar. When the browser
 * refreshes a frontend route (e.g. /skills, /dashboard/tokens) there is no matching static
 * resource, so Spring's resource handler raises {@link NoResourceFoundException}, which the
 * JSON-enveloping {@code GlobalExceptionHandler} would otherwise turn into a 500.
 *
 * <p>GET requests outside {@code /api/} that accept HTML are forwarded to /index.html so the
 * frontend router takes over; everything else (unknown API paths, non-HTML asset requests)
 * keeps a plain 404.</p>
 *
 * <p>Note: {@code @ControllerAdvice} (not {@code @RestControllerAdvice}) because the handler
 * returns a forward view name; a rest-controller advice would serialize it as JSON.</p>
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpaFallbackExceptionHandler {

    private static final String API_PREFIX = "/api/";

    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResource(NoResourceFoundException ex, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        if (!request.getRequestURI().startsWith(API_PREFIX)
                && "GET".equals(request.getMethod())
                && accept != null
                && accept.contains("text/html")) {
            return "forward:/index.html";
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }
}
