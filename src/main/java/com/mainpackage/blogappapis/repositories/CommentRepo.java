package com.mainpackage.blogappapis.repositories;

import com.mainpackage.blogappapis.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
