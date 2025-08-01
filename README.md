# Employee Management System (EMS) - API Documentation

## Introduction

Welcome to the Employee Management System (EMS) API. This document provides a comprehensive guide for developers. The system is designed to manage users (employees) and their leave requests through a RESTful API. It's intended for use by front-end applications or other services that need to programmatically interact with employee and leave data.

This API uses JSON for all data interchange and standard HTTP response codes to indicate the status of requests.

## Getting Started

### Base URL
All API endpoints are relative to the following base URL:
`http://localhost:8080`

### Authentication
Most endpoints are protected and require authentication. The EMS API uses JSON Web Tokens (JWT) for this purpose.

To authenticate, you must first call the `/auth/login` endpoint with a valid username and password. The server will respond with a JWT Bearer token. This token must then be included in the `Authorization` header for all subsequent requests to protected endpoints.

**Example Header:**
`Authorization: Bearer <your_jwt_token>`

### User Roles
The API defines two primary user roles, which dictate access to certain endpoints:
* **EMPLOYEE:** Standard users. They can manage their own profile and leave requests.
* **ADMIN:** Privileged users. They have full access to manage all users and all leave requests in the system.

---

## API Endpoints

### 1. Authentication

#### Login
Authenticates a user and provides a JWT token required for accessing protected routes.

* **Endpoint:** `POST /auth/login`
* **Access:** Public
* **Request Body:**
    ```json
    {
        "username": "your_username",
        "password": "your_password"
    }
    ```
* **Success Response (200 OK):**
    ```json
    {
        "token": "ey...",
        "username": "your_username"
    }
    ```
* **Error Response (401 Unauthorized):**
    ```json
    {
        "timestamp": "2025-08-01T04:30:00.000+00:00",
        "status": 401,
        "error": "Unauthorized",
        "message": "Invalid credentials",
        "path": "/auth/login"
    }
    ```

---

### 2. User Management

#### Create a New User (Register)
Creates a new user account.

* **Endpoint:** `POST /user/create`
* **Access:** ADMIN only
* **Authorization:** Bearer Token
* **Request Body:**
    ```json
    {
        "name": "Full Name",
        "phone": "1234567890",
        "email": "user@example.com",
        "address": "User Address",
        "department": "IT",
        "role": "EMPLOYEE"
    }
    ```
* **Success Response (201 Created):** Returns the details of the newly created user.
* **Error Responses:**
    * `400 Bad Request`: If the request body contains invalid data (e.g., missing fields, invalid email format).
    * `403 Forbidden`: If a non-admin user attempts to access this endpoint.

#### Get All Users
Retrieves a list of all users in the system.

* **Endpoint:** `GET /user/all`
* **Access:** ADMIN only
* **Authorization:** Bearer Token
* **Success Response (200 OK):** Returns an array of user objects.
* **Error Response (403 Forbidden):** If a non-admin user attempts to access this endpoint.

#### Get Logged-in User Details
Retrieves the profile information of the currently authenticated user.

* **Endpoint:** `GET /user/me`
* **Access:** EMPLOYEE, ADMIN
* **Authorization:** Bearer Token
* **Success Response (200 OK):** Returns the user object for the authenticated user.
* **Error Response (401 Unauthorized):** If the JWT is missing or invalid.

#### Update User Details
Updates the profile information for the currently authenticated user.

* **Endpoint:** `PUT /user/update`
* **Access:** EMPLOYEE, ADMIN
* **Authorization:** Bearer Token
* **Request Body:**
    ```json
    {
        "name": "Updated Name",
        "phone": "0987654321",
        "email": "updated@example.com",
        "address": "Updated Address",
        "department": "HR"
    }
    ```
* **Success Response (200 OK):** Returns the updated user object.
* **Error Response (400 Bad Request):** If the request body contains invalid data.

#### Change User Role
Allows an administrator to modify the role of any user.

* **Endpoint:** `PUT /user/change-role`
* **Access:** ADMIN only
* **Authorization:** Bearer Token
* **Query Parameters:**
    * `username` (string, required): The username of the user to modify (e.g., `EMP0001`).
    * `role` (string, required): The new role to assign (`EMPLOYEE` or `ADMIN`).
* **Success Response (200 OK):** A confirmation message.
* **Error Responses:**
    * `403 Forbidden`: If a non-admin user attempts access.
    * `404 Not Found`: If the specified `username` does not exist.

#### Delete a User
Permanently removes a user from the system.

* **Endpoint:** `DELETE /user/delete`
* **Access:** ADMIN only
* **Authorization:** Bearer Token
* **Query Parameters:**
    * `username` (string, required): The username of the user to delete.
* **Success Response (200 OK):** A confirmation message.
* **Error Responses:**
    * `403 Forbidden`: If a non-admin user attempts access.
    * `404 Not Found`: If the specified `username` does not exist.

---

### 3. Leave Management

#### Apply for Leave
Allows an authenticated user to submit a new leave request.

* **Endpoint:** `POST /leaves`
* **Access:** EMPLOYEE, ADMIN
* **Authorization:** Bearer Token
* **Request Body:**
    ```json
    {
        "startDate": "YYYY-MM-DD",
        "endDate": "YYYY-MM-DD",
        "reason": "Reason for taking leave."
    }
    ```
* **Success Response (201 Created):** Returns the newly created leave request object.
* **Error Response (400 Bad Request):** If dates are invalid (e.g., `endDate` is before `startDate`) or the reason is empty.

#### Get All Leave Requests
Retrieves all leave requests from all users.

* **Endpoint:** `GET /leaves/all`
* **Access:** ADMIN only
* **Authorization:** Bearer Token
* **Success Response (200 OK):** Returns an array of all leave request objects.
* **Error Response (403 Forbidden):** If a non-admin user attempts access.

#### Get Leave Requests by User
Retrieves all leave requests submitted by the currently authenticated user.

* **Endpoint:** `GET /leaves/user`
* **Access:** EMPLOYEE, ADMIN
* **Authorization:** Bearer Token
* **Success Response (200 OK):** Returns an array of the user's leave requests.

#### Approve a Leave Request
Approves a pending leave request.

* **Endpoint:** `PUT /leaves/approve/{leaveId}`
* **Access:** ADMIN only
* **Authorization:** Bearer Token
* **Path Variable:**
    * `leaveId` (string, required): The unique ID of the leave request.
* **Success Response (200 OK):** Returns the updated leave request object with status `APPROVED`.
* **Error Responses:**
    * `403 Forbidden`: If a non-admin user attempts access.
    * `404 Not Found`: If the `leaveId` does not correspond to an existing leave request.

#### Reject a Leave Request
Rejects a pending leave request.

* **Endpoint:** `PUT /leaves/reject/{leaveId}`
* **Access:** ADMIN only
* **Authorization:** Bearer Token
* **Path Variable:**
    * `leaveId` (string, required): The unique ID of the leave request.
* **Success Response (200 OK):** Returns the updated leave request object with status `REJECTED`.
* **Error Responses:**
    * `403 Forbidden`: If a non-admin user attempts access.
    * `404 Not Found`: If the `leaveId` does not correspond to an existing leave request.
