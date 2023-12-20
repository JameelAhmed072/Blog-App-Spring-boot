package com.mainpackage.blogappapis.services;

import com.mainpackage.blogappapis.payloads.PermissionDto;

import java.util.List;

public interface PermissionService {

    List<PermissionDto> getALlPermissions();


    public PermissionDto getPermissionsById(Long id);

    public PermissionDto createPermission(PermissionDto permissionDto);

    PermissionDto updatePermissionById(Long id,PermissionDto permissionDto);

    public PermissionDto deletePermissionById(Long id);
}
