package com.devederno.cursomc.config;

import com.devederno.cursomc.services.DBService;
import com.devederno.cursomc.services.MailService;
import com.devederno.cursomc.services.SmtpMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

  @Autowired
  private DBService dbService;

  @Value("${spring.jpa.hibernate.ddl-auto}")
  private String strategy;

  @Bean
  public boolean instantiateDatabase() throws ParseException {

    if (!"create".equals(strategy)) {
      return false;
    }

    dbService.instantiateTestDatabase();
    return true;
  }

  @Bean
  public MailService mailService() {
    return new SmtpMailService();
  }
}
