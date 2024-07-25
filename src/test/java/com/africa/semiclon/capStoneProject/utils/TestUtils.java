package com.africa.semiclon.capStoneProject.utils;

import com.africa.semiclon.capStoneProject.data.models.Address;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterRequest;

import static com.africa.semiclon.capStoneProject.data.models.Authority.USER;

public class TestUtils {
    public static final String BLACKLISTED_TOKEN = "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJqd3QtcHJvamVjd" +
            "CIsImlhdCI6MTcyMDg3NTc0NywiZXhwIjoxNzIwODc5MzQ3LCJzdWIiOiJhZG1pbiIsInByaW5jaXBhbCI6ImFkbWluIiwiY3Jl" +
            "ZGVudGlhbHMiOiJbUFJPVEVDVEVEXSIsInJvbGVzIjpbIkFETUlOIl19.QGMnCxOGMbG8-BoPz2Zf2OGiTAy4iVC0mUKhzKS6vj" +
            "007zNSMMNTrQBVnFQrzQPJg6mMw2xu6ZxVNS9EmLo2s19fFGMPKXGaEx4UWq8W-w_XyQG-oS6916pz7dNb0twKt8T9VvCe-uicll" +
            "Xjx3-ok5M-L-cpyE3_3Bc-aTMWfzGI0FU6Vi1zTykE_NvvKvJUKVyMIWlV7JV3pbPZTgzjdRCkkU-pw7WOajTJU54ngNI89-wq51o" +
            "H9yyBlQukiURGLugpXpv6y8EXjZA-s21USLOWPyaGx5_ae1Ac8qft4-a9yLH5YJh-hw39kHG7PG-PNd7d5NLhf3kaahgdAB_99g";


    public static RegisterRequest buildRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("username");
        request.setPhoneNumber("0070000");
        request.setPassword("password");
        request.setRole(USER);
        return request;
    }
}
