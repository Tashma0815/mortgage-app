package com.ing.mortgage.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MortgageCheckResponse {

  private boolean feasible;
  private BigDecimal monthlyInstallment;
}
