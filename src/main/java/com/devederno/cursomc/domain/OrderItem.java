package com.devederno.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

@Entity
public class OrderItem implements Serializable {

  @JsonIgnore
  @EmbeddedId
  private OrderItemPK id = new OrderItemPK();

  private Double discount;
  private Integer quantity;
  private Double price;

  public OrderItem() {
  }

  public OrderItem(Order order, Product product, Double discount, Integer quantity, Double price) {
    id.setOrder(order);
    id.setProduct(product);
    this.discount = discount;
    this.quantity = quantity;
    this.price = price;
  }

  public double getSubTotal() {
    return (price - discount) * quantity;
  }

  @JsonIgnore
  public Order getOrder() {
    return id.getOrder();
  }

  public void setOrder(Order order) {
    id.setOrder(order);
  }

  public Product getProduct() {
    return id.getProduct();
  }

  public void setProduct(Product product) {
    id.setProduct(product);
  }

  public OrderItemPK getId() {
    return id;
  }

  public void setId(OrderItemPK id) {
    this.id = id;
  }

  public Double getDiscount() {
    return discount;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderItem orderItem = (OrderItem) o;
    return Objects.equals(id, orderItem.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    final StringBuilder sb = new StringBuilder();
    sb.append(getProduct().getName());
    sb.append(", Qte: ");
    sb.append(getQuantity());
    sb.append(", Preço unitário: ");
    sb.append(nf.format(getPrice()));
    sb.append(", Subtotal: ");
    sb.append(nf.format(getSubTotal()));
    sb.append("\n");
    return sb.toString();
  }
}
