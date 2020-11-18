package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.City;
import com.devederno.cursomc.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

  @Autowired
  private CityRepository repo;

  public List<City> findCitiesByStateId(Integer state_id) {
    return repo.findCitiesByStateId(state_id);
  }
}
