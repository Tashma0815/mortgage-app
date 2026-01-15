package com.ing.mortgage.util;

import com.ing.mortgage.model.MortgageCheckRequest;

import java.math.BigDecimal;

public class TestDTOFactory {

  public static MortgageCheckRequest createMortgageCheckRequest() {
    MortgageCheckRequest request = new MortgageCheckRequest();
    request.setIncome(new BigDecimal(15000));
    request.setHomeValue(new BigDecimal(45000));
    request.setLoanValue(new BigDecimal(40000));
    request.setMaturityPeriodInMonths(60);
    return request;
  }
}
