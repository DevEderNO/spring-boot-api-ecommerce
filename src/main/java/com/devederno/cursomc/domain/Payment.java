package com.devederno.cursomc.domain;

import com.devederno.cursomc.domain.types.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment implements Serializable {

  @Id
  private Integer id;
  private Integer status;

  @JsonIgnore
  @OneToOne
  @JoinColumn(name = "order_id")
  @MapsId
  private Order order;

  public Payment() {
  }

  public Payment(Integer id, PaymentStatus status, Order order) {
    this.id = id;
    this.status = status == null ? null : status.getCod();
    this.order = order;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PaymentStatus getStatus() {
    return PaymentStatus.toEnum(status);
  }

  public void setStatus(PaymentStatus status) {
    this.status = status.getCod();
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Payment payment = (Payment) o;
    return Objects.equals(id, payment.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
