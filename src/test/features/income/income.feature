Feature: Income management

  Scenario: List income without user
    When I search my recipes
    Then not found recipes
