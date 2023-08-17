package com.practice.michael.creditcardgenerationservice.messaging;

import com.practice.michael.creditcardgenerationservice.event.NewCreditCardVerifiedEvent;
import com.practice.michael.creditcardgenerationservice.service.CreditCardService;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class NewCreditCardVerifiedEventListener {
  private CreditCardService creditCardService;

  @Bean
  public Consumer<NewCreditCardVerifiedEvent> generateNewCreditCard() {
    return newCreditCardVerifiedEvent -> {
      log.info(
          "**********Received a new credit card application: {}",
          newCreditCardVerifiedEvent.getCreditCardVerificationStatuses().size());
      creditCardService.generateNewCreditCardDetails(
          newCreditCardVerifiedEvent.getCreditCardVerificationStatuses());
    };
  }
}
