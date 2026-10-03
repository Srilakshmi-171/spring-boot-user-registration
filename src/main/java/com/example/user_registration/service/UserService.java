package com.example.user_registration.service;

import com.example.user_registration.entity.User;
import com.example.user_registration.repository.UserRepository;
import com.example.user_registration.dto.UserResponse;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Hash the password before saving it
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    

	public List<UserResponse> getAllUsers() {
	    return userRepository.findAll()
	            .stream()
	            .map(user -> new UserResponse(
	                    user.getId(),
	                    user.getName(),
	                    user.getEmail()))
	            .collect(java.util.stream.Collectors.toList());
	}

	public UserResponse getUserById(Long id) {
	    User user = userRepository.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("User not found with id: " + id));
	
	    return new UserResponse(
	            user.getId(),
	            user.getName(),
	            user.getEmail()
	    );
	}
	
	
	public void deleteUser(Long id) {
	    if (!userRepository.existsById(id)) {
	        throw new RuntimeException("User not found with id: " + id);
	    }
	
	    userRepository.deleteById(id);
	}
	
	
	public UserResponse updateUser(Long id, User updatedUser) {
	    User user = userRepository.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("User not found with id: " + id));
	
	    if (userRepository.findByEmail(updatedUser.getEmail())
	            .filter(existing -> !existing.getId().equals(id))
	            .isPresent()) {
	        throw new IllegalArgumentException("Email already registered");
	    }
	
	    user.setName(updatedUser.getName());
	    user.setEmail(updatedUser.getEmail());
	
	    User savedUser = userRepository.save(user);
	
	    return new UserResponse(
	            savedUser.getId(),
	            savedUser.getName(),
	            savedUser.getEmail()
	    );
	}
}
