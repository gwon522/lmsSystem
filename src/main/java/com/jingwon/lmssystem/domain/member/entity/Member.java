package com.jingwon.lmssystem.domain.member.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    private String userId;

    private String userName;
    private String pwd;
    private String resetPwdKey;
    private LocalDateTime resetPwdLimitDt;
    private String phone;

    private LocalDateTime regDt;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    private boolean adminYn;

}
