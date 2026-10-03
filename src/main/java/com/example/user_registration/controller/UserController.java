package com.example.user_registration.controller;

import com.example.user_registration.dto.RegisterRequest;
import com.example.user_registration.entity.User;
import com.example.user_registration.service.UserService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.example.user_registration.dto.UserResponse;
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(
            @Valid @RequestBody RegisterRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userService.registerUser(user);

        return "User registered successfully";
    }

	@GetMapping
	public List<UserResponse> getAllUsers() {
	    return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public UserResponse getUserById(
	        @org.springframework.web.bind.annotation.PathVariable Long id) {
	    return userService.getUserById(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long id) {
	    userService.deleteUser(id);
	}
	
	@PutMapping("/{id}")
	public UserResponse updateUser(
	        @PathVariable Long id,
	        @RequestBody User updatedUser) {
	    return userService.updateUser(id, updatedUser);
	}
}