package com.devederno.cursomc.resources;

import com.devederno.cursomc.domain.Order;
import com.devederno.cursomc.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

  @Autowired
  private OrderService service;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Order> find(@PathVariable Integer id) {
    Order cat = service.findById(id);
    return ResponseEntity.ok().body(cat);
  }

}
