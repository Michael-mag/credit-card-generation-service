package com.practice.michael.creditcardgenerationservice.repository;

import com.practice.michael.creditcardgenerationservice.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
  CreditCard findByRefId(String applicationRefId);
}
