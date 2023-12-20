package com.mainpackage.blogappapis.repositories;

import com.mainpackage.blogappapis.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepo extends JpaRepository<Permission,Long> {


    @Query("SELECT p FROM Permission p WHERE p.status = true")
    List<Permission> getActivePermissions();
}
