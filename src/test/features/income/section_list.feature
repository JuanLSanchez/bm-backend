Feature: Section list

  # List
  Scenario: List section without user
    Given the section resource
    When I make a get request to the URL '/api/section'
    Then http status is forbidden

  Scenario: List section with user
    Given the section resource
    And with the user 'user001' and password 'password'
    When I make a get request to the URL '/api/section'
    Then http status is ok