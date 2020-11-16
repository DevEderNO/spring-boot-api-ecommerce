package com.devederno.cursomc.config;

import com.devederno.cursomc.services.DBService;
import com.devederno.cursomc.services.MailService;
import com.devederno.cursomc.services.MockMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

  @Autowired
  private DBService dbService;

  @Bean
  public boolean instantiateDatabase() throws ParseException {

    dbService.instantiateTestDatabase();
    return true;
  }

  @Bean
  public MailService mailService() {
    return new MockMailService();
  }
}
