package com.company.apiperson.controller;

import com.company.apiperson.security.model.entity.User;
import com.company.apiperson.security.model.request.AuthorizationRequest;
import com.company.apiperson.security.model.response.UserResponse;
import com.company.apiperson.security.service.UserService;
import com.company.apiperson.service.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@Valid @RequestBody AuthorizationRequest userRequest) {
        return ResponseEntity.ok(userService.saveUser(mapper.toDomain(userRequest), userRequest.getRoles()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}