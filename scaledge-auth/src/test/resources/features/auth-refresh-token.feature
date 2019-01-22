Feature:User should be able to obtain new token using refresh token

  Scenario:User requests to refresh token
    Given user exists in system with username "Gaurav" and password "admin"
    When User attempts to login
    Then server should return with status code '200' and Token object as json
    When User requests to refresh token
    Then server should return new token and status '200'

  Scenario: User requests to refresh token using invalid token
    When User requests to refresh token using invalid token
    Then server should return new token and status '401'