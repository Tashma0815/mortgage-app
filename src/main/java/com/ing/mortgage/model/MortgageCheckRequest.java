package com.ing.mortgage.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class MortgageCheckRequest {

  @NonNull
  private BigDecimal income;
  private int maturityPeriodInMonths;
  @NonNull
  private BigDecimal loanValue;
  @NonNull
  private BigDecimal homeValue;
}
