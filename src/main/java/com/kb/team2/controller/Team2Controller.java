package com.kb.team2.controller;

import com.kb.team2.entity.*;
import com.kb.team2.service.MemberService;
import com.kb.team2.util.SendMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class Team2Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MemberService memberService;

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity chkByEmail(@RequestParam(value = "email", required = true) String email) {
        Optional<Member> result = memberService.chkByEmail(email);

        return new ResponseEntity<Member>(result.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/user/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findByEmail(@RequestParam(value = "email", required = true) String email) {
        Optional<Member> result = memberService.findByEmail(email);

        return new ResponseEntity<Member>(result.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object chkByEmailAndPassword(@RequestParam(value = "email", required = true) String email,
                                        @RequestParam(value = "password", required = true) String password) {
        Optional<Member> result = memberService.chkByEmailAndPassword(email, password);
        memberService.updateLastLogin(result.get().getIdx());

        return new ResponseEntity<Member>(result.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/user/join", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insertMember(@RequestParam(value = "email", required = true) String email,
                               @RequestParam(value = "password", required = true) String password,
                               @RequestParam(value = "name", required = true) String name) {
        int result = memberService.insertMember(email, password, name);

        CUDresult cudResult = new CUDresult();
        cudResult.setResult(result == 1 ? true : false);

        return cudResult;
    }

    @PostMapping(value = "/auth/mail", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object sendMail(@RequestParam(value = "email", required = true) String email) {
        Optional<Member> result = memberService.findByEmail(email);

        String code = result.get().getChk_code();
        String separate = "[.]";
        String [] tmpCode = code.split(separate);

        SendMail sendmail = new SendMail(email, tmpCode[1]);
        sendmail.transport();

        return true;
    }

    @GetMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateAuthFlg(@RequestParam(value = "code", required = true) String code) {
        String chk_code = "0." + code;
        int result = memberService.updateAuthFlg(chk_code);

        CUDresult cudResult = new CUDresult();
        cudResult.setResult(result == 1 ? true : false);

        return cudResult;
    }

    @PostMapping(value = "/user/os", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateOsUse(@RequestParam(value = "email", required = true) String email,
                              @RequestParam(value = "p_name", required = true) String p_name,
                              @RequestParam(value = "u_domain", required = true) String u_domain,
                              @RequestParam(value = "p_domain", required = true) String p_domain) {
        int result = memberService.updateOsUse(p_name, u_domain, p_domain, email);

        CUDresult cudResult = new CUDresult();
        cudResult.setResult(result == 1 ? true : false);

        return cudResult;
    }
}
