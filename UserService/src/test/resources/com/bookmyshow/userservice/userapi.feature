Feature: User API

  Scenario: Add a new User
    Given firstname, lastname, email, role
    When made a post request to create user
    Then User should be created and persisted in database
