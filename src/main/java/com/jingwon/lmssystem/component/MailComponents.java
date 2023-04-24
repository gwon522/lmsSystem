package com.jingwon.lmssystem.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailComponents {
    //xktzawjmmtpaxfme

    private final JavaMailSender javaMailSender;

    public void sendMailTest(){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("nesaz0522@naver.com");
        msg.setSubject("ㅎㅇ 테스트용 메일");
        msg.setText("텍스트 내용 테스트");
        javaMailSender.send(msg);
    }

    public boolean sendMail(String mail, String subject, String text){
        boolean result = false;

        MimeMessagePreparator mmp = mimeMessage -> {
            MimeMessageHelper mh = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mh.setTo(mail);
            mh.setSubject(subject);
            mh.setText(text,true);
        };

        try{
            javaMailSender.send(mmp);
            result = true;
        }catch (Exception e){
            log.info(e.getStackTrace().toString());
        }
        return result;
    }
}
