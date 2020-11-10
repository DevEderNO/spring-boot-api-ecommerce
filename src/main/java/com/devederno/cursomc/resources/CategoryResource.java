package com.devederno.cursomc.resources;

import com.devederno.cursomc.domain.Category;
import com.devederno.cursomc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

  @Autowired
  private CategoryService service;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> listar(@PathVariable Integer id) {
    Category cat = service.findById(id);
    return ResponseEntity.ok().body(cat);
  }

}
