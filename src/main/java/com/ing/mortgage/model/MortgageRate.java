package com.ing.mortgage.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MortgageRate {

  private int maturityPeriodInMonths;
  private double interestRate;
  private LocalDateTime lastUpdate;
}
