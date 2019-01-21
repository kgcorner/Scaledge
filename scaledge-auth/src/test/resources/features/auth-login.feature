Feature:Authentication service should be able to validate user's credentials

  Scenario:user has valid credentials
    Given user exists in system with username "Gaurav" and password "admin"
    When User attempts to login
    Then server should return with status code '200' and Token object as json

  Scenario:user uses non-basic token
    Given user exists in system with username "Gaurav" and password "admin"
    And user usage non-basic token
    When User attempts to login
    Then server should return with status code '400'

  Scenario:user uses empty basic token
    Given user exists in system with username "Gaurav" and password "admin"
    And user usage empty basic token
    When User attempts to login
    Then server should return with status code '400'

  Scenario:user uses basic token in invalid format
    Given user exists in system with username "Gaurav" and password "admin"
    And user usage basic token in invalid format
    When User attempts to login
    Then server should return with status code '400'

  @InvalidCredential
  Scenario Outline: :user uses invalid credentials
    Given user exists in system with username "<username>" and password "<password>"
    When User attempts to login
    Then server should return with status code '401'
    Examples:
      | username | password |
      | abc      | pwd      |
      | cbc      | pwd      |
      | zbc      | pwd      |