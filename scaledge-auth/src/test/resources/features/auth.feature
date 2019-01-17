Feature:Authentication service should be able to validate user's credentials

  Scenario:user has valid credentials
    Given user exists in system with username "Gaurav" and password "admin"
    When User attempts to login
    Then server should return with status code '200' and Token object as json