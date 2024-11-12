package com.g96.ftms.controller;

import com.g96.ftms.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseEntity<List<String>> getAllRoleNames() {
        List<String> roleNames = roleService.getAllRoleNames();
        return ResponseEntity.ok(roleNames);
    }
}
