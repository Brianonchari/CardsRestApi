# Cards REST API

This project implements a REST API for managing cards. Users can create, update, and search for cards based on various criteria. Authentication is required to access the API, and users have different levels of access based on their roles.

## Endpoints

The following endpoints are available in the API:

| Endpoint                       | Method | Description                                          |
|-------------------------------------|--------|------------------------------------------------------|
| /api/cards                          | GET    | Get all cards                                        |
| /api/cards/{cardId}/users/{userId}  | GET    | Get a specific card by ID                            |
| /api/cards                          | POST   | Create a new card                                    |
| /api/cards/{cardId}                 | PUT    | Update an existing card by ID                        |
| /api/cards/{cardId}                 | DELETE | Delete a card by ID                                  |
| /api/cards/search                   | GET    | Search for cards based on filters and criteria       |
| /api/users/{userId}/cards           | GET    | Get all cards for a specific user                    |

## Users

The following table lists the users along with their names and passwords:

| User ID | Name  | Password   |
|---------|-------|------------|
| 1       | John  | password   |
| 2       | Jack  | password   |


Feel free to populate the database with these users for testing purposes.

## Authentication

Authentication is required to access the API endpoints. Users can authenticate by providing their credentials (username and password) in the request headers. Upon successful authentication, a JSON Web Token (JWT) will be returned, which should be included in the `Authorization` header of subsequent requests as a Bearer token.

For example:
```
GET /api/cards
Authorization: Bearer <JWT>
```
