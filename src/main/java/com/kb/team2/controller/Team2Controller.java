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

        String code = result.get().getAuth_code();
        String separate = "[.]";
        String [] tmpCode = code.split(separate);

        SendMail sendmail = new SendMail(email, tmpCode[1]);
        sendmail.transport();

        memberService.updateAuthMailDate(email);

        return true;
    }

    @GetMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateAuthFlg(@RequestParam(value = "code", required = true) String code) {
        String auth_code = "0." + code;
        int result = memberService.updateAuthFlg(auth_code);

        String resultMsg = "";
        //CUDresult cudResult = new CUDresult();
        //cudResult.setResult(result == 1 ? true : false);

        if (result == 1) {
            resultMsg = "이메일 인증이 완료되었습니다. 창을 닫고 로그인해 주세요. Bono 2 team";
        } else {
            resultMsg = "이메일 인증에 실패하였습니다, 관리자에게 문의하여 주세요.";
        }

        return resultMsg;
    }

    @PostMapping(value = "/user/os", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateOsUse(@RequestParam(value = "email", required = true) String email) {
        int result = memberService.updateOsUse(email);

        CUDresult cudResult = new CUDresult();
        cudResult.setResult(result == 1 ? true : false);

        return cudResult;
    }
}
