Feature: InvoiceLine statistic

  # Evolution
  Scenario: List invoiceLine evolution without user
    Given the invoiceLine resource
    And adding the param 'start' with the value '2016-01-01'
    And adding the param 'end' with the value '2016-02-01'
    When I make a get request to the URL '/api/invoice_line/statistic/evolution'
    Then http status is forbidden

  Scenario: List invoiceLine evolution with user
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And adding the param 'start' with the value '2016-01-01'
    And adding the param 'end' with the value '2016-02-01'
    When I make a get request to the URL '/api/invoice_line/statistic/evolution'
    Then http status is ok
    And exist the key '2016-01-01'
    And exist the key '2016-01-15'
    And exist the key '2016-01-31'
    And no exist the key '2016-02-01'
    And no exist the key '2015-12-31'