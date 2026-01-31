package com.app.dao;

import com.app.pojos.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CustomerDaoIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testFindByEmail() {
        // Given
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        customer.setPassword("password");
        customer.setBirthDate(LocalDate.of(1990, 1, 1));
        customer.setContact("1234567890");
        customer.setAddress("Test Address");
        customer.setEmployeeId(1);

        entityManager.persistAndFlush(customer);

        // When
        Customer found = customerDao.findByEmail("test@example.com");

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test Customer");
        assertThat(found.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testFindByEmailNotFound() {
        // When
        Customer found = customerDao.findByEmail("nonexistent@example.com");

        // Then
        assertThat(found).isNull();
    }

    @Test
    public void testFindByEmployeeId() {
        // Given
        Customer customer1 = new Customer();
        customer1.setName("Customer 1");
        customer1.setEmail("customer1@example.com");
        customer1.setPassword("password");
        customer1.setEmployeeId(1);

        Customer customer2 = new Customer();
        customer2.setName("Customer 2");
        customer2.setEmail("customer2@example.com");
        customer2.setPassword("password");
        customer2.setEmployeeId(1);

        Customer customer3 = new Customer();
        customer3.setName("Customer 3");
        customer3.setEmail("customer3@example.com");
        customer3.setPassword("password");
        customer3.setEmployeeId(2);

        entityManager.persistAndFlush(customer1);
        entityManager.persistAndFlush(customer2);
        entityManager.persistAndFlush(customer3);

        // When
        List<Customer> customers = customerDao.findAllByEmployeeId(1);

        // Then
        assertThat(customers).hasSize(2);
        assertThat(customers).extracting(Customer::getName)
                .containsExactlyInAnyOrder("Customer 1", "Customer 2");
    }

    @Test
    public void testSaveCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setName("New Customer");
        customer.setEmail("new@example.com");
        customer.setPassword("password");
        customer.setBirthDate(LocalDate.of(1985, 5, 15));
        customer.setContact("9876543210");
        customer.setAddress("New Address");
        customer.setEmployeeId(1);

        // When
        Customer saved = customerDao.save(customer);

        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("New Customer");
        assertThat(saved.getEmail()).isEqualTo("new@example.com");

        // Verify it's actually saved
        Customer found = entityManager.find(Customer.class, saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("New Customer");
    }

    @Test
    public void testUpdateCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setName("Original Name");
        customer.setEmail("original@example.com");
        customer.setPassword("password");
        customer.setEmployeeId(1);

        Customer saved = entityManager.persistAndFlush(customer);

        // When
        saved.setName("Updated Name");
        saved.setContact("9999999999");
        Customer updated = customerDao.save(saved);

        // Then
        assertThat(updated.getName()).isEqualTo("Updated Name");
        assertThat(updated.getContact()).isEqualTo("9999999999");
        assertThat(updated.getEmail()).isEqualTo("original@example.com"); // unchanged
    }

    @Test
    public void testDeleteCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setName("To Delete");
        customer.setEmail("delete@example.com");
        customer.setPassword("password");
        customer.setEmployeeId(1);

        Customer saved = entityManager.persistAndFlush(customer);
        Integer customerId = saved.getId();

        // When
        customerDao.deleteById(customerId);
        entityManager.flush();

        // Then
        Customer found = entityManager.find(Customer.class, customerId);
        assertThat(found).isNull();
    }
}