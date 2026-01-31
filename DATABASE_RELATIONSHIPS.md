# Database Relationships and Foreign Keys

## Entity Relationship Overview

```
┌─────────────┐
│    USERS    │ (Base authentication table)
│─────────────│
│ email (PK)  │
│ password    │
│ role        │
│ active      │
└─────────────┘

┌─────────────┐    ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│    ADMIN    │    │     VENDOR      │    │    EMPLOYEE     │    │    CUSTOMER     │
│─────────────│    │─────────────────│    │─────────────────│    │─────────────────│
│ id (PK)     │    │ id (PK)         │    │ id (PK)         │    │ id (PK)         │
│ name        │    │ name            │    │ name            │    │ name            │
│ email       │    │ address         │    │ birth_date      │    │ birth_date      │
│ password    │    │ contact         │    │ email           │    │ contact         │
└─────────────┘    │ email           │    │ password        │    │ address         │
                   │ password        │    │ vendor_id (FK)  │────│ email           │
                   └─────────────────┘    └─────────────────┘    │ password        │
                            │                       │             │ employee_id(FK) │
                            └───────────────────────┘             └─────────────────┘
                                                                           │
┌─────────────────────┐    ┌─────────────────────┐                      │
│   SERVICE_PACKAGE   │    │   SERVICE_REQUEST   │◄─────────────────────┘
│─────────────────────│    │─────────────────────│
│ package_id (PK)     │    │ request_id (PK)     │
│ package_name        │    │ vehicle_type        │
│ package_description │    │ vehicle_model       │
│ package_price       │    │ vehicle_brand       │
└─────────────────────┘    │ vehicle_reg_no      │
           │                │ service_date        │
           └────────────────│ out_date            │
                            │ delivery_type       │
                            │ customer_id (FK)    │
                            │ status              │
                            │ labour_charges      │
                            │ discount            │
                            │ product_charges     │
                            │ total               │
                            │ package_id (FK)     │
                            └─────────────────────┘
                                     │
                            ┌─────────────────┐
                            │    FEEDBACK     │
                            │─────────────────│
                            │ id (PK)         │
                            │ customer_id(FK) │◄───┐
                            │ feedback_msg    │    │
                            └─────────────────┘    │
                                                   │
┌─────────────────┐    ┌─────────────────┐       │
│     STOCK       │    │     OFFER       │       │
│─────────────────│    │─────────────────│       │
│ stock_id (PK)   │    │ offer_id (PK)   │       │
│ item_name       │    │ offer_name      │       │
│ price           │    │ offer_discount  │       │
│ quantity        │    │ min_value       │       │
└─────────────────┘    └─────────────────┘       │
                                                  │
┌─────────────────┐                              │
│    QUESTION     │                              │
│─────────────────│                              │
│ question_id(PK) │                              │
│ question        │                              │
└─────────────────┘                              │
                                                  │
                            ┌─────────────────────┘
                            │
                    ┌─────────────────┐
                    │    CUSTOMER     │
                    │─────────────────│
                    │ id (PK)         │
                    └─────────────────┘
```

## Foreign Key Relationships

### 1. **EMPLOYEE → VENDOR**
```sql
FOREIGN KEY (vendor_id) REFERENCES vendor(id) ON DELETE CASCADE
```
- **Relationship**: Many-to-One (Many employees belong to one vendor)
- **Business Logic**: When a vendor is deleted, all associated employees are also deleted
- **JPA Mapping**: `@OneToMany(mappedBy = "vendorId", cascade = CascadeType.ALL, orphanRemoval = true)` in Vendor entity

### 2. **CUSTOMER → EMPLOYEE**
```sql
FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE SET NULL
```
- **Relationship**: Many-to-One (Many customers can be assigned to one employee)
- **Business Logic**: When an employee is deleted, customers are not deleted but employee_id is set to NULL
- **JPA Mapping**: `@OneToMany(mappedBy = "employeeId", cascade = CascadeType.PERSIST)` in Employee entity

### 3. **SERVICE_REQUEST → CUSTOMER**
```sql
FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
```
- **Relationship**: Many-to-One (Many service requests belong to one customer)
- **Business Logic**: When a customer is deleted, all their service requests are also deleted
- **JPA Mapping**: `@OneToMany(mappedBy = "customerId", fetch = FetchType.EAGER)` in Customer entity

### 4. **SERVICE_REQUEST → SERVICE_PACKAGE**
```sql
FOREIGN KEY (package_id) REFERENCES service_package(package_id) ON DELETE SET NULL
```
- **Relationship**: Many-to-One (Many service requests can use one service package)
- **Business Logic**: When a service package is deleted, service requests are not deleted but package_id is set to NULL
- **JPA Mapping**: No direct mapping in entities (handled via package_id field)

### 5. **FEEDBACK → CUSTOMER**
```sql
FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
```
- **Relationship**: Many-to-One (Many feedback entries belong to one customer)
- **Business Logic**: When a customer is deleted, all their feedback is also deleted
- **JPA Mapping**: No direct mapping in entities (handled via customer_id field)

## Referential Integrity Rules

### CASCADE DELETE
- **Vendor → Employee**: Deleting a vendor removes all associated employees
- **Customer → Service Request**: Deleting a customer removes all their service requests
- **Customer → Feedback**: Deleting a customer removes all their feedback

### SET NULL
- **Employee → Customer**: Deleting an employee sets customer.employee_id to NULL
- **Service Package → Service Request**: Deleting a service package sets service_request.package_id to NULL

## Data Consistency Checks

### 1. **User Authentication Consistency**
- Every Admin, Vendor, Employee, and Customer should have a corresponding entry in the USERS table
- Email addresses must be unique across the system

### 2. **Business Logic Constraints**
- Service requests must have a valid customer_id
- Employees must belong to a valid vendor
- Service requests can optionally reference a service package

### 3. **Data Validation**
- Email format validation
- Date constraints (birth_date must be in the past)
- Positive values for prices and quantities
- Valid enum values for roles, vehicle types, delivery types, and status

## Index Strategy

```sql
-- Performance indexes on foreign keys
CREATE INDEX idx_employee_vendor ON employee(vendor_id);
CREATE INDEX idx_customer_employee ON customer(employee_id);
CREATE INDEX idx_service_request_customer ON service_request(customer_id);
CREATE INDEX idx_service_request_package ON service_request(package_id);
CREATE INDEX idx_feedback_customer ON feedback(customer_id);

-- Business logic indexes
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_service_request_status ON service_request(status);
CREATE INDEX idx_service_request_date ON service_request(service_date);
```

## Sample Data Relationships

The sample data maintains proper referential integrity:

1. **Vendor 1** → **Employee 1, 2**
2. **Vendor 2** → **Employee 3**
3. **Employee 1** → **Customer 1**
4. **Employee 2** → **Customer 2**
5. **Employee 3** → **Customer 3**
6. **Customer 1** → **Service Request 1, 4** → **Feedback 1, 4**
7. **Customer 2** → **Service Request 2, 5** → **Feedback 2, 5**
8. **Customer 3** → **Service Request 3** → **Feedback 3**

All foreign key constraints are properly maintained in both the Liquibase changelogs and the direct SQL scripts.