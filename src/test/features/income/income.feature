Feature: Income management

  # List
  Scenario: List income without user
    Given the income resource
    When I make a get request to the URL '/api/income'
    Then http status is forbidden

  Scenario: List income with user
    Given the income resource
    And with the user 'user001' and password 'password'
    When I make a get request to the URL '/api/income'
    Then http status is ok

  # Create
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
    Then the income is creating

  Scenario: Create income without name with user
    Given the income resource
    And with the user 'user001' and password 'password'
    And a incomeDTO without name
    When I make a post request to the URL '/api/income'
    Then http status is bad request

  # Update
  Scenario: Update income without user
    Given the income resource
    And a good incomeDTO
    When I make a put request to the URL '/api/income/7'
    Then http status is forbidden

  Scenario: Update income with user
    Given the income resource
    And with the user 'user001' and password 'password'
    And a good incomeDTO
    When I make a put request to the URL '/api/income/7'
    Then http status is ok
    Then the income 7 is updating

  Scenario: Update income without name with user
    Given the income resource
    And with the user 'user001' and password 'password'
    And a incomeDTO without name
    When I make a put request to the URL '/api/income/7'
    Then http status is bad request
