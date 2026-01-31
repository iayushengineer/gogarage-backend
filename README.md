# Car Service Station Backend

A Spring Boot application for managing car service operations, upgraded to Java 17 and Spring Boot 3.2.0 with modern features.

## Features

- **Java 17** and **Spring Boot 3.2.0**
- **JWT Authentication** with role-based access control
- **Multiple Database Profiles** (H2, MySQL)
- **Liquibase** for database migration
- **Comprehensive Testing** with integration tests
- **RESTful APIs** for all operations
- **Role-based Security** (Admin, Vendor, Employee, Customer)

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Security 6.x
- Spring Data JPA
- JWT (JSON Web Tokens)
- Liquibase
- H2 Database (for development/testing)
- MySQL (for production)
- Maven
- JUnit 5

## Database Schema

The application includes the following main entities:
- **Users** - Authentication and authorization
- **Admin** - System administrators
- **Vendor** - Service providers
- **Employee** - Vendor employees
- **Customer** - Service customers
- **Service Package** - Available service packages
- **Service Request** - Customer service requests
- **Stock** - Inventory management
- **Feedback** - Customer feedback
- **Offer** - Promotional offers
- **Question** - FAQ management

## Profiles

### Development Profile (dev)
Uses H2 in-memory database with sample data.
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### H2 Profile (h2)
Uses H2 database with console access enabled.
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```
- H2 Console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

### Production Profile (prod)
Uses MySQL database for production deployment.
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (for production profile)

### Installation

1. Clone the repository
```bash
git clone <repository-url>
cd car-service-backend
```

2. Build the application
```bash
mvn clean compile
```

3. Run tests
```bash
mvn test
```

4. Run the application (development mode)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Database Setup

#### For Development (H2)
No setup required. The application will create an in-memory database with sample data.

#### For Production (MySQL)
1. Create a MySQL database:
```sql
CREATE DATABASE car_service_db;
```

2. Update the database credentials in `application-prod.properties` or set environment variables:
```bash
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

3. Run with production profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## API Endpoints

### Authentication
- `POST /auth/login` - User login
- `POST /auth/register` - User registration

### Admin Endpoints
- `GET /admin/users` - Get all users
- `POST /admin/servicepackage` - Create service package
- `PUT /admin/servicepackage/{id}` - Update service package
- `DELETE /admin/servicepackage/{id}` - Delete service package

### Customer Endpoints
- `GET /customer/profile` - Get customer profile
- `PUT /customer/profile` - Update customer profile
- `GET /customer/service-requests` - Get customer's service requests
- `POST /customer/service-request` - Create new service request

### Employee Endpoints
- `GET /employee/customers` - Get assigned customers
- `GET /employee/service-requests` - Get service requests
- `PUT /employee/service-request/{id}` - Update service request

### Vendor Endpoints
- `GET /vendor/employees` - Get vendor employees
- `POST /vendor/employee` - Add new employee
- `GET /vendor/service-requests` - Get all service requests

### Public Endpoints
- `GET /servicepackage/list` - Get all service packages
- `GET /home/offers` - Get current offers
- `GET /home/faqs` - Get frequently asked questions

## Sample Users

The application comes with pre-configured sample users:

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@carservice.com | admin123 |
| Vendor | vendor@carservice.com | vendor123 |
| Employee | employee@carservice.com | emp123 |
| Customer | customer@carservice.com | cust123 |

## Testing

### Run All Tests
```bash
mvn test
```

### Run Integration Tests
```bash
mvn test -Dtest="*IntegrationTest"
```

### Run Specific Test Class
```bash
mvn test -Dtest="AuthControllerIntegrationTest"
```

## Database Migration

The application uses Liquibase for database schema management:

- Schema changes are in `src/main/resources/db/changelog/`
- Master changelog: `db.changelog-master.xml`
- Schema creation: `001-create-schema.xml`
- Sample data: `002-insert-data.xml`

## Security

- JWT-based authentication
- Role-based authorization
- Password encoding (configurable)
- CORS support for frontend integration
- Session stateless configuration

## Configuration Files

- `application.properties` - Main configuration
- `application-dev.properties` - Development profile
- `application-h2.properties` - H2 database profile
- `application-prod.properties` - Production profile
- `application-test.properties` - Test profile

## SQL Scripts

- `schema.sql` - Complete DDL for manual database setup
- `data.sql` - Sample data for testing

## Troubleshooting

### Common Issues

1. **Port already in use**
   - Change the port in `application.properties`: `server.port=8081`

2. **Database connection issues**
   - Verify database credentials
   - Ensure database server is running
   - Check firewall settings

3. **JWT token issues**
   - Verify token format in Authorization header: `Bearer <token>`
   - Check token expiration (24 hours by default)

### Logs

Enable debug logging by adding to `application.properties`:
```properties
logging.level.com.app=DEBUG
logging.level.org.springframework.security=DEBUG
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## License

This project is licensed under the MIT License.