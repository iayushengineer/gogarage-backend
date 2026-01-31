# Car Service Station API - Postman Collection

This folder contains Postman collection and environment files for testing the Car Service Station API.

## Files

- `Car-Service-API.postman_collection.json` - Complete API collection with all endpoints
- `Car-Service-Environment.postman_environment.json` - Environment variables and credentials
- `README.md` - This documentation file

## Setup Instructions

### 1. Import Collection and Environment

1. Open Postman
2. Click **Import** button
3. Import both files:
   - `Car-Service-API.postman_collection.json`
   - `Car-Service-Environment.postman_environment.json`

### 2. Select Environment

1. In Postman, select **Car Service Environment** from the environment dropdown (top right)
2. The environment contains pre-configured variables for base URL and credentials

### 3. Start the Application

Make sure your Spring Boot application is running:
```bash
cd car-service-backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Collection Structure

### 📁 Authentication
- **Admin Login** - Login as admin and store JWT token
- **Customer Login** - Login as customer and store JWT token
- **Employee Login** - Login as employee and store JWT token (if needed)
- **Vendor Login** - Login as vendor and store JWT token (if needed)

### 📁 Public Endpoints
- **Get All Service Packages** - Retrieve all available service packages
- **Get Service Package by ID** - Get specific service package details

### 📁 Admin Endpoints
- **Create Service Package** - Add new service package (Admin only)
- **Update Service Package** - Modify existing service package (Admin only)
- **Delete Service Package** - Remove service package (Admin only)
- **Get All Users** - Retrieve all system users (Admin only)
- **Get All Feedbacks** - View customer feedbacks (Admin only)

### 📁 Customer Endpoints
- **Get Customer Profile** - Retrieve customer profile information
- **Update Customer Profile** - Modify customer details
- **Get Customer Service Requests** - View customer's service history
- **Create Service Request** - Book new service request

### 📁 Employee Endpoints (Future)
- **Get Assigned Customers** - View customers assigned to employee
- **Get Service Requests** - View service requests to handle
- **Update Service Request** - Update service request status

### 📁 Vendor Endpoints (Future)
- **Get Vendor Employees** - View vendor's employees
- **Add New Employee** - Register new employee
- **Get All Service Requests** - View all service requests for vendor

## Environment Variables

| Variable | Description | Default Value |
|----------|-------------|---------------|
| `base_url` | API base URL | `http://localhost:8080` |
| `admin_token` | JWT token for admin (auto-set) | - |
| `customer_token` | JWT token for customer (auto-set) | - |
| `employee_token` | JWT token for employee (auto-set) | - |
| `vendor_token` | JWT token for vendor (auto-set) | - |
| `admin_email` | Admin email | `admin@carservice.com` |
| `admin_password` | Admin password | `admin123` |
| `customer_email` | Customer email | `customer@carservice.com` |
| `customer_password` | Customer password | `cust123` |

## Usage Workflow

### 1. Authentication Flow
1. Run **Admin Login** or **Customer Login** first
2. The JWT token will be automatically stored in environment variables
3. Subsequent requests will use the stored token for authentication

### 2. Testing Public Endpoints
- No authentication required
- Can be tested directly without login

### 3. Testing Protected Endpoints
1. Login first using appropriate user role
2. Use the role-specific endpoints
3. JWT token is automatically included in Authorization header

## Sample Test Scenarios

### Scenario 1: Customer Journey
1. **Customer Login** - Authenticate as customer
2. **Get All Service Packages** - Browse available services
3. **Create Service Request** - Book a service
4. **Get Customer Service Requests** - Check booking status
5. **Update Customer Profile** - Modify personal details

### Scenario 2: Admin Management
1. **Admin Login** - Authenticate as admin
2. **Get All Users** - View system users
3. **Create Service Package** - Add new service offering
4. **Get All Feedbacks** - Review customer feedback
5. **Update Service Package** - Modify service details

## Request Examples

### Login Request
```json
POST /auth/login
{
    "email": "admin@carservice.com",
    "password": "admin123"
}
```

### Create Service Request
```json
POST /customer/service-request
Authorization: Bearer {{customer_token}}
{
    "vehicleType": "CAR",
    "vehicleModel": "Civic",
    "vehicleBrand": "Honda",
    "vehicleRegNo": "MH 14 XY 9876",
    "deliveryType": "PICKUP",
    "customerId": 1,
    "packageId": 2
}
```

### Create Service Package (Admin)
```json
POST /admin/servicepackage
Authorization: Bearer {{admin_token}}
{
    "packageName": "Premium Car Wash",
    "packageDescription": "Complete exterior and interior cleaning",
    "packagePrice": 1500.0
}
```

## Response Formats

### Success Response
```json
{
    "status": "success",
    "data": { ... },
    "message": "Operation completed successfully"
}
```

### Error Response
```json
{
    "status": "error",
    "message": "Error description",
    "timestamp": "2024-01-15T10:30:00Z"
}
```

### JWT Token Response
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "email": "admin@carservice.com",
    "role": "ADMIN"
}
```

## Testing Tips

### 1. Auto-Token Management
- Login requests automatically store JWT tokens
- No need to manually copy/paste tokens
- Tokens are used automatically in subsequent requests

### 2. Environment Switching
- Change `base_url` to test different environments
- Use `http://localhost:8080` for local development
- Update to production URL when needed

### 3. Error Handling
- Check response status codes
- Review error messages for debugging
- Ensure proper authentication before testing protected endpoints

### 4. Data Validation
- Use realistic test data
- Follow required field constraints
- Check date formats (YYYY-MM-DD)

## Troubleshooting

### Common Issues

1. **401 Unauthorized**
   - Ensure you've logged in first
   - Check if JWT token is properly set
   - Verify user role permissions

2. **404 Not Found**
   - Verify the application is running
   - Check the base URL in environment
   - Ensure endpoint paths are correct

3. **400 Bad Request**
   - Validate request body format
   - Check required fields
   - Verify data types and constraints

4. **Connection Refused**
   - Ensure Spring Boot application is running
   - Check if port 8080 is available
   - Verify no firewall blocking

### Debug Steps
1. Check application logs
2. Verify database connectivity
3. Test with H2 console if using H2 profile
4. Review JWT token expiration (24 hours default)

## Additional Resources

- [Postman Documentation](https://learning.postman.com/)
- [JWT.io](https://jwt.io/) - JWT token decoder
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [API Documentation](../README.md) - Main project README

## Support

For issues or questions:
1. Check application logs
2. Review this documentation
3. Test with H2 console for database issues
4. Verify environment configuration