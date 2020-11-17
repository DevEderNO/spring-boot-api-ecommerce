package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractMailService implements MailService {

  @Value("${default.sender}")
  private String sender;

  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private JavaMailSender javaMailSender;

  @Override
  public void sendOrderConfirmationMail(Order obj) {
    SimpleMailMessage message = simpleMailMessageFromOrder(obj);
    sendMail(message);
  }

  protected SimpleMailMessage simpleMailMessageFromOrder(Order obj) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(obj.getClient().getEmail());
    message.setFrom(sender);
    message.setSubject("Pedido confirmado Código: " + obj.getId());
    message.setSentDate(new Date(System.currentTimeMillis()));
    message.setText(obj.toString());
    return message;
  }

  protected String htmlFromTemplateOrder(Order obj) {
    Context context = new Context();
    context.setVariable("order", obj);
    return templateEngine.process("mail/order-confirmation", context);
  }

  @Override
  public void sendOrderConfirmationHtmlMail(Order obj) {
    try {
      MimeMessage message = mimeMailMessageFromOrder(obj);
      sendHtmlMail(message);
    } catch (MessagingException e) {
      sendOrderConfirmationMail(obj);
    }
  }

  protected MimeMessage mimeMailMessageFromOrder(Order obj) throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper mmh = new MimeMessageHelper(message, true);
    mmh.setTo(obj.getClient().getEmail());
    mmh.setFrom(sender);
    mmh.setSubject("Pedido confirmado Código: " + obj.getId());
    mmh.setSentDate(new Date(System.currentTimeMillis()));
    mmh.setText(htmlFromTemplateOrder(obj), true);
    return message;
  }
}
