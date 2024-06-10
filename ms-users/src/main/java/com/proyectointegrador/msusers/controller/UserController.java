package com.proyectointegrador.msusers.controller;

import com.proyectointegrador.msusers.domain.User;
import com.proyectointegrador.msusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Value("${backend.keycloak.realm}")
    private String realm;

    @Value("${backend.keycloak.clientId}")
    private String clientId;

    @Value("${backend.keycloak.clientSecret}")
    private String clientSecret;

    @Value("${backend.keycloak.serverUrl}")
    private String serverUrl;

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> getToken() {
        RestTemplate restTemplate = new RestTemplate();

        String tokenEndpoint = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(tokenEndpoint, HttpMethod.POST, request, Map.class);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("access_token", (String) response.getBody().get("access_token"));

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/info")
    public Map<String, String> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        String id = jwt.getClaimAsString("sub");
        String userName = jwt.getClaimAsString("preferred_username");
        String name = jwt.getClaimAsString("given_name");
        String lastName = jwt.getClaimAsString("family_name");
        String email = jwt.getClaimAsString("email");
        Map<String, String> userInfo = new LinkedHashMap<>();
        userInfo.put("id", id);
        userInfo.put("userName", userName);
        userInfo.put("name", name);
        userInfo.put("lastName", lastName);
        userInfo.put("email", email);
        return userInfo;
    }

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
