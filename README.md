# Employee Management System (EMS) API

A comprehensive REST API for managing employees, attendance, leave requests, and payroll in an organization.

## Table of Contents

- [Overview](#overview)
- [Base URL](#base-url)
- [Authentication](#authentication)
- [API Endpoints](#api-endpoints)
  - [User Management](#user-management)
  - [Authentication](#authentication-endpoints)
  - [Leave Management](#leave-management)
  - [Attendance Management](#attendance-management)
  - [Payroll Management](#payroll-management)
- [Access Levels](#access-levels)
- [Response Formats](#response-formats)
- [Getting Started](#getting-started)

## Overview

The Employee Management System API provides endpoints for:
- User registration and management
- Authentication and authorization
- Leave request management
- Attendance tracking
- Payroll generation and management

## Base URL

```
http://localhost:8080
```

Replace `{{baseURL}}` in all endpoints with your actual server URL.

## Authentication

Most endpoints require authentication using a Bearer Token. Include the token in the Authorization header:

```
Authorization: Bearer <your-token-here>
```

To obtain a token, use the `/auth/login` endpoint.

## API Endpoints

### User Management

#### Register User
Creates a new user in the system.

```http
POST {{baseURL}}/user/create
```

**Access:** ADMIN, AUTHENTICATED  
**Authentication:** Bearer Token Required

**Request Body:**
```json
{
    "name": "John Doe",
    "phone": "9876543210",
    "email": "john.doe@example.com",
    "address": "Anytown, USA",
    "department": "IT",
    "role": "EMPLOYEE",
    "hire_date": "2025-08-01",
    "salary": 50000
}
```

#### Get All Users
Retrieves a list of all users.

```http
GET {{baseURL}}/user/all
```

**Access:** ADMIN, AUTHENTICATED  
**Authentication:** Bearer Token Required

#### Get Current Employee
Retrieves details of the currently authenticated employee.

```http
GET {{baseURL}}/user/me
```

**Access:** ALL USERS, AUTHENTICATED  
**Authentication:** Bearer Token Required

#### Update User Details
Updates the details of an existing user.

```http
PUT {{baseURL}}/user/update
```

**Access:** ALL USERS, AUTHENTICATED  
**Authentication:** Bearer Token Required

**Request Body:**
```json
{
    "name": "Jane Smith",
    "phone": "1234567890",
    "email": "jane.smith@example.com",
    "address": "Somecity, USA",
    "department": "HR",
    "role": "MANAGER"
}
```

#### Change User Role
Changes the role of a specified user.

```http
PUT {{baseURL}}/user/change-role?username={username}&role={role}
```

**Access:** ADMIN, AUTHENTICATED  
**Authentication:** Bearer Token Required

**Query Parameters:**
- `username`: The unique identifier of the user (e.g., user001)
- `role`: The new role for the user (ADMIN, EMPLOYEE, MANAGER)

#### Delete User
Deletes a user from the system.

```http
DELETE {{baseURL}}/user/delete?username={username}
```

**Access:** ADMIN, AUTHENTICATED  
**Authentication:** Bearer Token Required

**Query Parameters:**
- `username`: The unique identifier of the user to delete (e.g., user002)

### Authentication Endpoints

#### Login
Authenticates a user and provides a token.

```http
POST {{baseURL}}/auth/login
```

**Access:** ALL USERS  
**Authentication:** None Required

**Request Body:**
```json
{
    "username": "admin01",
    "password": "password123"
}
```

#### Logout
Logs out the current user.

```http
GET {{baseURL}}/auth/logout
```

**Access:** ALL USERS, AUTHENTICATED  
**Authentication:** Bearer Token Required

### Leave Management

#### Create Leave Request
Submits a new leave request.

```http
POST {{baseURL}}/leaves
```

**Access:** ALL USERS, AUTHENTICATED  
**Authentication:** Bearer Token Required

**Request Body:**
```json
{
    "startDate": "2025-08-10",
    "endDate": "2025-08-15",
    "reason": "Family vacation"
}
```

#### Get All Leave Requests
Retrieves all leave requests.

```http
GET {{baseURL}}/leaves/all
```

**Access:** ADMIN, AUTHENTICATED  
**Authentication:** Bearer Token Required

#### Get Leave Requests by User
Retrieves leave requests for the currently authenticated user.

```http
GET {{baseURL}}/leaves/user
```

**Access:** ALL USERS, AUTHENTICATED  
**Authentication:** Bearer Token Required

#### Approve Leave Request
Approves a specific leave request.

```http
PUT {{baseURL}}/leaves/approve/{leaveId}
```

**Access:** ADMIN, AUTHENTICATED  
**Authentication:** Bearer Token Required

**Path Parameters:**
- `leaveId`: The unique ID of the leave request (e.g., leave-id-12345)

#### Reject Leave Request
Rejects a specific leave request.

```http
PUT {{baseURL}}/leaves/reject/{leaveId}
```

**Access:** ADMIN, AUTHENTICATED  
**Authentication:** Bearer Token Required

**Path Parameters:**
- `leaveId`: The unique ID of the leave request (e.g., leave-id-12345)

### Attendance Management

#### Check-in / Check-out
Records a user's check-in or check-out.

```http
POST {{baseURL}}/attendance/check-in
POST {{baseURL}}/attendance/check-out
```

**Access:** ALL USERS, AUTHENTICATED  
**Authentication:** Bearer Token Required

#### Get All Attendance Records
Retrieves all attendance records.

```http
GET {{baseURL}}/attendance/all
```

**Access:** ADMIN, AUTHENTICATED  
**Authentication:** Bearer Token Required

#### Get User's Attendance
Retrieves attendance records for the currently authenticated user.

```http
GET {{baseURL}}/attendance/user
```

**Access:** ALL USERS, AUTHENTICATED  
**Authentication:** Bearer Token Required

#### Get Today's Attendance
Retrieves today's attendance record for the current user.

```http
GET {{baseURL}}/attendance/user/today
```

**Access:** ALL USERS, AUTHENTICATED  
**Authentication:** Bearer Token Required

### Payroll Management

#### Generate Payroll
Generates payroll for a specific user and month.

```http
POST {{baseURL}}/payroll/generate
```

**Access:** ADMIN, AUTHENTICATED  
**Authentication:** Bearer Token Required

**Request Body:**
```json
{
    "username": "user001",
    "month": "092025"
}
```

#### Get User Payroll
Retrieves all payroll records for a specific user.

```http
GET {{baseURL}}/payroll/user/{username}
```

**Access:** ALL USERS, AUTHENTICATED  
**Authentication:** Bearer Token Required

**Path Parameters:**
- `username`: The unique ID of the user (e.g., user001)

#### Get Payroll by Month
Retrieves a specific user's payroll for a given month.

```http
GET {{baseURL}}/payroll/{username}/{month}
```

**Access:** ALL USERS, AUTHENTICATED  
**Authentication:** Bearer Token Required

**Path Parameters:**
- `username`: The unique ID of the user (e.g., user001)
- `month`: The month and year (e.g., 092025)

#### Get All Payrolls
Retrieves all payroll records for all users.

```http
GET {{baseURL}}/payroll/all
```

**Access:** ADMIN, AUTHENTICATED  
**Authentication:** Bearer Token Required

#### Update Payroll Status
Updates the status of a specific payroll record.

```http
PUT {{baseURL}}/payroll/update-status
```

**Access:** ADMIN, AUTHENTICATED  
**Authentication:** Bearer Token Required

**Request Body:**
```json
{
    "id": "payroll-id-67890",
    "status": "PAID"
}
```

## Access Levels

- **ALL USERS:** Available to all authenticated users
- **ADMIN:** Available only to users with admin privileges
- **AUTHENTICATED:** Requires valid authentication token

## Response Formats

All API responses are in JSON format. Error responses typically include:

```json
{
    "error": "Error message",
    "status": "error_code"
}
```

## Getting Started

1. **Start the server** on your local machine (default: `http://localhost:8080`)

2. **Login** to get an authentication token:
   ```bash
   curl -X POST http://localhost:8080/auth/login \
     -H "Content-Type: application/json" \
     -d '{
       "username": "admin01",
       "password": "password123"
     }'
   ```

3. **Use the token** in subsequent requests:
   ```bash
   curl -X GET http://localhost:8080/user/me \
     -H "Authorization: Bearer <your-token-here>"
   ```

4. **Explore the API** using the endpoints documented above.

## Notes

- Date formats should be in `YYYY-MM-DD` format
- Month format for payroll should be `MMYYYY` (e.g., `092025` for September 2025)
- All timestamps are handled server-side for attendance check-in/check-out
- Ensure proper authorization levels when accessing different endpoints

## Support

For issues or questions about the API, please contact the development team or refer to the system administrator.
