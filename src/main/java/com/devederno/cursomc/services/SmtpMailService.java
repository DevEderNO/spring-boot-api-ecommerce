package com.devederno.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpMailService extends AbstractMailService {

  private static final Logger LOG = LoggerFactory.getLogger(MockMailService.class);

  @Autowired
  private MailSender mailSender;

  @Autowired
  private JavaMailSender javaMailSender;


  @Override
  public void sendMail(SimpleMailMessage message) {
    LOG.info("Simulando envio de email...");
    mailSender.send(message);
    LOG.info("Email enviado");
  }

  @Override
  public void sendHtmlMail(MimeMessage message) {
    LOG.info("Simulando envio de email...");
    javaMailSender.send(message);
    LOG.info("Email enviado");
  }
}
