# User Registration REST API

A RESTful User Registration API built using Java and Spring Boot. The application manages user records with MySQL persistence, input validation, password hashing, and structured exception handling.

## Technologies Used

* Java 23
* Spring Boot 2.7.18
* Spring Data JPA
* MySQL
* Maven
* BCrypt password hashing
* Bean Validation
* Postman

## Features

* Register a new user
* Retrieve all users
* Retrieve a user by ID
* Update user details
* Delete a user
* Validate names, email addresses, and password length
* Prevent duplicate email registrations
* Return meaningful HTTP error responses
* Exclude password hashes from user response DTOs

## API Endpoints

| Method | Endpoint          | Purpose             |
| ------ | ----------------- | ------------------- |
| POST   | `/users/register` | Register a user     |
| GET    | `/users`          | Retrieve all users  |
| GET    | `/users/{id}`     | Retrieve a user     |
| PUT    | `/users/{id}`     | Update user details |
| DELETE | `/users/{id}`     | Delete a user       |

## HTTP Status Codes

* `201 Created` — User registered successfully
* `200 OK` — Data retrieved or updated successfully
* `204 No Content` — User deleted successfully
* `400 Bad Request` — Input validation failed
* `404 Not Found` — User does not exist
* `409 Conflict` — Email is already registered

## Setup Instructions

1. Install Java and MySQL.
2. Create a MySQL database named `user_registration`.
3. Configure your database URL, username, and password in `application.properties`.
4. Open the project as a Maven project in Eclipse.
5. Run the Spring Boot application.
6. Use Postman to test the endpoints.

## Security Notes

* Passwords are hashed using BCrypt before storage.
* Password hashes are excluded from user response DTOs.
* Configure database credentials outside public source control before publishing.

## Future Improvements

* Login and authentication
* JWT-based authorization
* Automated unit and integration tests
* API documentation using OpenAPI/Swagger
