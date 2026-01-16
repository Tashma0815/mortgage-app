package com.ing.mortgage.resource;

import com.ing.mortgage.model.MortgageCheckRequest;
import com.ing.mortgage.model.MortgageCheckResponse;
import com.ing.mortgage.model.MortgageRate;
import com.ing.mortgage.service.MortgageService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.extern.java.Log;

import java.util.List;

/**
 * Resource class exposing mortgage related endpoints
 */
@Log
@Path("/api")
public class MortgageResource {

  @Inject
  MortgageService mortgageService;

  /**
   * Method to fetch the details MortgageRate that are configured in the application
   *
   * @return SignObjectResponse
   */
  @GET
  @Path("/interest-rates}")
  public List<MortgageRate> getInterestRates() {
    return mortgageService.getInterestRates();
  }

  /**
   * Method to calculate mortgage feasibility and monthly installment based on the request which contains
   * details like applicant income, loan amount, home value and tenure etc.
   *
   * @return MortgageCheckResponse
   */
  @POST
  @Path("/mortgage-check")
  public MortgageCheckResponse calculateMortgage(MortgageCheckRequest mortgageCheckRequest) {
    return mortgageService.calculateMortgage(mortgageCheckRequest);
  }
}

