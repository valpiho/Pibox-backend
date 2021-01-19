package com.pibox.swan.constant;

public class Authority {
    public static final String[] USER_AUTHORITIES = { "user:read" };
    public static final String[] MANAGER_AUTHORITIES = { "user:read", "user:update" };
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update", "user:delete"};
}
