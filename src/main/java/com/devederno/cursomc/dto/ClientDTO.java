package com.devederno.cursomc.dto;

import com.devederno.cursomc.domain.Client;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ClientDTO implements Serializable {

  private Integer id;

  @NotEmpty(message = "Preenchimento obrigatório")
  @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 80 caracteres")
  private String name;

  @NotEmpty(message = "Preenchimento obrigatório")
  @Email(message = "E-mail inválido")
  private String email;

  public ClientDTO() {
  }

  public ClientDTO(Client obj) {
    this.id = obj.getId();
    this.name = obj.getName();
    this.email = obj.getEmail();

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
}
