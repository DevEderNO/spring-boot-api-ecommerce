package com.devederno.cursomc.domain;

import com.devederno.cursomc.domain.types.PaymentStatus;

import javax.persistence.Entity;

@Entity
public class PaymentCard extends Payment {

  private Integer parcelNumber;

  public PaymentCard() {
  }

  public PaymentCard(Integer id, PaymentStatus status, Order order, Integer parcelNumber) {
    super(id, status, order);
    this.parcelNumber = parcelNumber;
  }

  public Integer getParcelNumber() {
    return parcelNumber;
  }

  public void setParcelNumber(Integer parcelNumber) {
    this.parcelNumber = parcelNumber;
  }

}
