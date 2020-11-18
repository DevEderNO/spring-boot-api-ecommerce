package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.Client;
import com.devederno.cursomc.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

  @Autowired
  private ClientService clientService;

  @Autowired
  private ClientRepository repo;

  @Autowired
  private BCryptPasswordEncoder bcrypt;

  @Autowired
  private MailService mailService;

  private Random random = new Random();

  public void sendNewPassword(String email) {
    Client client = clientService.findByEmail(email);
    String newPassword = newPassword();
    client.setPassword(bcrypt.encode(newPassword));
    repo.save(client);

    mailService.sendNewPasswordMail(client, newPassword);
  }

  private String newPassword() {
    char[] vet = new char[10];
    for (int i = 0; i < 10; i++) {
      vet[i] = randomChar();
    }
    return new String(vet);
  }

  private char randomChar() {
    int opt = random.nextInt(3);
    if (opt == 0) {
      return (char) (random.nextInt(10) + 48);
    } else if (opt == 1) {
      return (char) (random.nextInt(26) + 65);
    } else {
      return (char) (random.nextInt(26) + 97);
    }
  }
}
