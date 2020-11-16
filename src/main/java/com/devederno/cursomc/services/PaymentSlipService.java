package com.devederno.cursomc.services;

import com.devederno.cursomc.domain.PaymentSlip;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class PaymentSlipService {

  public void insertDueDate(PaymentSlip payment, Date orderInstant) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(orderInstant);
    cal.add(Calendar.DAY_OF_MONTH, 7);
    payment.setDueDate(cal.getTime());
  }
}
