Feature: Supplier management

  # List
  Scenario: List supplier without user
    Given the supplier resource
    When I make a get request to the URL '/api/supplier'
    Then http status is forbidden

  Scenario: List supplier with user
    Given the supplier resource
    And with the user 'user001' and password 'password'
    When I make a get request to the URL '/api/supplier'
    Then http status is ok

  # Create
  Scenario: Create supplier without user
    Given the supplier resource
    And a good supplierDTO for the user001
    When I make a post request to the URL '/api/supplier'
    Then http status is forbidden

  Scenario: Create supplier with user
    Given the supplier resource
    And with the user 'user001' and password 'password'
    And a good supplierDTO for the user001
    And count the user's suppliers
    When I make a post request to the URL '/api/supplier'
    Then http status is created
    Then the supplier is creating
    Then count the user's suppliers and it has increse 1

  Scenario: Create supplier with user without name
    Given the supplier resource
    And with the user 'user001' and password 'password'
    And a supplierDTO for the user001 without name
    And count the user's suppliers
    When I make a post request to the URL '/api/supplier'
    Then http status is bad request
    Then count the user's suppliers and it has increse 0

  # Update
  Scenario: Update supplier without user
    Given the supplier resource
    And a good supplierDTO for the user001
    When I make a put request to the URL '/api/supplier/1'
    Then http status is forbidden

  Scenario: Update supplier with user
    Given the supplier resource
    And with the user 'user001' and password 'password'
    And a good supplierDTO for the user001
    And count the user's suppliers
    When I make a put request to the URL '/api/supplier/1'
    Then http status is ok
    Then the supplier 1 is updating
    Then count the user's suppliers and it has increse 0

  Scenario: Update supplier with other user
    Given the supplier resource
    And with the user 'user002' and password 'password'
    And a good supplierDTO for the user001
    And count the user's suppliers
    When I make a put request to the URL '/api/supplier/1'
    Then http status is bad request
    Then the supplier 1 is not updating
    Then count the user's suppliers and it has increse 0

  Scenario: Update supplier not created with user
    Given the supplier resource
    And with the user 'user002' and password 'password'
    And a good supplierDTO for the user001
    And count the user's suppliers
    When I make a put request to the URL '/api/supplier/700000'
    Then http status is bad request
    Then count the user's suppliers and it has increse 0

  Scenario: Update supplier without name with user
    Given the supplier resource
    And with the user 'user002' and password 'password'
    And a supplierDTO for the user001 without name
    And count the user's suppliers
    When I make a put request to the URL '/api/supplier/3'
    Then http status is bad request
    Then the supplier 3 is not updating
    Then count the user's suppliers and it has increse 0

  #Delete
  Scenario: Delete supplier without user
    Given the supplier resource
    When I make a delete request to the URL '/api/supplier/1'
    Then http status is forbidden

  Scenario: Delete supplier with user
    Given the supplier resource
    And with the user 'user001' and password 'password'
    And count the user's suppliers
    When I make a delete request to the URL '/api/supplier/81'
    Then http status is ok
    Then the supplier 81 is delete
    Then count the user's suppliers and it has increse -1

  Scenario: Delete supplier with other user
    Given the supplier resource
    And with the user 'user002' and password 'password'
    And count the user's suppliers
    When I make a delete request to the URL '/api/supplier/81'
    Then http status is bad request
    Then count the user's suppliers and it has increse 0

  Scenario: Delete supplier not created with user
    Given the supplier resource
    And with the user 'user002' and password 'password'
    And count the user's suppliers
    When I make a delete request to the URL '/api/supplier/700000'
    Then http status is bad request
    Then count the user's suppliers and it has increse 0

  Scenario: Delete supplier with user and invoices
    Given the supplier resource
    And with the user 'user001' and password 'password'
    And count the user's suppliers
    When I make a delete request to the URL '/api/supplier/1'
    Then http status is bad request
    Then the supplier 1 is not delete
    Then count the user's suppliers and it has increse 0
