package com.mainpackage.blogappapis.services.impl;

import com.mainpackage.blogappapis.entities.Permission;
import com.mainpackage.blogappapis.exceptions.ResourceNotFoundException;
import com.mainpackage.blogappapis.payloads.PermissionDto;
import com.mainpackage.blogappapis.repositories.PermissionRepo;
import com.mainpackage.blogappapis.services.PermissionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionRepo permissionRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public List<PermissionDto> getALlPermissions() {
        List<Permission> permissions = permissionRepository.findAll();

        return permissions.stream().map(permission -> modelMapper.map(permission, PermissionDto.class)).collect(Collectors.toList());
    }


    public List<Permission> getActivePermissions() {
        return permissionRepository.getActivePermissions();
    }

    @Override
    public PermissionDto getPermissionsById(Long id) {
        Optional<Permission> optionalPermission = permissionRepository.findById(id);
        Permission permission = optionalPermission.orElseThrow(() -> new ResourceNotFoundException("Permission  "," Id ", id));
        return modelMapper.map(permission,PermissionDto.class);
    }

    @Override
    public PermissionDto createPermission(PermissionDto permissionDto) {
        Permission permission = modelMapper.map(permissionDto,Permission.class);
        Permission savedPermission = permissionRepository.save(permission);

        return modelMapper.map(savedPermission,PermissionDto.class);
    }

    @Override
    public PermissionDto updatePermissionById(Long id, PermissionDto permissionDto) {

        Optional<Permission> existPermission = permissionRepository.findById(id);

        if(existPermission.isPresent()){
            existPermission.get().setName(permissionDto.getName());
            return modelMapper.map(permissionRepository.save(existPermission.get()),PermissionDto.class);
        }
        throw new RuntimeException(String.format("Permission Not Found by this Id => %d" , id));
    }

    @Override
    public PermissionDto deletePermissionById(Long id) {

        //  here we are actually assigning false to permission, but not deleting it.
        Optional<Permission> permission = permissionRepository.findById(id);

        if(permission.isPresent()){
            permission.get().setStatus(Boolean.FALSE);
            return modelMapper.map(permissionRepository.save(permission.get()),PermissionDto.class);
        }
        throw new RuntimeException(String.format("Permission Not Found by this Id => %d" , id));
    }


    public Permission makePermissionActive(Long id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        if(permission.isPresent()){
            if(permission.get().isStatus()){
                throw new RuntimeException("Record is already Active");
            }
            permission.get().setStatus(Boolean.TRUE);
            return permissionRepository.save(permission.get());
        }
        throw new RuntimeException(String.format("Permission Not Found by this Id => %d" , id));
    }
}
