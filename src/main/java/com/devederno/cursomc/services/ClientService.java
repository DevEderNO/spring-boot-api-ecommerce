package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.Client;
import com.devederno.cursomc.repositories.ClientRepository;
import com.devederno.cursomc.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

  @Autowired
  private ClientRepository repo;

  public Client findById(Integer id) {
    Optional<Client> obj = repo.findById(id);
    obj.orElseThrow(() -> new ObjectNotFoundException(
      "Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName())
    );
    return obj.get();
  }
}
