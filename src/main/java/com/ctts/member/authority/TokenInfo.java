package com.ctts.member.authority;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
