package com.app.controller;

import com.app.pojos.ServicePackage;
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

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
public class ServicePackageControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private MockMvc mockMvc;
    private String adminToken;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // Generate JWT token for admin
        Map<String, String> claims = new HashMap<>();
        claims.put("email", "admin@carservice.com");
        claims.put("password", "admin123");
        claims.put("role", "ADMIN");
        adminToken = jwtTokenUtil.generateToken(claims);
    }

    @Test
    public void testGetAllServicePackages() throws Exception {
        mockMvc.perform(get("/servicepackage/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].packageName").exists())
                .andExpect(jsonPath("$[0].packagePrice").exists());
    }

    @Test
    public void testGetServicePackageById() throws Exception {
        mockMvc.perform(get("/servicepackage/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.packageId").value(1))
                .andExpect(jsonPath("$.packageName").exists());
    }

    @Test
    public void testCreateServicePackage() throws Exception {
        ServicePackage servicePackage = new ServicePackage();
        servicePackage.setPackageName("Test Package");
        servicePackage.setPackageDescription("Test Description");
        servicePackage.setPackagePrice(1500.0);

        mockMvc.perform(post("/admin/servicepackage")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servicePackage)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateServicePackage() throws Exception {
        ServicePackage servicePackage = new ServicePackage();
        servicePackage.setPackageId(1L);
        servicePackage.setPackageName("Updated Package");
        servicePackage.setPackageDescription("Updated Description");
        servicePackage.setPackagePrice(2000.0);

        mockMvc.perform(put("/admin/servicepackage/1")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servicePackage)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteServicePackage() throws Exception {
        mockMvc.perform(delete("/admin/servicepackage/1")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetNonExistentServicePackage() throws Exception {
        mockMvc.perform(get("/servicepackage/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateServicePackageWithoutAuth() throws Exception {
        ServicePackage servicePackage = new ServicePackage();
        servicePackage.setPackageName("Test Package");
        servicePackage.setPackageDescription("Test Description");
        servicePackage.setPackagePrice(1500.0);

        mockMvc.perform(post("/admin/servicepackage")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servicePackage)))
                .andExpect(status().isUnauthorized());
    }
}