package com.boot.teach.common.config;

import com.boot.teach.common.util.JWTUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.UUID;

public class main {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("admin");
        System.out.println(encode);

        String token = JWTUtils.getToken("6c63288f-66a5-4162-9976-13ddfd5ae513", "201815270130");
        System.out.println(token);

        String uuid = String.valueOf(UUID.randomUUID());
        System.out.println(uuid);
    }
}
