Feature: Invoice range 

  # List
  Scenario: Show range of invoice without user
    Given the invoice resource
    When I make a get request to the URL '/api/invoice/range'
    Then http status is forbidden

  Scenario: Show range of invoice with the user001
    Given the invoice resource
    And with the user 'user001' and password 'password'
    When I make a get request to the URL '/api/invoice/range'
    Then http status is ok
    Then the minimum is '2015-11-05T00:00:00'
    Then the maximum is '2016-10-21T00:00:00'
    
  Scenario: Show range of invoice with the user002
    Given the invoice resource
    And with the user 'user002' and password 'password'
    When I make a get request to the URL '/api/invoice/range'
    Then http status is ok
    Then the minimum is '2015-10-25T00:00:00'
    Then the maximum is '2016-10-22T00:00:00'

  Scenario: Show range of invoice with the user003
    Given the invoice resource
    And with the user 'user003' and password 'password'
    When I make a get request to the URL '/api/invoice/range'
    Then http status is ok
    Then the minimum is '2015-10-24T00:00:00'
    Then the maximum is '2016-10-19T00:00:00'
