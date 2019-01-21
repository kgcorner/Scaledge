Feature:Auth server should be able to validate JWT

  Scenario:Proper JWT is obtained by login
    Given user exists in system with username "Gaurav" and password "admin"
    When User attempts to login
    Then server should return with status code '200' and Token object as json
    When User asks to validate received JWT
    Then server should return UserPreview object which JWT belongs to and status code '200'

  Scenario: Invalid JWT is used for validation
    When User attempts validate api with invalid JWT
    Then Server should respond with status '401'

  Scenario: Expired JWT is used for validation
    When User attempts validate api with expired JWT
    Then Server should respond with status '401'