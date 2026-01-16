package com.ing.mortgage.service;

import com.ing.mortgage.model.MortgageCheckRequest;
import com.ing.mortgage.model.MortgageCheckResponse;
import com.ing.mortgage.util.TestDTOFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class MortgageServiceTest {

  @Inject
  MortgageService mortgageService;

  @Test
  public void calculateMortgage_WhenLoanIsWithinHomeValue() {
    MortgageCheckRequest request = TestDTOFactory.createMortgageCheckRequest();
    MortgageCheckResponse response = mortgageService.calculateMortgage(request);

    assertThat(response.isFeasible()).isTrue();
    assertThat(response.getMonthlyInstallment()).isEqualByComparingTo(BigDecimal.valueOf(696.67));
  }

  @Test
  public void calculateMortgage_WhenLoanIsMoreThanHomeValue() {
    MortgageCheckRequest request = TestDTOFactory.createMortgageCheckRequest();
    request.setLoanValue(new BigDecimal(48000));
    MortgageCheckResponse response = mortgageService.calculateMortgage(request);

    assertThat(response.isFeasible()).isFalse();
  }

  @Test
  public void calculateMortgage_WhenLoanIsMoreThan4XOfSalary() {
    MortgageCheckRequest request = TestDTOFactory.createMortgageCheckRequest();
    request.setLoanValue(new BigDecimal(65000));
    MortgageCheckResponse response = mortgageService.calculateMortgage(request);

    assertThat(response.isFeasible()).isFalse();
  }

  @Test
  public void calculateMortgage_WhenLoanIsLessThan4XOfSalary() {
    MortgageCheckRequest request = TestDTOFactory.createMortgageCheckRequest();
    request.setLoanValue(new BigDecimal(35000));
    MortgageCheckResponse response = mortgageService.calculateMortgage(request);

    assertThat(response.isFeasible()).isTrue();
    assertThat(response.getMonthlyInstallment()).isEqualByComparingTo(BigDecimal.valueOf(609.58));
  }

  @Test
  public void testCalculateMortgage_WhenMaturityPeriodIsNotConfigured() {
    MortgageCheckRequest request = TestDTOFactory.createMortgageCheckRequest();
    request.setMaturityPeriodInMonths(12);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      mortgageService.calculateMortgage(request);
    });
    assertTrue(exception.getMessage().contains("No rate configured"));
  }

  @Test
  public void testGetInterestRates() {
    assertThat(mortgageService.getInterestRates()).isNotEmpty();
  }
}
