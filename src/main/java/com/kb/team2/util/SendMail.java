package com.kb.team2.util;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    private MimeMessage msg;
    private String email;
    private String code;

    public SendMail(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public void transport(){
        String from = "team2_info@mail.bono.com";
        String title = "Certification mail!!";
        String content = "<html><body><div>Bono 2 Team 계정 인증 메일 입니다.</div><div><a href='http://210.216.61.151:12080/auth?code=" + code + "'>인증 하기</a></div></body></html>";

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "192.168.21.13");
            props.put("mail.smtp.user", "team2_info");
            props.put("mail.smtp.password", "123qwe");

            Session sess = Session.getInstance(props, null);

            msg = new MimeMessage(sess);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email));
            msg.setSubject(title);
            msg.setSentDate(new java.util.Date());
            msg.setContent(content, "text/html; charset=euc-kr");

            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
