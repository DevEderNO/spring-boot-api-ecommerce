package com.devederno.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "ODER")
public class Order implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private Date instant;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
  private Payment payment;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToOne
  @JoinColumn(name = "delivery_address_id")
  private Address deliveryAddress;

  @OneToMany(mappedBy = "id.order")
  private Set<OrderItem> items = new HashSet<>();

  public Order() {
  }

  public Order(Integer id, Date instant, Client client, Address deliveryAddress) {
    this.id = id;
    this.instant = instant;
    this.client = client;
    this.deliveryAddress = deliveryAddress;
  }

  public double getTotal() {
    double total = 0;
    for (OrderItem item : items) {
      total += item.getSubTotal();
    }
    return total;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getInstant() {
    return instant;
  }

  public void setInstant(Date instant) {
    this.instant = instant;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Address getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(Address deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public Set<OrderItem> getItems() {
    return items;
  }

  public void setItems(Set<OrderItem> items) {
    this.items = items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    final StringBuilder sb = new StringBuilder();
    sb.append("Pedido número: ");
    sb.append(getId());
    sb.append(", Instante: ");
    sb.append(sdf.format(getInstant()));
    sb.append(", Cliente: ");
    sb.append(getClient().getName());
    sb.append(", Situação do pagamento: ");
    sb.append(getPayment().getStatus().getDescription());
    sb.append("\nDetalhes:\n");
    for (OrderItem item : getItems()) {
      sb.append(item.toString());
    }
    sb.append("Valor total: ");
    sb.append(nf.format(getTotal()));
    return sb.toString();
  }

}
