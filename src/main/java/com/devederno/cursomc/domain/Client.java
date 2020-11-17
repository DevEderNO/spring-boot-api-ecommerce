package com.devederno.cursomc.domain;

import com.devederno.cursomc.domain.types.ClientType;
import com.devederno.cursomc.domain.types.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Client implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;

  @Column(unique = true)
  private String email;
  private String cpfOrCnpj;
  private Integer type;

  @JsonIgnore
  private String password;

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
  private List<Address> addresses = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "PHONE")
  private Set<String> phones = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "client")
  private List<Order> orders = new ArrayList<>();

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PROFILES")
  private Set<Integer> profiles = new HashSet<>();

  public Client() {
    addProfile(Profile.CLIENT);
  }

  public Client(Integer id, String name, String email, String cpfOrCnpj, ClientType type, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.cpfOrCnpj = cpfOrCnpj;
    this.type = type == null ? null : type.getCod();
    this.password = password;
    addProfile(Profile.CLIENT);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCpfOrCnpj() {
    return cpfOrCnpj;
  }

  public void setCpfOrCnpj(String cpfOrCnpj) {
    this.cpfOrCnpj = cpfOrCnpj;
  }

  public ClientType getType() {
    return ClientType.toEnum(type);
  }

  public void setType(ClientType type) {
    this.type = type.getCod();
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public Set<String> getPhones() {
    return phones;
  }

  public void setPhones(Set<String> phones) {
    this.phones = phones;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Profile> getProfiles() {
    return profiles.stream().map(profile -> Profile.toEnum(profile)).collect(Collectors.toSet());
  }

  public void addProfile(Profile profile) {
    profiles.add(profile.getCod());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Client client = (Client) o;
    return Objects.equals(id, client.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
