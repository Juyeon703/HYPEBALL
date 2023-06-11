package com.project.hypeball.config.auth.dto;

import com.project.hypeball.domain.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class LoginMember implements Serializable {
    private String name;
    private String email;
//    private String picture;

    private String provider;

    public LoginMember(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
//        this.picture = member.getPicture();
        this.provider = member.getProvider();
    }
}