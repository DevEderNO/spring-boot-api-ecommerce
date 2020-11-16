package com.devederno.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MockMailService extends AbstractMailService {

  private static final Logger LOG = LoggerFactory.getLogger(MockMailService.class);

  @Override
  public void sendMail(SimpleMailMessage message) {
    LOG.info("Simulando envio de email...");
    LOG.info(message.toString());
    LOG.info("Email enviado");
  }

  @Override
  public void sendHtmlMail(MimeMessage message) {
    LOG.info("Simulando envio de email HTML...");
    LOG.info(message.toString());
    LOG.info("Email enviado");
  }
}
