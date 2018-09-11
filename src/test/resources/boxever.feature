Feature: Boxever Test

  Scenario: Create a public gist
    When I access the gist endpoint
    Then I create a new Gist with json createGist.json successfully

  Scenario: Get gist
    When I access the gist endpoint
    And I have a gist already created with the json createGist.json
    Then I get the gist and contains the expected values

  Scenario: Delete gist
    When I access the gist endpoint
    And I have a gist already created with the json createGist.json
    And I delete the gist
    Then the gist is deleted