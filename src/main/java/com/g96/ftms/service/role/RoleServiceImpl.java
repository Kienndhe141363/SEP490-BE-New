package com.g96.ftms.service.role;

import com.g96.ftms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<String> getAllRoleNames() {
        return roleRepository.findAllRoleNames();
    }
}
