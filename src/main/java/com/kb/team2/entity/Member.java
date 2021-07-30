package com.kb.team2.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    private String email;
    private String name;
    private String password;
    private int chk_flg;
    private String chk_code;
    private int os_use_flg;
    private String os_project_name;
    private String os_user_domain_name;
    private String os_project_domain_name;
    private Date j_date;
    private Date l_date;

    /*
    @Builder
    public Member(String email){
        this.email = email;
    }
    */
}

