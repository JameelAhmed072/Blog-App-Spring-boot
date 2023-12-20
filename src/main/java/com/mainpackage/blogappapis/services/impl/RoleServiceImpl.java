package com.mainpackage.blogappapis.services.impl;


import com.mainpackage.blogappapis.entities.Permission;
import com.mainpackage.blogappapis.entities.Role;
import com.mainpackage.blogappapis.exceptions.ResourceNotFoundException;
import com.mainpackage.blogappapis.payloads.RoleDto;
import com.mainpackage.blogappapis.repositories.RoleRepo;
import com.mainpackage.blogappapis.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepo roleRepository;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public List<RoleDto> getALlRoles() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream().map(role -> modelMapper.map(role, RoleDto.class)).collect(Collectors.toList());
    }

    @Override
    public RoleDto getRolesById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        Role role = optionalRole.orElseThrow(() -> new ResourceNotFoundException("Role"," Id ",  id));
        return modelMapper.map(role,RoleDto.class);
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {

        Optional<Role> existingRole = roleRepository.findById(roleDto.getId());
        Set<Permission> truePermissions = new HashSet<>();

        if (existingRole.isPresent() && roleDto!= null) {
            for(Permission permission: roleDto.getPermissions()){
                if(permission.isStatus()){
                    truePermissions.add(permission);
                }
            }
            existingRole.get().setPermissions(truePermissions);
        }
//
//        Role roles = modelMapper.map(roleDto,Role.class);
//        Role savedRole = roleRepository.save(existingRole);

        return modelMapper.map(roleRepository.save(existingRole.get()),RoleDto.class);
    }

    @Override
    public RoleDto updateRoleById(Long id, RoleDto roleDto) {
        Role role = roleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role ", " Id ", id));

        role.setName(roleDto.getName());
        role.setStatus(Boolean.TRUE);

        Role updatedRole = roleRepository.save(role);
        return modelMapper.map(updatedRole,RoleDto.class);
    }

    @Override
    public void deleteRoleById(Long id) {
        if(!roleRepository.existsById(id)){
            throw new ResourceNotFoundException("Role "," Id ",+id);
        }
        roleRepository.deleteById(id);
    }
}
