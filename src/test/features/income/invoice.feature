Feature: Invoice management

  # List
  Scenario: List invoice without user
    Given the invoice resource
    When I make a get request to the URL '/api/invoice'
    Then http status is forbidden

  Scenario: List invoice with user
    Given the invoice resource
    And with the user 'user001' and password 'password'
    When I make a get request to the URL '/api/invoice'
    Then http status is ok

  # Create
  Scenario: Create invoice without user
    Given the invoice resource
    And a good invoiceDTO
    When I make a post request to the URL '/api/invoice'
    Then http status is forbidden

  Scenario: Create invoice with user
    Given the invoice resource
    And with the user 'user001' and password 'password'
    And a good invoiceDTO
    And count the user's invoices
    When I make a post request to the URL '/api/invoice'
    Then http status is created
    Then the invoice is creating
    Then count the user's invoices and it has increse 1

  Scenario: Create invoice without name with user
    Given the invoice resource
    And with the user 'user001' and password 'password'
    And a invoiceDTO without name
    When I make a post request to the URL '/api/invoice'
    Then http status is bad request

  # Update
  Scenario: Update invoice without user
    Given the invoice resource
    And a good invoiceDTO
    When I make a put request to the URL '/api/invoice/7'
    Then http status is forbidden

  Scenario: Update invoice with user
    Given the invoice resource
    And with the user 'user001' and password 'password'
    And a good invoiceDTO
    And count the user's invoices
    When I make a put request to the URL '/api/invoice/7'
    Then http status is ok
    Then the invoice 7 is updating
    Then count the user's invoices and it has increse 0

  Scenario: Update invoice with other user
    Given the invoice resource
    And with the user 'user002' and password 'password'
    And a good invoiceDTO
    And count the user's invoices
    When I make a put request to the URL '/api/invoice/7'
    Then http status is bad request
    Then count the user's invoices and it has increse 0

  Scenario: Update invoice not created with user
    Given the invoice resource
    And with the user 'user002' and password 'password'
    And a good invoiceDTO
    And count the user's invoices
    When I make a put request to the URL '/api/invoice/700000'
    Then http status is bad request
    Then count the user's invoices and it has increse 0

  Scenario: Update invoice without name with user
    Given the invoice resource
    And with the user 'user001' and password 'password'
    And a invoiceDTO without name
    When I make a put request to the URL '/api/invoice/7'
    Then http status is bad request

  #Delete
  Scenario: Delete invoice without user
    Given the invoice resource
    When I make a delete request to the URL '/api/invoice/7'
    Then http status is forbidden

  Scenario: Delete invoice with user
    Given the invoice resource
    And with the user 'user001' and password 'password'
    And count the user's invoices
    When I make a delete request to the URL '/api/invoice/7'
    Then http status is ok
    Then the invoice 7 is delete
    Then count the user's invoices and it has increse -1

  Scenario: Delete invoice with other user
    Given the invoice resource
    And with the user 'user002' and password 'password'
    And count the user's invoices
    When I make a delete request to the URL '/api/invoice/7'
    Then http status is bad request
    Then count the user's invoices and it has increse 0

  Scenario: Delete invoice not created with user
    Given the invoice resource
    And with the user 'user002' and password 'password'
    And count the user's invoices
    When I make a delete request to the URL '/api/invoice/700000'
    Then http status is bad request
    Then count the user's invoices and it has increse 0
