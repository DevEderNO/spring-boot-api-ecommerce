package com.devederno.cursomc.config;

import com.devederno.cursomc.domain.Category;
import com.devederno.cursomc.domain.Product;
import com.devederno.cursomc.repositories.CategoryRepository;
import com.devederno.cursomc.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Intantiation implements CommandLineRunner {

  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private ProductRepository productRepository;

  @Override
  public void run(String... args) throws Exception {

    Category cat1 = new Category(null, "Informática");
    Category cat2 = new Category(null, "Escritório");

    Product p1 = new Product(null, "Computador", 2000.00);
    Product p2 = new Product(null, "Impressora", 800.00);
    Product p3 = new Product(null, "Mouse", 80.00);

    cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
    cat1.getProducts().addAll(Arrays.asList(p2));

    p1.getCategories().addAll(Arrays.asList(cat1));
    p2.getCategories().addAll(Arrays.asList(cat1, cat2));
    p3.getCategories().addAll(Arrays.asList(cat1));

    categoryRepository.saveAll(Arrays.asList(cat1, cat2));
    productRepository.saveAll(Arrays.asList(p1, p2, p3));
  }
}
