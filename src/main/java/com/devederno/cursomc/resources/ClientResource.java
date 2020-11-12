package com.devederno.cursomc.resources;

import com.devederno.cursomc.domain.Client;
import com.devederno.cursomc.dto.ClientDTO;
import com.devederno.cursomc.dto.ClientNewDTO;
import com.devederno.cursomc.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

  @Autowired
  private ClientService service;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Client> find(@PathVariable Integer id) {
    Client client = service.findById(id);
    return ResponseEntity.ok().body(client);
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<ClientDTO>> list() {
    List<Client> list = service.findAll();
    List<ClientDTO> listDto = list.stream().map(item -> new ClientDTO(item)).collect(Collectors.toList());
    return ResponseEntity.ok().body(listDto);
  }

  @RequestMapping(value = "/page", method = RequestMethod.GET)
  public ResponseEntity<Page<ClientDTO>> findPage(
    @RequestParam(value = "page", defaultValue = "0") Integer page,
    @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
    @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
    @RequestParam(value = "direction", defaultValue = "ASC") String direction
  ) {
    Page<Client> list = service.findPage(page, linesPerPage, orderBy, direction);
    Page<ClientDTO> listDto = list.map(item -> new ClientDTO(item));
    return ResponseEntity.ok().body(listDto);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Void> create(@Valid @RequestBody ClientNewDTO objDto) {
    Client obj = service.fromDTO(objDto);
    service.insert(obj);
    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequestUri()
      .path("/{id}")
      .buildAndExpand(obj.getId())
      .toUri();
    return ResponseEntity.created(uri).build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO objDTO, @PathVariable Integer id) {
    Client obj = service.fromDTO(objDTO);
    obj.setId(id);
    service.update(obj);
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }


}
