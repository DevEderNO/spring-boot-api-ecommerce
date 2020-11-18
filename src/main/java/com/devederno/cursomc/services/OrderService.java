package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.Client;
import com.devederno.cursomc.domain.Order;
import com.devederno.cursomc.domain.OrderItem;
import com.devederno.cursomc.domain.PaymentSlip;
import com.devederno.cursomc.domain.types.PaymentStatus;
import com.devederno.cursomc.repositories.OrderItemRepository;
import com.devederno.cursomc.repositories.OrderRepository;
import com.devederno.cursomc.repositories.PaymentRepository;
import com.devederno.cursomc.security.UserSS;
import com.devederno.cursomc.services.exeptions.AuthorizationException;
import com.devederno.cursomc.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class OrderService {

  @Autowired
  private OrderRepository repo;

  @Autowired
  private PaymentSlipService paymentSlipService;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private ProductService productService;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private ClientService clientService;

  @Autowired
  private MailService mailService;

  public Order findById(Integer id) {
    Optional<Order> obj = repo.findById(id);
    obj.orElseThrow(() -> new ObjectNotFoundException(
      "Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName())
    );
    return obj.get();
  }

  @Transactional
  public Order insert(Order obj) {
    obj.setId(null);
    obj.setInstant(new Date());
    obj.setClient(clientService.findById(obj.getClient().getId()));
    obj.getPayment().setStatus(PaymentStatus.PENDING);
    obj.getPayment().setOrder(obj);
    if (obj.getPayment() instanceof PaymentSlip) {
      PaymentSlip payment = (PaymentSlip) obj.getPayment();
      paymentSlipService.insertDueDate(payment, obj.getInstant());
    }
    repo.save(obj);
    paymentRepository.save(obj.getPayment());
    for (OrderItem item : obj.getItems()) {
      item.setDiscount(0.0);
      item.setProduct(productService.findById(item.getProduct().getId()));
      item.setPrice(item.getProduct().getPrice());
      item.setOrder(obj);
    }
    orderItemRepository.saveAll(obj.getItems());
    mailService.sendOrderConfirmationHtmlMail(obj);
    return obj;
  }

  public Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
    UserSS user = UserService.authenticated();
    if (user == null) {
      throw new AuthorizationException("Acesso negado");
    }
    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
    Client client = clientService.findById(user.getId());
    return repo.findByClient(client, pageRequest);
  }
}
