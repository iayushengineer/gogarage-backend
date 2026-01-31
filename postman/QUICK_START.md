# 🚀 Quick Start Guide - Postman Collection

## 📁 Files in this folder:
- `Car-Service-API.postman_collection.json` - Basic API collection
- `Car-Service-Complete-API.postman_collection.json` - Complete API collection with all endpoints
- `Car-Service-Environment.postman_environment.json` - Environment variables
- `README.md` - Detailed documentation
- `QUICK_START.md` - This quick setup guide

## ⚡ Quick Setup (2 minutes)

### 1. Import to Postman
1. Open Postman
2. Click **Import** button
3. Drag and drop these files:
   - `Car-Service-Complete-API.postman_collection.json`
   - `Car-Service-Environment.postman_environment.json`

### 2. Select Environment
- In Postman, select **"Car Service Environment"** from the environment dropdown (top right)

### 3. Start Application
```bash
cd car-service-backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 4. Test API (30 seconds)
1. **Run "Admin Login"** → JWT token auto-stored
2. **Run "Get All Service Packages"** → See sample data
3. **Run "Create Service Package"** → Test admin functionality
4. **Run "Customer Login"** → Switch to customer role
5. **Run "Get Customer Profile"** → See customer data

## 🎯 Key Features

### ✅ Auto-Token Management
- Login requests automatically store JWT tokens
- No manual copy/paste needed
- Tokens used automatically in subsequent requests

### ✅ Pre-configured Test Data
- Sample users for all roles (Admin, Customer, Employee, Vendor)
- Ready-to-use service packages and requests
- Realistic test scenarios

### ✅ Environment Variables
```
base_url = http://localhost:8080
admin_email = admin@carservice.com
customer_email = customer@carservice.com
```

## 🔐 Sample Credentials

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@carservice.com | admin123 |
| Customer | customer@carservice.com | cust123 |
| Employee | employee@carservice.com | emp123 |
| Vendor | vendor@carservice.com | vendor123 |

## 📋 Test Scenarios

### Scenario 1: Customer Journey (2 minutes)
1. **Customer Login** → Get JWT token
2. **Get All Service Packages** → Browse services
3. **Create Service Request** → Book a service
4. **Get Service Requests** → Check booking status
5. **Submit Feedback** → Leave review

### Scenario 2: Admin Management (2 minutes)
1. **Admin Login** → Get admin JWT token
2. **Get All Users** → View system users
3. **Create Service Package** → Add new service
4. **Update Service Package** → Modify service
5. **Delete Service Package** → Remove service

## 🛠️ Troubleshooting

### ❌ 401 Unauthorized
- **Solution**: Run login request first to get JWT token

### ❌ Connection Refused
- **Solution**: Ensure Spring Boot app is running on port 8080

### ❌ 404 Not Found
- **Solution**: Check if endpoint exists in your controllers

### ❌ 400 Bad Request
- **Solution**: Verify JSON format and required fields

## 🎉 Success Indicators

✅ **Login Response**: Should return JWT token
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "email": "admin@carservice.com",
    "role": "ADMIN"
}
```

✅ **Service Packages**: Should return array of packages
```json
[
    {
        "packageId": 1,
        "packageName": "Basic Service",
        "packagePrice": 2500.0
    }
]
```

✅ **Protected Endpoints**: Should work with valid JWT token

## 📞 Need Help?

1. Check the detailed [README.md](README.md) in this folder
2. Review application logs for errors
3. Test with H2 console: http://localhost:8080/h2-console
4. Verify environment variables in Postman

---

**🎯 Goal**: Get familiar with the API in under 5 minutes!