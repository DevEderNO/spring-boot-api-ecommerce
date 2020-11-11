package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.Category;
import com.devederno.cursomc.repositories.CategoryRepository;
import com.devederno.cursomc.services.exeptions.DataIntegrityException;
import com.devederno.cursomc.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements Serializable {

  private static final long serialVersionUID = 1L;

  @Autowired
  private CategoryRepository repo;

  public List<Category> findAll() {
    return repo.findAll();
  }

  public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
    return repo.findAll(pageRequest);
  }

  public Category findById(Integer id) {
    Optional<Category> obj = repo.findById(id);
    obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
    return obj.get();
  }

  public Category insert(Category obj) {
    obj.setId(null);
    return repo.save(obj);
  }

  public Category update(Category obj) {
    findById(obj.getId());
    return repo.save(obj);
  }

  public void delete(Integer id) {
    Category obj = findById(id);
    try {
      repo.deleteById(obj.getId());
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityException("Não e possível excluir uma categoria que possui produtos");
    }
  }
}
