package com.devederno.cursomc.services.validation;

import com.devederno.cursomc.domain.types.ClientType;
import com.devederno.cursomc.dto.ClientNewDTO;
import com.devederno.cursomc.resources.exception.FieldMessage;
import com.devederno.cursomc.services.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClientInsertValidation implements ConstraintValidator<ClientInsert, ClientNewDTO> {
  @Override
  public void initialize(ClientInsert ann) {
  }

  @Override
  public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
    List<FieldMessage> list = new ArrayList<>();

    if(objDto.getType().equals(ClientType.PESSOA_FISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOrCnpj())){
      list.add(new FieldMessage("cpfOrCnpj", "CPF inválido"));
    }
    if(objDto.getType().equals(ClientType.PESSOA_JURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOrCnpj())){
      list.add(new FieldMessage("cpfOrCnpj", "CNPJ inválido"));
    }

    for (FieldMessage fm : list) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(fm.getMessage()).addPropertyNode(fm.getFieldName()).addConstraintViolation();
    }
    return list.isEmpty();
  }
}
