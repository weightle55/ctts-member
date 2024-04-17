package com.ctts.member.authority;


import lombok.Getter;

@Getter
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
