package com.app.controller;

import com.app.pojos.Customer;
import com.app.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
public class CustomerControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private MockMvc mockMvc;
    private String customerToken;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // Generate JWT token for customer
        Map<String, String> claims = new HashMap<>();
        claims.put("email", "customer@carservice.com");
        claims.put("password", "cust123");
        claims.put("role", "CUSTOMER");
        customerToken = jwtTokenUtil.generateToken(claims);
    }

    @Test
    public void testGetCustomerProfile() throws Exception {
        mockMvc.perform(get("/customer/profile")
                .header("Authorization", "Bearer " + customerToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("customer@carservice.com"));
    }

    @Test
    public void testUpdateCustomerProfile() throws Exception {
        Customer customer = new Customer();
        customer.setName("Updated Customer Name");
        customer.setEmail("customer@carservice.com");
        customer.setContact("9999999999");
        customer.setAddress("Updated Address");
        customer.setBirthDate(LocalDate.of(1985, 3, 25));

        mockMvc.perform(put("/customer/profile")
                .header("Authorization", "Bearer " + customerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCustomerServiceRequests() throws Exception {
        mockMvc.perform(get("/customer/service-requests")
                .header("Authorization", "Bearer " + customerToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testAccessWithoutToken() throws Exception {
        mockMvc.perform(get("/customer/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAccessWithInvalidToken() throws Exception {
        mockMvc.perform(get("/customer/profile")
                .header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized());
    }
}