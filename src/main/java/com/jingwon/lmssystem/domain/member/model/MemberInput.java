package com.jingwon.lmssystem.domain.member.model;

import lombok.Data;

@Data
public class MemberInput {
    private String userId;
    private String userName;
    private String phone;
    private String pwd;

}
