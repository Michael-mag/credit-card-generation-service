package com.practice.michael.creditcardgenerationservice.service;

import com.practice.michael.creditcardgenerationservice.entity.CreditCard;
import com.practice.michael.creditcardgenerationservice.event.CreditCardVerificationStatus;
import com.practice.michael.creditcardgenerationservice.event.VerificationStatus;
import com.practice.michael.creditcardgenerationservice.repository.CreditCardRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreditCardService {
  private static final long MIN = 10000L;
  private static final long MAX = 9999999999999L;
  private static final long CVV_MIN = 100L;
  private static final long CVV_MAX = 999L;
  private CreditCardRepository creditCardRepository;

  public void generateNewCreditCardDetails(
      List<CreditCardVerificationStatus> creditCardVerificationStatuses) {
    List<CreditCard> creditCards =
        creditCardVerificationStatuses.stream()
            .map(
                creditCardVerificationStatus -> {
                  CreditCard newCreditCard = new CreditCard();
                  BeanUtils.copyProperties(creditCardVerificationStatus, newCreditCard);
                  if (creditCardVerificationStatus
                      .getVerificationStatus()
                      .equals(VerificationStatus.APPROVED)) {
                    log.info(
                        "***** The credit card application :[{}] was "
                            + "approved."
                            + "."
                            + ".Generating credit card details...*****",
                        creditCardVerificationStatus.getRefId());
                    Long creditCardNumber = generateRandomInt(MIN, MAX);
                    newCreditCard.setCreditCardNumber(creditCardNumber);
                    Long cvv = generateRandomInt(CVV_MIN, CVV_MAX);
                    newCreditCard.setCvv(cvv);
                  } else {
                    log.info(
                        "****** New credit card details can not be generated"
                            + " for an application which was not approved: {} "
                            + "******",
                        creditCardVerificationStatus.getRefId());
                  }

                  newCreditCard.setVerificationStatus(
                      creditCardVerificationStatus.getVerificationStatus());
                  return newCreditCard;
                })
            .toList();

    log.info("***** Saving new credit card details ******* ");
    creditCardRepository.saveAll(creditCards);
  }

  private Long generateRandomInt(long min, long max) {
    RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
    return randomDataGenerator.nextLong(min, max);
  }

  public VerificationStatus getApplicationStatus(String applicationRefId) {
    return creditCardRepository.findByRefId(applicationRefId).getVerificationStatus();
  }
}
