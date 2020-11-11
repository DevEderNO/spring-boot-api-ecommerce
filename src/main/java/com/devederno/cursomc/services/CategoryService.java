package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.Category;
import com.devederno.cursomc.repositories.CategoryRepository;
import com.devederno.cursomc.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

@Service
public class CategoryService implements Serializable {

  private static final long serialVersionUID = 1L;

  @Autowired
  private CategoryRepository repo;

  public Category findById(Integer id) {
    Optional<Category> obj = repo.findById(id);
    obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
    return obj.get();
  }

  public Category insert(Category obj) {
    obj.setId(null);
    return repo.save(obj);
  }
}
