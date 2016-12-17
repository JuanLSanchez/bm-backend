Feature: Supplier management

  # List
  Scenario: List supplier without user
    Given the supplier resource
    When I make a get request to the URL '/api/section'
    Then http status is forbidden

  Scenario: List supplier with user
    Given the supplier resource
    And with the user 'user001' and password 'password'
    When I make a get request to the URL '/api/section'
    Then http status is ok