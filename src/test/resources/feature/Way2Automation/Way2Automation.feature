##Author: Bhavana Boganatham
##Keywords Summary : This feature file is to test API service validations for Way2 Automation service
@way2Automation @MVP1
Feature: Way2 Automation Service

  @getALLUsers @TC001
  Scenario Outline: Verify the response for getAllUsers service
    Given The EndPoint is setup for wayToAutomation service
    When I hit the GET request for the scenario "<Scenario>" for getAllUsers service
    Then I validate Event Status Response Code "<ResponseCode>", Response Message "<ResponseMessage>" for the Scenario Type "<ScenarioType>" and Scenario "<Scenario>" for getAllUsers service

    Examples: 
      | ScenarioType | Scenario                                 		    | ResponseCode | ResponseMessage |
      | Valid        | Get All Users when valid data is provided 			|          200 | OK              |
      | Invalid      | Get All Users when invalid resource path is provided |          404 | Not Found       |
      
  @createUser @TC002
  Scenario Outline: Verify the response for createUser service
    Given The EndPoint is setup for wayToAutomation service
    When I hit the POST request for the scenario "<Scenario>" for createUser service
    Then I validate Event Status Response Code "<ResponseCode>", Response Message "<ResponseMessage>" for the Scenario Type "<ScenarioType>" and Scenario "<Scenario>" for createUser service

    Examples: 
      | ScenarioType | Scenario                                 		       	  | ResponseCode | ResponseMessage |
      | Valid        | Post Create User with valid data is provided			      |          201 | Created         |
      | Invalid      | Post Create User when invalid resource path is provided 	  |          404 | Not Found       |
      | Invalid      | Post Create User when email id is provided as empty array  |          400 | Bad Request     |
	  | Invalid      | Post Create User when email id is provided as empty list   |          400 | Bad Request     |
	  | Invalid      | Post Create User when firstName is provided as empty array |          400 | Bad Request     |
	  | Invalid      | Post Create User when firstName is provided as empty list  |          400 | Bad Request     |
	  | Invalid      | Post Create User when lastName is provided as empty array  |          400 | Bad Request     |
	  | Invalid      | Post Create User when lastName is provided as empty list   |          400 | Bad Request     |
	  
  @getSingleUser @TC003
  Scenario Outline: Verify the response for getSingleUser service
    Given The EndPoint is setup for wayToAutomation service
    When I hit the GET request for the scenario "<Scenario>" for getSingleUser service
    Then I validate Event Status Response Code "<ResponseCode>", Response Message "<ResponseMessage>" for the Scenario Type "<ScenarioType>" and Scenario "<Scenario>" for getSingleUser service

    Examples: 
      | ScenarioType | Scenario                                 		        | ResponseCode | ResponseMessage |
      | Valid        | Get Single User when valid data is provided 		        |          200 | OK              |
      | Invalid      | Get Single User when invalid resource path is provided   |          404 | Not Found       |
      | Invalid      | Get Single User when invalid user id is provided in path |          404 | Not Found       |
      
  @updateUser @TC004
  Scenario Outline: Verify the response for updateUser service
    Given The EndPoint is setup for wayToAutomation service
    When I hit the PUT request for the scenario "<Scenario>" for updateUser service
    Then I validate Event Status Response Code "<ResponseCode>", Response Message "<ResponseMessage>" for the Scenario Type "<ScenarioType>" and Scenario "<Scenario>" for updateUser service

    Examples: 
      | ScenarioType | Scenario                                 		     	 | ResponseCode | ResponseMessage |
      | Valid        | Put Update User with valid data is provided		         |          201 | Created         |
      | Invalid      | Put Update User when invalid resource path is provided    |          404 | Not Found       |
      | Invalid      | Put Update User when email id is provided as empty array  |          400 | Bad Request     |
	  | Invalid      | Put Update User when email id is provided as empty list   |          400 | Bad Request     |
	  | Invalid      | Put Update User when firstName is provided as empty array |          400 | Bad Request     |
	  | Invalid      | Put Update User when firstName is provided as empty list  |          400 | Bad Request     |
	  | Invalid      | Put Update User when lastName is provided as empty array  |          400 | Bad Request     |
	  | Invalid      | Put Update User when lastName is provided as empty list   |          400 | Bad Request     |
	  
  @deleteUser @TC005
  Scenario Outline: Verify the response for deleteUser service
    Given The EndPoint is setup for wayToAutomation service
    When I hit the DELETE request for the scenario "<Scenario>" for deleteUser service
    Then I validate Event Status Response Code "<ResponseCode>", Response Message "<ResponseMessage>" for the Scenario Type "<ScenarioType>" and Scenario "<Scenario>" for deleteUser service

    Examples: 
      | ScenarioType | Scenario                                 		    | ResponseCode | ResponseMessage |
      | Valid        | Delete User when valid data is provided  		    |          200 | OK              |
      | Invalid      | Delete User when invalid user id is provided in path |          304 | Not Modified    |
      | Invalid      | Delete User when invalid resource path is provided   |          404 | Not Found       |
	
      
 