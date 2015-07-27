Feature: Login page

  Scenario: Open login page
    Given I want to use the browser Firefox
    And I navigate to the login page
    Then I see "username" as input
    And I see "password" as input

  Scenario: Login with wrong username and password
    Given I want to use the browser Firefox
    And I navigate to the login page
    When I enter "unknown user" as username
    And I enter "wrong_password" as password
    And I login
    Then I see the following error message "Invalid username and password!"

  Scenario: Login with correct username and password
    Given I want to use the browser Firefox
    And I navigate to the login page
    When I login with username "guest" and password "guest"
    Then the url contains "start.mvc"