package com.kb.team2.service;

import com.kb.team2.entity.*;
import com.kb.team2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Optional<Member> chkByEmail(String email) {
        Optional<Member> result = memberRepository.chkByEmail(email);

        return result;
    }

    public Optional<Member> chkByEmailAndPassword(String email, String password) {
        Optional<Member> result = memberRepository.chkByEmailAndPassword(email, password);

        return result;
    }

    public Optional<Member> findByEmail(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);

        return result;
    }

    public Integer updateLastLogin(int idx) {
        int result = memberRepository.updateLastLogin(idx);

        return result;
    }

    public Integer updateAuthMailDate(String email) {
        int result = memberRepository.updateAuthMailDate(email);

        return result;
    }

    public Integer insertMember(String email, String password, String name) {
        int result = memberRepository.insertMember(email, password, name);

        return result;
    }

    public Integer updateAuthFlg(String code) {
        int result = memberRepository.updateAuthFlg(code);

        return result;
    }

    public Integer updateOsUse(String email) {
        int result = memberRepository.updateOsUse(email);

        return result;
    }

}
