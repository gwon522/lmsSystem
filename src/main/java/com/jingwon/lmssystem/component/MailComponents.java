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

    private final JavaMailSender javaMailSender;

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
