package com.devederno.cursomc.services.validation;

import com.devederno.cursomc.domain.Client;
import com.devederno.cursomc.domain.types.ClientType;
import com.devederno.cursomc.dto.ClientDTO;
import com.devederno.cursomc.repositories.ClientRepository;
import com.devederno.cursomc.resources.exception.FieldMessage;
import com.devederno.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientUpdateValidation implements ConstraintValidator<ClientInsert, ClientDTO> {

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private ClientRepository repo;

  @Override
  public void initialize(ClientInsert ann) {
  }

  @Override
  public boolean isValid(ClientDTO objDto, ConstraintValidatorContext context) {

    Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    Integer uriId = Integer.parseInt(map.get("id"));

    List<FieldMessage> list = new ArrayList<>();

    Client obj = repo.findByEmail(objDto.getEmail());

    if (obj != null && !obj.getId().equals(uriId)) {
      list.add(new FieldMessage("email", "E-mail já cadastrado"));
    }

    for (FieldMessage fm : list) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(fm.getMessage()).addPropertyNode(fm.getFieldName()).addConstraintViolation();
    }
    return list.isEmpty();
  }
}
