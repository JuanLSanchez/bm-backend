Feature: InvoiceLine management

  # List
  Scenario: List invoiceLine without user
    Given the invoiceLine resource
    When I make a get request to the URL '/api/invoice_line'
    Then http status is forbidden

  Scenario: List invoiceLine with user
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    When I make a get request to the URL '/api/invoice_line'
    Then http status is ok

  # List of invoice
  Scenario: List invoiceLine without user
    Given the invoiceLine resource
    When I make a get request to the URL '/api/invoice_line/invoice/4'
    Then http status is forbidden

  Scenario: List invoiceLine with user
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    When I make a get request to the URL '/api/invoice_line/invoice/4'
    Then http status is ok

  Scenario: List invoiceLine with other user
    Given the invoiceLine resource
    And with the user 'user002' and password 'password'
    When I make a get request to the URL '/api/invoice_line/invoice/4'
    Then http status is bad request

  # Create
  Scenario: Create invoiceLine without user
    Given the invoiceLine resource
    And a good invoiceLineDTO for user001
    When I make a post request to the URL '/api/invoice_line'
    Then http status is forbidden

  Scenario: Create invoiceLine with user
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a good invoiceLineDTO for user001
    And count the user's invoiceLines
    When I make a post request to the URL '/api/invoice_line'
    Then http status is created
    Then the invoiceLine is creating
    Then count the user's invoiceLines and it has increse 1

  Scenario: Create invoiceLine with user and without iva
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a invoiceLineDTO for user001 without iva
    And count the user's invoiceLines
    When I make a post request to the URL '/api/invoice_line'
    Then http status is bad request
    Then count the user's invoiceLines and it has increse 0

  Scenario: Create invoiceLine with user and without base
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a invoiceLineDTO for user001 without base
    And count the user's invoiceLines
    When I make a post request to the URL '/api/invoice_line'
    Then http status is bad request
    Then count the user's invoiceLines and it has increse 0

  Scenario: Create invoiceLine with user and iva great that 100
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a invoiceLineDTO for user001 with iva great that 100
    And count the user's invoiceLines
    When I make a post request to the URL '/api/invoice_line'
    Then http status is bad request
    Then count the user's invoiceLines and it has increse 0

  Scenario: Create invoiceLine with user and invoice of other user
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a good invoiceLineDTO for user003
    And count the user's invoiceLines
    When I make a post request to the URL '/api/invoice_line'
    Then http status is bad request
    Then count the user's invoiceLines and it has increse 0

  Scenario: Create invoiceLine with user and iva less that 0
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a invoiceLineDTO for user001 with iva less that 0
    And count the user's invoiceLines
    When I make a post request to the URL '/api/invoice_line'
    Then http status is bad request
    Then count the user's invoiceLines and it has increse 0

  Scenario: Create invoiceLine with user and without invoice id
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a invoiceLineDTO without invoice
    And count the user's invoiceLines
    When I make a post request to the URL '/api/invoice_line'
    Then http status is bad request
    Then count the user's invoiceLines and it has increse 0

  # Update
  Scenario: Update invoiceLine without user
    Given the invoiceLine resource
    And a good invoiceLineDTO for user001
    When I make a put request to the URL '/api/invoice_line/1'
    Then http status is forbidden

  Scenario: Update invoiceLine with user
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a good invoiceLineDTO for user001
    And count the user's invoiceLines
    When I make a put request to the URL '/api/invoice_line/1'
    Then http status is ok
    Then the invoiceLine 1 is updating
    Then count the user's invoiceLines and it has increse 0

  Scenario: Update invoiceLine with user and without iva
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a invoiceLineDTO for user001 without iva
    And count the user's invoiceLines
    When I make a put request to the URL '/api/invoice_line/1'
    Then http status is bad request
    Then the invoiceLine 1 is not updating
    Then count the user's invoiceLines and it has increse 0

  Scenario: Update invoiceLine with user and without base
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a invoiceLineDTO for user001 without base
    And count the user's invoiceLines
    When I make a put request to the URL '/api/invoice_line/1'
    Then http status is bad request
    Then the invoiceLine 1 is not updating
    Then count the user's invoiceLines and it has increse 0

  Scenario: Update invoiceLine with user and iva great that 100
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a invoiceLineDTO for user001 with iva great that 100
    And count the user's invoiceLines
    When I make a put request to the URL '/api/invoice_line/1'
    Then http status is bad request
    Then the invoiceLine 1 is not updating
    Then count the user's invoiceLines and it has increse 0

  Scenario: Update invoiceLine with user and iva less that 0
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a invoiceLineDTO for user001 with iva less that 0
    And count the user's invoiceLines
    When I make a put request to the URL '/api/invoice_line/1'
    Then http status is bad request
    Then the invoiceLine 1 is not updating
    Then count the user's invoiceLines and it has increse 0

  Scenario: Update invoiceLine with user and update the invoice for the invoice of other user
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a good invoiceLineDTO for user003
    And count the user's invoiceLines
    When I make a put request to the URL '/api/invoice_line/1'
    Then http status is bad request
    Then the invoiceLine 1 is not updating
    Then count the user's invoiceLines and it has increse 0

  Scenario: Update invoiceLine with user and without invoice
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And a invoiceLineDTO without invoice
    And count the user's invoiceLines
    When I make a put request to the URL '/api/invoice_line/1'
    Then http status is bad request
    Then the invoiceLine 1 is not updating
    Then count the user's invoiceLines and it has increse 0

  Scenario: Update invoiceLine with other user
    Given the invoiceLine resource
    And with the user 'user002' and password 'password'
    And a good invoiceLineDTO for user001
    And count the user's invoiceLines
    When I make a put request to the URL '/api/invoice_line/1'
    Then http status is bad request
    Then count the user's invoiceLines and it has increse 0

  Scenario: Update invoiceLine not created with user
    Given the invoiceLine resource
    And with the user 'user002' and password 'password'
    And a good invoiceLineDTO for user001
    And count the user's invoiceLines
    When I make a put request to the URL '/api/invoice_line/700000'
    Then http status is bad request
    Then count the user's invoiceLines and it has increse 0

  #Delete
  Scenario: Delete invoiceLine without user
    Given the invoiceLine resource
    When I make a delete request to the URL '/api/invoice_line/1'
    Then http status is forbidden

  Scenario: Delete invoiceLine with user
    Given the invoiceLine resource
    And with the user 'user001' and password 'password'
    And count the user's invoiceLines
    When I make a delete request to the URL '/api/invoice_line/1'
    Then http status is ok
    Then the invoiceLine 1 is delete
    Then count the user's invoiceLines and it has increse -1

  Scenario: Delete invoiceLine with other user
    Given the invoiceLine resource
    And with the user 'user002' and password 'password'
    And count the user's invoiceLines
    When I make a delete request to the URL '/api/invoice_line/1'
    Then http status is bad request
    Then count the user's invoiceLines and it has increse 0

  Scenario: Delete invoiceLine not created with user
    Given the invoiceLine resource
    And with the user 'user002' and password 'password'
    And count the user's invoiceLines
    When I make a delete request to the URL '/api/invoice_line/700000'
    Then http status is bad request
    Then count the user's invoiceLines and it has increse 0
