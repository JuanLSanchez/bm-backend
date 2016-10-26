Feature: Income management

  Scenario: List income without user
    Given the income resource
    When I make a get request to the URL '/api/incomes'
    Then http status is unauthorized
