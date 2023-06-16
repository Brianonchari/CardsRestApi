# Cards REST API

This project implements a REST API for managing cards. Users can create, update, and search for cards based on various criteria. Authentication is required to access the API, and users have different levels of access based on their roles.

## Running the Project

To run the project, follow these steps:

1. Make sure you have Java 8 or above and Maven installed on your system.
2. Clone the project repository to your local machine.
3. Open a terminal or command prompt and navigate to the project directory.
4. Build the project using the following command:

## Endpoints

The following endpoints are available in the API:

| Endpoint                           | Method | Description                                          |
|------------------------------------|--------|------------------------------------------------------|
| /api/auth/login                    | POST   | Get all cards                                        |
| /api/auth/token                    | POST   | Get all cards                                        |
 | /api/cards                         | GET    | Get all cards                                        |
| /api/cards/{cardId}/users/{userId} | GET    | Get a specific card by ID                            |
| /api/cards                         | POST   | Create a new card                                    |
| /api/cards/{cardId}                | PUT    | Update an existing card by ID                        |
| /api/cards/{cardId}                | DELETE | Delete a card by ID                                  |
| /api/cards/search                  | GET    | Search for cards based on filters and criteria       |
| /api/users/{userId}/cards          | GET    | Get all cards for a specific user                    |

## Users

The following table lists the users along with their names and passwords:

| User ID | Name                | Password   |
|---------|---------------------|------------|
| 1       | john@logicea.com    | password   |
| 2       | jack@logicea.com    | password   |


Feel free to populate the database with these users for testing purposes.

## Authentication

Authentication is required to access the API endpoints. Users can authenticate by providing their credentials (username and password) in the request headers. Upon successful authentication, a JSON Web Token (JWT) will be returned, which should be included in the `Authorization` header of subsequent requests as a Bearer token.

For example:
```
GET /api/cards
Authorization: Bearer <JWT>
```

## Code Coverage with JaCoCo

To measure code coverage using JaCoCo, follow these instructions:

1. Ensure that you have added the JaCoCo plugin to your project's pom.xml file.
2. Run the tests for your project. This will generate the coverage data.
3. To view the code coverage report, execute the following command `mvn jacoco:report`
   This command will generate an HTML report in the target/site/jacoco directory.
