package com.devederno.cursomc.domain.types;

public enum PaymentStatus {

  PENDING(1, "Pendente"),
  PAID(2, "Quitado"),
  CANCELLED(3, "Cancelado");

  private int cod;
  private String description;

  private PaymentStatus(int cod, String description) {
    this.cod = cod;
    this.description = description;
  }

  public int getCod() {
    return cod;
  }

  public String getDescription() {
    return description;
  }

  public static PaymentStatus toEnum(Integer cod) {
    if (cod == null) {
      return null;
    }
    for (PaymentStatus x : PaymentStatus.values()) {
      if (cod.equals(x.getCod())) {
        return x;
      }
    }

    throw new IllegalArgumentException("Id inválido: " + cod);
  }
}
