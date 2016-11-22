Feature: Operation management

  # List
  Scenario: List operation without user
    Given the operation resource
    When I make a get request to the URL '/api/operation'
    Then http status is forbidden

  Scenario: List operation with user
    Given the operation resource
    And with the user 'user001' and password 'password'
    When I make a get request to the URL '/api/operation'
    Then http status is ok

  # Create
  Scenario: Create operation without user
    Given the operation resource
    And a good operationDTO for the user001
    When I make a post request to the URL '/api/operation'
    Then http status is forbidden

  Scenario: Create operation with user
    Given the operation resource
    And with the user 'user001' and password 'password'
    And a good operationDTO for the user001
    And count the user's operations
    When I make a post request to the URL '/api/operation'
    Then http status is created
    Then the operation is creating
    Then count the user's operations and it has increse 1

  Scenario: Create operation with user without name with user
    Given the operation resource
    And with the user 'user001' and password 'password'
    And a operationDTO for the user001 without name
    And count the user's operations
    When I make a post request to the URL '/api/operation'
    Then http status is bad request
    Then count the user's operations and it has increse 0

  Scenario: Create operation with user and the section of other user
    Given the operation resource
    And with the user 'user001' and password 'password'
    And a operationDTO for the user001 with the section of other user
    And count the user's operations
    When I make a post request to the URL '/api/operation'
    Then http status is bad request
    Then count the user's operations and it has increse 0

  # Update
  Scenario: Update operation without user
    Given the operation resource
    And a good operationDTO for the user001
    When I make a put request to the URL '/api/operation/1'
    Then http status is forbidden

  Scenario: Update operation with user
    Given the operation resource
    And with the user 'user001' and password 'password'
    And a good operationDTO for the user001
    And count the user's operations
    When I make a put request to the URL '/api/operation/1'
    Then http status is ok
    Then the operation 1 is updating
    Then count the user's operations and it has increse 0

  Scenario: Update operation with other user
    Given the operation resource
    And with the user 'user002' and password 'password'
    And a good operationDTO for the user001
    And count the user's operations
    When I make a put request to the URL '/api/operation/1'
    Then http status is bad request
    Then the operation 1 is not updating
    Then count the user's operations and it has increse 0

  Scenario: Update operation not created with user
    Given the operation resource
    And with the user 'user002' and password 'password'
    And a good operationDTO for the user001
    And count the user's operations
    When I make a put request to the URL '/api/operation/700000'
    Then http status is bad request
    Then the operation 1 is not updating
    Then count the user's operations and it has increse 0

  Scenario: Update operation without name with user
    Given the operation resource
    And with the user 'user001' and password 'password'
    And a operationDTO for the user001 without name
    And count the user's operations
    When I make a put request to the URL '/api/operation/1'
    Then http status is bad request
    Then the operation 1 is not updating
    Then count the user's operations and it has increse 0

  Scenario: Update operation with user and the section of other user
    Given the operation resource
    And with the user 'user001' and password 'password'
    And a operationDTO for the user001 with the section of other user
    And count the user's operations
    When I make a put request to the URL '/api/operation/1'
    Then http status is bad request
    Then the operation 1 is not updating
    Then count the user's operations and it has increse 0

  #Delete
  Scenario: Delete operation without user
    Given the operation resource
    When I make a delete request to the URL '/api/operation/1'
    Then http status is forbidden

  Scenario: Delete operation with user
    Given the operation resource
    And with the user 'user001' and password 'password'
    And count the user's operations
    When I make a delete request to the URL '/api/operation/9'
    Then http status is ok
    Then the operation 1 is delete
    Then count the user's operations and it has increse -1

  Scenario: Delete operation with other user
    Given the operation resource
    And with the user 'user002' and password 'password'
    And count the user's operations
    When I make a delete request to the URL '/api/operation/9'
    Then http status is bad request
    Then count the user's operations and it has increse 0

  Scenario: Delete operation not created with user
    Given the operation resource
    And with the user 'user002' and password 'password'
    And count the user's operations
    When I make a delete request to the URL '/api/operation/700000'
    Then http status is bad request
    Then count the user's operations and it has increse 0

  Scenario: Delete operation with user and invoices
    Given the operation resource
    And with the user 'user001' and password 'password'
    And count the user's operations
    When I make a delete request to the URL '/api/operation/1'
    Then http status is bad request
    Then the operation 1 is not delete
    Then count the user's operations and it has increse 0