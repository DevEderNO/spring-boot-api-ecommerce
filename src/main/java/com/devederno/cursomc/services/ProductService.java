package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.Category;
import com.devederno.cursomc.domain.Product;
import com.devederno.cursomc.repositories.CategoryRepository;
import com.devederno.cursomc.repositories.ProductRepository;
import com.devederno.cursomc.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

  @Autowired
  private ProductRepository repo;

  @Autowired
  private CategoryRepository categoryRepository;

  public Product findById(Integer id) {
    Optional<Product> obj = repo.findById(id);
    obj.orElseThrow(() -> new ObjectNotFoundException(
      "Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName())
    );
    return obj.get();
  }

  public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
    List<Category> categories = categoryRepository.findAllById(ids);
    return repo.findDistinctByNameIgnoreCaseContainingAndCategoriesIn(name, categories, pageRequest);
  }
}
