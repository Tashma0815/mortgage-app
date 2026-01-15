package com.ing.mortgage.resource;

import com.ing.mortgage.service.MortgageService;
import com.ing.mortgage.util.TestDTOFactory;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class MortgageResourceTest {
  @Inject
  MortgageResource mortgageResource;
  @InjectMock
  MortgageService mortgageService;

  @Test
  public void testGetInterestRates() {
    mortgageResource.getInterestRates();
    Mockito.verify(mortgageService).getInterestRates();
  }

  @Test
  public void testCalculateMortgage() {
    mortgageResource.calculateMortgage(TestDTOFactory.createMortgageCheckRequest());
    Mockito.verify(mortgageService).calculateMortgage(TestDTOFactory.createMortgageCheckRequest());
  }
}
