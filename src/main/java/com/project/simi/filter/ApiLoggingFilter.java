package com.project.simi.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;


@Component
@RequiredArgsConstructor
@Slf4j
public class ApiLoggingFilter implements Filter {
    private final Environment environment;

    // TODO: 2024-05-06 18:09
    private final String[] IP_HEADER_CANDIDATES = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(
        ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        ContentCachingRequestWrapper wrappedRequest =
            new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper wrappedResponse =
            new ContentCachingResponseWrapper(httpServletResponse);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        try {
            handleRequest(wrappedRequest);
            handleResponse(wrappedResponse);

            int httpStatus = httpServletResponse.getStatus();
            log.info(
                "{} {} {}",
                httpStatus,
                wrappedRequest.getMethod(),
                getRequestPath(httpServletRequest));
        } finally {
            MDC.clear();
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void handleRequest(ContentCachingRequestWrapper wrappedRequest) throws IOException {
        String requestBody =
            new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
        if (requestBody.isBlank()) {
            requestBody =
                StreamUtils.copyToString(
                    wrappedRequest.getInputStream(), StandardCharsets.UTF_8);
        }

        buildMDC(wrappedRequest, requestBody);
    }

    private void handleResponse(ContentCachingResponseWrapper wrappedResponse) {
        String responseBody =
            new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

        buildMDC(wrappedResponse, responseBody);
    }

    private void buildMDC(HttpServletRequest request, String requestBody)
        throws UnknownHostException {
        MDC.put("server_ip_address", InetAddress.getLocalHost().getHostAddress());
        MDC.put("client_ip_address", getClientIpAddress(request));
        MDC.put("environment", getCurrentEnvironment());
        MDC.put("request_method", request.getMethod());
        MDC.put("request_api", request.getRequestURI());
        MDC.put("request_path", getRequestPath(request));
        MDC.put("request_body", requestBody);
    }

    private void buildMDC(HttpServletResponse response, String responseBody) {
        MDC.put("http_status", String.valueOf(response.getStatus()));
        MDC.put("response_body", responseBody);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0];
            }
        }

        return request.getRemoteAddr();
    }

    private String getCurrentEnvironment() {
        if (environment.getActiveProfiles().length != 0) {
            return (environment.getActiveProfiles()[0]);
        }

        return environment.getDefaultProfiles()[0];
    }

    private String getRequestPath(HttpServletRequest request) {
        return request.getScheme()
            + "://"
            + (request).getHeader("Host")
            + request.getRequestURI()
            + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }
}
