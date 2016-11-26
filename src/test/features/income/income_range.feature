Feature: Income range 

  # List
  Scenario: Show range without user
    Given the income resource
    When I make a get request to the URL '/api/income/range'
    Then http status is forbidden

  Scenario: Show range of income with the user001
    Given the income resource
    And with the user 'user001' and password 'password'
    When I make a get request to the URL '/api/income/range'
    Then http status is ok
    Then the minimum is '2015-10-27T00:00:00'
    Then the maximum is '2016-10-23T00:00:00'
    
  Scenario: Show range of income with the user002
    Given the income resource
    And with the user 'user002' and password 'password'
    When I make a get request to the URL '/api/income/range'
    Then http status is ok
    Then the minimum is '2015-10-25T00:00:00'
    Then the maximum is '2016-10-23T00:00:00'

  Scenario: Show range of income with the user004
    Given the income resource
    And with the user 'user004' and password 'password'
    When I make a get request to the URL '/api/income/range'
    Then http status is ok
    Then the minimum is '2015-10-26T00:00:00'
    Then the maximum is '2016-10-23T00:00:00'
