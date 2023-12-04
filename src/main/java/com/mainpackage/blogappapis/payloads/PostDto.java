package com.mainpackage.blogappapis.payloads;

import com.mainpackage.blogappapis.entities.Category;
import com.mainpackage.blogappapis.entities.Comment;
import com.mainpackage.blogappapis.entities.User;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {

    private Integer postId;
    private String title;
    private String content;

    private String imageName;
    private Date addedDate;

    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();

}
