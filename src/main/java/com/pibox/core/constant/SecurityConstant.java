package com.pibox.core.constant;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000; // 5 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String COMPANY = "Pibox";
    public static final String ADMINISTRATION = "User Management CRM";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to login to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = { "/api/v1/user/login", "/api/v1/user/register", "/api/v1/user/reset-password/*", "/user/image/**",
            "/api-docs", "/swagger-config/**", "/v3/api-docs/**", "/swagger-ui/**"};

}
