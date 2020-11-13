package com.devederno.cursomc.resources;

import com.devederno.cursomc.domain.Product;
import com.devederno.cursomc.dto.ProductDTO;
import com.devederno.cursomc.resources.utils.URL;
import com.devederno.cursomc.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

  @Autowired
  private ProductService service;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Product> find(@PathVariable Integer id) {
    Product cat = service.findById(id);
    return ResponseEntity.ok().body(cat);
  }

  @RequestMapping(value = "/search", method = RequestMethod.GET)
  public ResponseEntity<Page<ProductDTO>> findPage(
    @RequestParam(value = "name", defaultValue = "0") String name,
    @RequestParam(value = "categories", defaultValue = "0") String categories,
    @RequestParam(value = "page", defaultValue = "0") Integer page,
    @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
    @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
    @RequestParam(value = "direction", defaultValue = "ASC") String direction
  ) {
    String nomeDecoded = URL.decodeParam(name);
    List<Integer> ids = URL.decodeInList(categories);
    Page<Product> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
    Page<ProductDTO> listDto = list.map(item -> new ProductDTO(item));
    return ResponseEntity.ok().body(listDto);
  }

}
