package com.devederno.cursomc.resources;

import com.devederno.cursomc.domain.City;
import com.devederno.cursomc.domain.State;
import com.devederno.cursomc.dto.CityDTO;
import com.devederno.cursomc.dto.StateDTO;
import com.devederno.cursomc.services.CityService;
import com.devederno.cursomc.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/states")
public class StateResource {

  @Autowired
  private StateService service;

  @Autowired
  private CityService cityService;

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<StateDTO>> findAll() {
    List<State> list = service.findAll();
    List<StateDTO> listDto = list.stream().map(state -> new StateDTO(state)).collect(Collectors.toList());
    return ResponseEntity.ok().body(listDto);
  }

  @RequestMapping(value = "/{state_id}/cities", method = RequestMethod.GET)
  public ResponseEntity<List<CityDTO>> findCitiesByStateId(@PathVariable Integer state_id) {
    List<City> list = cityService.findCitiesByStateId(state_id);
    List<CityDTO> listDto = list.stream().map(city -> new CityDTO(city)).collect(Collectors.toList());
    return ResponseEntity.ok().body(listDto);
  }
}
