# Mortgage App

This is a simple Quarkus REST application for mortgage services. The application has 2 endpoints

  * GET /api/interest-rates - get a list of current interest rates
  * POST /api/mortgage-check - checks the feasibility and monthly payment based on the request containing information about home value, annual income, maturity period and loan amount

# Features implemented:

1. The interest rates fetched from the memory created application which will be fetched during the application startup.
2. The feasibility of loan is calculated based on the below business logic by considering the flat rate of interest.
   - a mortgage should not exceed 4 times the income
   - a mortgage should not exceed the home value
3. Logging is enabled.
4. Junit test cases added.

# Enhancements :
Below mentioned enhancements can be taken up based on the requirements and business needs

1. The in-memory database feature can also be implemented using h2 database. Map is been used currently to keep the implementation simple.
2. Currently there are no custom headers implemented as part of the API calls, they can also be added to keep the track of the conencting system by introducing user-id/consumer-id. trace-id can be added to track each requests in end to end flow
3. More precise exception handling can be done by introducing custom exception classes and business specific error messages and error codes. currently exception handling is by inbuilt libraries.
4. The current implementation for calculating the monthly installment/costs is with considering the interest rate to be flat. logic can be modified conidering the reducing interest rate as well based on the business need.





