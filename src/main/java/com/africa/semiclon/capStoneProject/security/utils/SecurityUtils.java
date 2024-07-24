package com.africa.semiclon.capStoneProject.security.utils;

import java.util.List;

public class SecurityUtils {

    private SecurityUtils() {}

    public static final List<String> PUBLIC_ENDPOINTS = List.of("/ap1/v1/auth");

    public static final String JWT_PREFIX = "Bearer ";
}
