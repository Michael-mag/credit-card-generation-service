package com.practice.michael.creditcardgenerationservice.controller;

import com.practice.michael.creditcardgenerationservice.service.CreditCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Credit Card Generation")
@Api(tags = "Credit Card Generation")
@RequestMapping("/creditCardGeneration/v1")
public class CreditCardController {
  private CreditCardService creditCardService;

  @ApiOperation(value = "Credit card application status")
  @ApiResponses(
      value = {
        @ApiResponse(code = 500, message = "Internal Server Error"),
        @ApiResponse(code = 200, message = "OK", response = String.class)
      })
  @GetMapping("/creditCardApplicationStatus")
  public ResponseEntity<String> creditCardApplicationStatus(
      @ApiParam(value = "Credit Card Application Reference Id", type = "String", required = true)
          @RequestParam
          String applicationRefId) {
    if (StringUtils.isEmpty(applicationRefId)) {
      ResponseEntity.badRequest().build();
    }
    String applicationStatus = creditCardService.getApplicationStatus(applicationRefId).name();
    return StringUtils.isEmpty(applicationStatus)
        ? ResponseEntity.status(HttpStatus.OK)
            .body(
                "Application status"
                    + " is not found. This may be because your application "
                    + "may still be under review.")
        : ResponseEntity.status(HttpStatus.OK).body(applicationStatus);
  }
}
