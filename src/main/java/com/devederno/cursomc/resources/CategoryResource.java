package com.devederno.cursomc.resources;

import com.devederno.cursomc.domain.Category;
import com.devederno.cursomc.dto.CategoryDTO;
import com.devederno.cursomc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

  @Autowired
  private CategoryService service;

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<CategoryDTO>> list() {
    List<Category> list = service.findAll();
    List<CategoryDTO> listDto = list.stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList());
    return ResponseEntity.ok().body(listDto);
  }

  @RequestMapping(value = "/page", method = RequestMethod.GET)
  public ResponseEntity<Page<CategoryDTO>> findPage(
    @RequestParam(value = "page", defaultValue = "0") Integer page,
    @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
    @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
    @RequestParam(value = "direction", defaultValue = "ASC") String direction
  ) {
    Page<Category> list = service.findPage(page, linesPerPage, orderBy, direction);
    Page<CategoryDTO> listDto = list.map(item -> new CategoryDTO(item));
    return ResponseEntity.ok().body(listDto);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Category> find(
    @PathVariable Integer id
  ) {
    Category cat = service.findById(id);
    return ResponseEntity.ok().body(cat);
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Void> create(
    @Valid
    @RequestBody CategoryDTO obj
  ) {
    Category category = service.fromDTO(obj);
    service.insert(category);
    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequestUri()
      .path("/{id}")
      .buildAndExpand(category.getId())
      .toUri();
    return ResponseEntity.created(uri).build();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Void> update(
    @Valid
    @RequestBody CategoryDTO objDTO,
    @PathVariable Integer id
  ) {
    Category obj = service.fromDTO(objDTO);
    obj.setId(id);
    service.update(obj);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(
    @PathVariable Integer id
  ) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

}
