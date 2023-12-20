package com.mainpackage.blogappapis.repositories;

import com.mainpackage.blogappapis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    User findByName(String name);
}
