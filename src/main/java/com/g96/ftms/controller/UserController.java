package com.g96.ftms.controller;

import com.g96.ftms.dto.UserDTO;
import com.g96.ftms.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getLoggedInUserProfile(Authentication authentication) {
        String account = authentication.getName();
        UserDTO userDTO = userService.getUserProfileByAccount(account);
        return ResponseEntity.ok(userDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_CLASS_ADMIN','ROLE_TRAINER')")
    @GetMapping("/management/list")
    public ResponseEntity<Map<String, Object>> getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
        Map<String, Object> response = userService.getPagedUsers(pageable);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_CLASS_ADMIN','ROLE_TRAINER')")
    @GetMapping("/management/list/{userId}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable Long userId, Authentication authentication) {
        UserDTO userDTO = userService.getUserDetails(userId, authentication);
        return ResponseEntity.ok(userDTO);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/management/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO, Authentication authentication) {
        return userService.addUser(userDTO, authentication);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
        @PostMapping("/management/status/{userId}")
    public ResponseEntity<?> changeUserStatus(@PathVariable Long userId, Authentication authentication) {
        return userService.changeStatus(userId, authentication);
    }


}
