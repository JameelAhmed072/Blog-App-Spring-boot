package com.mainpackage.blogappapis.repositories;

import com.mainpackage.blogappapis.entities.Category;
import com.mainpackage.blogappapis.entities.Post;
import com.mainpackage.blogappapis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    Page<Post> findByUser(User user,Pageable p);
    Page<Post> findByCategory(Category category,Pageable p);




//    List<Post> findByTitleContaining(String title);
        //  by making the above search using query
    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String title);

}
