package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.Address;
import com.devederno.cursomc.domain.City;
import com.devederno.cursomc.domain.Client;
import com.devederno.cursomc.domain.types.ClientType;
import com.devederno.cursomc.domain.types.Profile;
import com.devederno.cursomc.dto.ClientDTO;
import com.devederno.cursomc.dto.ClientNewDTO;
import com.devederno.cursomc.repositories.AddressRepository;
import com.devederno.cursomc.repositories.ClientRepository;
import com.devederno.cursomc.security.UserSS;
import com.devederno.cursomc.services.exeptions.AuthorizationException;
import com.devederno.cursomc.services.exeptions.DataIntegrityException;
import com.devederno.cursomc.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

  @Autowired
  private ClientRepository repo;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private BCryptPasswordEncoder bcrypt;

  @Autowired
  private S3Service s3Service;

  @Autowired
  private ImageService imageService;

  @Value("${img.prefix.client.profile}")
  private String imgPrefix;

  @Value("${img.profile.size}")
  private Integer imgSize;

  public Client findById(Integer id) {
    UserSS user = UserService.authenticated();
    if (user == null || !user.hasHole(Profile.ADMIN) && !id.equals(user.getId())) {
      throw new AuthorizationException("Acesso negado");
    }
    Optional<Client> obj = repo.findById(id);
    obj.orElseThrow(() -> new ObjectNotFoundException(
      "Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName())
    );
    return obj.get();
  }

  public Client findByEmail(String email) {
    Client obj = repo.findByEmail(email);
    if (obj == null) {
      throw new ObjectNotFoundException("Email não encontrado");
    }
    return obj;
  }

  public Client findAuthenticatedUserByEmail(String email) {
    UserSS user = UserService.authenticated();
    if (user == null || !user.hasHole(Profile.ADMIN) && !email.equals(user.getUsername())) {
      throw new AuthorizationException("Acesso negado");
    }
    Client obj = findByEmail(email);
    return obj;
  }

  public List<Client> findAll() {
    return repo.findAll();
  }

  public Page<Client> findPage(
    Integer page, Integer linesPerPage, String orderBy, String direction
  ) {
    PageRequest pageRequest = PageRequest.of(
      page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
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
    return new Client(obj.getId(), obj.getName(), obj.getEmail(), null, null, null);
  }

  public Client fromDTO(ClientNewDTO obj) {
    Client cli = new Client(
      null,
      obj.getName(),
      obj.getEmail(),
      obj.getCpfOrCnpj(),
      ClientType.toEnum(obj.getType()),
      bcrypt.encode(obj.getPassword())
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

  public URI uploadProfilePicture(MultipartFile multipartFile) {
    UserSS user = UserService.authenticated();

    BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);

    jpgImage = imageService.cropSquare(jpgImage);
    jpgImage = imageService.resize(jpgImage, imgSize);

    String filename = imgPrefix + user.getId() + ".jpg";

    return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), filename, "image");
  }

}
