package com.practice.michael.creditcardgenerationservice.event;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewCreditCardVerifiedEvent {
  private EventType eventType = EventType.CREDIT_CARD_APPROVED;
  private List<CreditCardVerificationStatus> creditCardVerificationStatuses;
}
