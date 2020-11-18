package com.devederno.cursomc.resources;

import com.devederno.cursomc.domain.Order;
import com.devederno.cursomc.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Page<Order>> findPage(
    @RequestParam(value = "page", defaultValue = "0") Integer page,
    @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
    @RequestParam(value = "orderBy", defaultValue = "instant") String orderBy,
    @RequestParam(value = "direction", defaultValue = "DESC") String direction
  ) {
    Page<Order> list = service.findPage(page, linesPerPage, orderBy, direction);
    return ResponseEntity.ok().body(list);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Void> create(
    @Valid
    @RequestBody Order obj
  ) {
    service.insert(obj);
    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequestUri()
      .path("/{id}")
      .buildAndExpand(obj.getId())
      .toUri();
    return ResponseEntity.created(uri).build();
  }

}
