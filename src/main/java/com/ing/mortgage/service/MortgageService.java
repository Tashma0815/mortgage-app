package com.ing.mortgage.service;

import com.ing.mortgage.model.MortgageCheckRequest;
import com.ing.mortgage.model.MortgageCheckResponse;
import com.ing.mortgage.model.MortgageRate;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.java.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Log
@ApplicationScoped
public class MortgageService {

  private final List<MortgageRate> mortgageRates;

  /**
   * Constructor to initialize the in-memory
   * interest rates on application startup
   */
  public MortgageService() {

    // Initialize MortgageRates object in memory

    mortgageRates = new ArrayList<>();
    MortgageRate standardRate = new MortgageRate();
    standardRate.setMaturityPeriodInMonths(60);
    standardRate.setInterestRate(4.5);
    standardRate.setLastUpdate(java.time.LocalDateTime.now());

    MortgageRate eliteRate = new MortgageRate();
    eliteRate.setMaturityPeriodInMonths(80);
    eliteRate.setInterestRate(6);
    eliteRate.setLastUpdate(java.time.LocalDateTime.now());

    MortgageRate basicRate = new MortgageRate();
    basicRate.setMaturityPeriodInMonths(50);
    basicRate.setInterestRate(4);
    basicRate.setLastUpdate(java.time.LocalDateTime.now());

    this.mortgageRates.add(standardRate);
    this.mortgageRates.add(eliteRate);
    this.mortgageRates.add(basicRate);
  }

  /**
   * Get the available interest rates configured in the application
   *
   * @return mortgageRates
   */
  public List<MortgageRate> getInterestRates() {
    return mortgageRates;
  }

  /**
   * Calculate mortgage feasibility and monthly installment based on the request
   *
   * @param mortgageCheckRequest request object { @link MortgageCheckRequest }
   * @return response { @link MortgageCheckResponse }
   */
  public MortgageCheckResponse calculateMortgage(MortgageCheckRequest mortgageCheckRequest) {
    MortgageCheckResponse response = new MortgageCheckResponse();
    response.setMonthlyInstallment(calculateMonthlyInstallment(mortgageCheckRequest));
    response.setFeasible(isMortgageFeasible(mortgageCheckRequest));
    return response;
  }

  private BigDecimal calculateMonthlyInstallment(MortgageCheckRequest mortgageCheckRequest) {
    return (mortgageCheckRequest.getLoanValue().add(getTotalInterestToBePaid(mortgageCheckRequest)))
      .divide(BigDecimal.valueOf(mortgageCheckRequest.getMaturityPeriodInMonths()), 2, RoundingMode.HALF_UP);
  }

  private BigDecimal getTotalInterestToBePaid(MortgageCheckRequest mortgageCheckRequest) {
    return mortgageCheckRequest.getLoanValue()
      .multiply((getInterestFromConfig(mortgageCheckRequest.getMaturityPeriodInMonths()).divide(new BigDecimal(100), 10, RoundingMode.HALF_UP)));
  }

  private BigDecimal getInterestFromConfig(int maturityPeriod) {
    return mortgageRates.stream()
      .filter(rate -> rate.getMaturityPeriodInMonths() == maturityPeriod)
      .findFirst()
      .map(rate -> BigDecimal.valueOf(rate.getInterestRate()))
      .orElseThrow(() -> new IllegalArgumentException("No rate configured the maturity period: " + maturityPeriod));
  }

  private boolean isMortgageFeasible(MortgageCheckRequest mortgageCheckRequest) {
    boolean isLoanExceeding = mortgageCheckRequest.getLoanValue().compareTo(mortgageCheckRequest.getHomeValue()) <= 0;
    boolean isLoanWithinLimit = mortgageCheckRequest.getLoanValue().compareTo
      (mortgageCheckRequest.getIncome().multiply(new java.math.BigDecimal(4))) <= 0;
    return isLoanExceeding && isLoanWithinLimit;
  }
}
