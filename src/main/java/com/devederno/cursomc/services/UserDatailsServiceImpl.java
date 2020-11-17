package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.Client;
import com.devederno.cursomc.repositories.ClientRepository;
import com.devederno.cursomc.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDatailsServiceImpl implements UserDetailsService {

  @Autowired
  private ClientRepository repository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Client cli = repository.findByEmail(email);
    if (cli == null) {
      throw new UsernameNotFoundException(email);
    }
    return new UserSS(cli.getId(), cli.getEmail(), cli.getPassword(), cli.getProfiles());
  }
}
