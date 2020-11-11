package com.devederno.cursomc.domain;


import com.devederno.cursomc.domain.types.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class PaymentSlip extends Payment {

  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dueDate;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private Date paymentDate;

  public PaymentSlip() {
  }

  public PaymentSlip(Integer id, PaymentStatus status, Order order, Date dueDate, Date paymentDate) {
    super(id, status, order);
    this.dueDate = dueDate;
    this.paymentDate = paymentDate;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }
}
