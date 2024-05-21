package com.proyectointegrador.msusers.controller;

import com.proyectointegrador.msusers.domain.User;
import com.proyectointegrador.msusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/username/{username}")
    public ResponseEntity<List<User>> findUserByUserName(@PathVariable String username){
        return ResponseEntity.ok(userService.findUserByUserName(username));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<User>> findUserById(@PathVariable String id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id){
        userService.deleteUserById(id);
        String message = "User with ID '" + id + "' has been successfully deleted.";
        return ResponseEntity.ok().body(message);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles/{id}")
    public ResponseEntity<List<String>> getUserRoles(@PathVariable String id){
        return ResponseEntity.ok(userService.getUserRoles(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/roles/addRole")
    public ResponseEntity<List<String>> addRoleToUser(@RequestParam("id") String id, @RequestParam("role") String role){
        return ResponseEntity.ok(userService.addRoleToUser(id, role));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/roles/deleteRole")
    public ResponseEntity<String> deleteRoleToUser(@RequestParam("id") String id, @RequestParam("role") String role){
        userService.deleteRoleToUser(id, role);
        String message = "Role '" + role + "' has been successfully deleted from user with ID '" + id + "'.";
        return ResponseEntity.ok().body(message);
    }
}
