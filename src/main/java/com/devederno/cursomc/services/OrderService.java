package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.Order;
import com.devederno.cursomc.repositories.OrderRepository;
import com.devederno.cursomc.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

  @Autowired
  private OrderRepository repo;

  public Order findById(Integer id) {
    Optional<Order> obj = repo.findById(id);
    obj.orElseThrow(() -> new ObjectNotFoundException(
      "Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName())
    );
    return obj.get();
  }
}
