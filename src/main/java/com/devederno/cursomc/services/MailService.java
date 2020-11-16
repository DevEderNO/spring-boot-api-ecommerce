package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.Order;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface MailService {

  void sendOrderConfirmationMail(Order obj);

  void sendMail(SimpleMailMessage message);

  void sendOrderConfirmationHtmlMail(Order obj);

  void sendHtmlMail(MimeMessage message);
}
