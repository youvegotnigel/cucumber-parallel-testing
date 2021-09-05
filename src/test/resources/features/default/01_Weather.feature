@regression
Feature: Search Weather Test

  Background: User navigates to Application URL
    Given The Application has been launched

  Scenario Outline: User should not be able to login with invalid credential
    When I enter '<city>' in search box
    Then I should see city name '<city>' displayed

    Examples:
      | city    |
      | Negombo |