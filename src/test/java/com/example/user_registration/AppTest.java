
package com.example.user_registration;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@SpringBootTest
@AutoConfigureMockMvc
class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        // Verifies that the application context starts.
    }


	@Test
	void registerUserWithValidData() throws Exception {
	    String uniqueEmail = "test-" + UUID.randomUUID()
	            + "@example.com";
	
	    String requestBody = "{\n" +
	            "    \"name\": \"Test User\",\n" +
	            "    \"email\": \"test-" + UUID.randomUUID() + "@example.com\",\n" +
	            "    \"password\": \"SecurePass123\"\n" +
	            "}";
	
	    mockMvc.perform(post("/users/register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isCreated());
	}
	    

	@Test
	void registerUserWithInvalidData() throws Exception {
		String requestBody = "{\n" +
                "    \"name\": \"\",\n" +
                "    \"email\": \"invalid-email\",\n" +
                "    \"password\": \"123\"\n" +
                "}";
	
	    mockMvc.perform(post("/users/register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isBadRequest());
	}
	

	@Test
	void registerUserWithDuplicateEmail() throws Exception {
	    String email = "duplicate-" + UUID.randomUUID() + "@example.com";
	
	    String requestBody = "{\n" +
	            "    \"name\": \"Test User\",\n" +
	            "    \"email\": \"" + email + "\",\n" +
	            "    \"password\": \"SecurePass123\"\n" +
	            "}";
	
	    // First registration should succeed
	    mockMvc.perform(post("/users/register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isCreated());
	
	    // Second registration with the same email should fail
	    mockMvc.perform(post("/users/register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isConflict());
	}
	
	@Test
	void getAllUsers() throws Exception {
	    mockMvc.perform(get("/users"))
	            .andExpect(status().isOk());
	}
	
	@Test
	void getUserWithNonExistingId() throws Exception {
	    mockMvc.perform(get("/users/999999"))
	            .andExpect(status().isNotFound());
	}
	
	
	@Test
	void deleteExistingUser() throws Exception {
	    String email = "delete-" + UUID.randomUUID() + "@example.com";
	
	    String requestBody = "{\n" +
	            "    \"name\": \"Delete Test User\",\n" +
	            "    \"email\": \"" + email + "\",\n" +
	            "    \"password\": \"SecurePass123\"\n" +
	            "}";
	
	    mockMvc.perform(post("/users/register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isCreated());
	
	    MvcResult result = mockMvc.perform(get("/users"))
	            .andExpect(status().isOk())
	            .andReturn();
	
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode users = mapper.readTree(
	            result.getResponse().getContentAsString());
	
	    long userId = -1;
	
	    for (JsonNode user : users) {
	        if (email.equals(user.get("email").asText())) {
	            userId = user.get("id").asLong();
	            break;
	        }
	    }
	
	    org.junit.jupiter.api.Assertions.assertTrue(
	            userId > 0, "Registered user should be found");
	
	    mockMvc.perform(delete("/users/" + userId))
	            .andExpect(status().isNoContent());
	}

	
	@Test
	void deleteNonExistingUser() throws Exception {
	    mockMvc.perform(delete("/users/999999"))
	            .andExpect(status().isNotFound());
	}
	
	
	@Test
	void updateExistingUser() throws Exception {
	    String email = "update-" + UUID.randomUUID() + "@example.com";
	
	    String registerBody = "{\n" +
	            "    \"name\": \"Original Name\",\n" +
	            "    \"email\": \"" + email + "\",\n" +
	            "    \"password\": \"SecurePass123\"\n" +
	            "}";
	
	    mockMvc.perform(post("/users/register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(registerBody))
	            .andExpect(status().isCreated());
	
	    MvcResult result = mockMvc.perform(get("/users"))
	            .andExpect(status().isOk())
	            .andReturn();
	
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode users = mapper.readTree(
	            result.getResponse().getContentAsString());
	
	    long userId = -1;
	
	    for (JsonNode user : users) {
	        if (email.equals(user.get("email").asText())) {
	            userId = user.get("id").asLong();
	            break;
	        }
	    }
	
	    org.junit.jupiter.api.Assertions.assertTrue(
	            userId > 0, "Registered user should be found");
	
	    String updateBody = "{\n" +
	            "    \"name\": \"Updated Name\",\n" +
	            "    \"email\": \"" + email + "\",\n" +
	            "    \"password\": \"SecurePass123\"\n" +
	            "}";
	
	    mockMvc.perform(put("/users/" + userId)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(updateBody))
	            .andExpect(status().isOk());
	    
	    mockMvc.perform(get("/users/" + userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated Name"));
	}
}