package com.jingwon.lmssystem.domain.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResetPwd {
    private String userId;
    private String userName;
    private String password;
    private String id;
}
