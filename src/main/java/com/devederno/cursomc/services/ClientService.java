package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.Address;
import com.devederno.cursomc.domain.City;
import com.devederno.cursomc.domain.Client;
import com.devederno.cursomc.domain.types.ClientType;
import com.devederno.cursomc.dto.ClientDTO;
import com.devederno.cursomc.dto.ClientNewDTO;
import com.devederno.cursomc.repositories.AddressRepository;
import com.devederno.cursomc.repositories.ClientRepository;
import com.devederno.cursomc.services.exeptions.DataIntegrityException;
import com.devederno.cursomc.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

  @Autowired
  private ClientRepository repo;

  @Autowired
  private AddressRepository addressRepository;

  public Client findById(Integer id) {
    Optional<Client> obj = repo.findById(id);
    obj.orElseThrow(() -> new ObjectNotFoundException(
      "Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName())
    );
    return obj.get();
  }

  public List<Client> findAll() {
    return repo.findAll();
  }

  public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
    return repo.findAll(pageRequest);
  }

  @Transactional
  public Client insert(Client obj) {
    obj.setId(null);
    repo.save(obj);
    addressRepository.saveAll(obj.getAddresses());
    return repo.save(obj);
  }

  public Client update(Client obj) {
    Client newObj = findById(obj.getId());
    updateData(newObj, obj);
    return repo.save(newObj);
  }


  public void delete(Integer id) {
    Client obj = findById(id);
    try {
      repo.deleteById(obj.getId());
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityException("Não e possível excluir uma categoria que possui produtos");
    }
  }

  public Client fromDTO(ClientDTO obj) {
    return new Client(obj.getId(), obj.getName(), obj.getEmail(), null, null);
  }

  public Client fromDTO(ClientNewDTO obj) {
    Client cli = new Client(
      null,
      obj.getName(),
      obj.getEmail(),
      obj.getCpfOrCnpj(),
      ClientType.toEnum(obj.getType())
    );
    City city = new City(obj.getCityId(), null, null);
    Address address = new Address(
      null,
      obj.getStreet(),
      obj.getNumber(),
      obj.getComplement(),
      obj.getNeighborhood(),
      obj.getZipCode(),
      cli,
      city
    );
    cli.getAddresses().add(address);
    cli.getPhones().add(obj.getPhone1());
    if (obj.getPhone2() != null) {
      cli.getPhones().add(obj.getPhone2());
    }
    if (obj.getPhone3() != null) {
      cli.getPhones().add(obj.getPhone3());
    }
    return cli;
  }

  private void updateData(Client newObj, Client obj) {
    newObj.setName(obj.getName());
    newObj.setEmail(obj.getEmail());
  }

}
