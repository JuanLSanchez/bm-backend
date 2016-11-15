Feature: Income management

  Scenario: List income without user
    Given the income resource
    When I make a get request to the URL '/api/incomes'
    Then http status is forbidden


  Scenario: List income with user
    Given the income resource
    And with the user 'user001' and password 'password'
    When I make a get request to the URL '/api/incomes'
    Then http status is ok
