package com.devederno.cursomc.config;

import com.devederno.cursomc.domain.*;
import com.devederno.cursomc.domain.types.ClientType;
import com.devederno.cursomc.domain.types.PaymentStatus;
import com.devederno.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@Configuration
public class Intantiation implements CommandLineRunner {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private StateRepository stateRepository;

  @Autowired
  private CityRepository cityRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Override
  public void run(String... args) throws Exception {

    Category cat1 = new Category(null, "Informática");
    Category cat2 = new Category(null, "Escritório");

    Product p1 = new Product(null, "Computador", 2000.00);
    Product p2 = new Product(null, "Impressora", 800.00);
    Product p3 = new Product(null, "Mouse", 80.00);

    cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
    cat1.getProducts().addAll(Arrays.asList(p2));

    p1.getCategories().addAll(Arrays.asList(cat1));
    p2.getCategories().addAll(Arrays.asList(cat1, cat2));
    p3.getCategories().addAll(Arrays.asList(cat1));

    categoryRepository.saveAll(Arrays.asList(cat1, cat2));
    productRepository.saveAll(Arrays.asList(p1, p2, p3));

    State state1 = new State(null, "Minas Gerais");
    State state2 = new State(null, "São Paulo");

    City city1 = new City(null, "Uberlândia", state1);
    City city2 = new City(null, "São Paulo", state2);
    City city3 = new City(null, "Campinas", state2);

    state1.getCities().addAll(Arrays.asList(city1));
    state2.getCities().addAll(Arrays.asList(city2, city3));

    stateRepository.saveAll(Arrays.asList(state1, state2));
    cityRepository.saveAll(Arrays.asList(city1, city2, city3));

    Client client1 = new Client(null,
      "Maria Silva",
      "maria@gmail.com",
      "36378912377",
      ClientType.PESSOA_FISICA);
    client1.getPhones().addAll(Arrays.asList("6227363323", "6233335555"));

    Address address1 = new Address(null,
      "Rua Flores",
      "300",
      "Apto 303",
      "Jardim",
      "30030030033",
      client1,
      city1);
    Address address2 = new Address(null,
      "Av Matos",
      "105",
      "Sala 800",
      "Centro",
      "75526832104",
      client1,
      city2);

    client1.getAddresses().addAll(Arrays.asList(address1, address2));

    clientRepository.save(client1);
    addressRepository.saveAll(Arrays.asList(address1, address2));

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    Order order1 = new Order(null,
      sdf.parse("30/09/2017 10:32"),
      client1,
      address1
    );
    Order order2 = new Order(null,
      sdf.parse("10/10/2017 10:32"),
      client1,
      address2
    );

    Payment payment1 = new PaymentCard(null, PaymentStatus.PAID, order1, 6);
    order1.setPayment(payment1);

    PaymentSlip payment2 = new PaymentSlip(null,
      PaymentStatus.PENDING,
      order2,
      sdf.parse("20/10/2017 00:00"),
      null);
    order2.setPayment(payment2);

    client1.getOrders().addAll(Arrays.asList(order1, order2));

    orderRepository.saveAll(Arrays.asList(order1, order2));
    paymentRepository.saveAll(Arrays.asList(payment1, payment2));

    OrderItem orderItem1 = new OrderItem(order1, p1, 0.00, 1, 2000.00);
    OrderItem orderItem2 = new OrderItem(order1, p3, 0.00, 2, 80.00);
    OrderItem orderItem3 = new OrderItem(order2, p2, 100.00, 1, 800.00);

    order1.getItems().addAll(Arrays.asList(orderItem1, orderItem2));
    order2.getItems().addAll(Arrays.asList(orderItem3));

    p1.getItems().addAll(Arrays.asList(orderItem1));
    p2.getItems().addAll(Arrays.asList(orderItem3));
    p3.getItems().addAll(Arrays.asList(orderItem2));

    orderItemRepository.saveAll(Arrays.asList(orderItem1, orderItem2, orderItem3));
  }
}
