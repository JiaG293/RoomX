/*
package com.roomx.controller.resource;

import com.roomx.application.service.admin.impl.KeycloakAdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/keycloak")
public class KeycloakAdminController {

    private final KeycloakAdminService keycloakAdminService;

    public KeycloakAdminController(KeycloakAdminService keycloakAdminService) {
        this.keycloakAdminService = keycloakAdminService;
    }

    @PostMapping("/users")
    public String createUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        return keycloakAdminService.createUser(username, email, password);
    }

    @PostMapping("/users/{userId}/roles")
    public String assignRoleToUser(@PathVariable String userId, @RequestParam String roleName) {
        return keycloakAdminService.assignRoleToUser(userId, roleName);
    }

    @GetMapping("/users")
    public List<UserRepresentation> getUsers() {
        return keycloakAdminService.getUsers();
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return keycloakAdminService.deleteUser(userId);
    }
}
*/
