Feature: Income management

  Scenario: List income without user
    Given the income resource
    When I make a get request to the URL '/api/income'
    Then http status is forbidden

  Scenario: List income with user
    Given the income resource
    And with the user 'user001' and password 'password'
    When I make a get request to the URL '/api/income'
    Then http status is ok

  Scenario: Create income without user
    Given the income resource
    And a good incomeDTO
    When I make a post request to the URL '/api/income'
    Then http status is forbidden

  Scenario: Create income with user
    Given the income resource
    And with the user 'user001' and password 'password'
    And a good incomeDTO
    When I make a post request to the URL '/api/income'
    Then http status is created

  Scenario: Create income without name with user
    Given the income resource
    And with the user 'user001' and password 'password'
    And a incomeDTO without name
    When I make a post request to the URL '/api/income'
    Then http status is bad request
