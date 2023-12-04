package com.mainpackage.blogappapis.services;

import com.mainpackage.blogappapis.entities.Post;
import com.mainpackage.blogappapis.payloads.CategoryDto;
import com.mainpackage.blogappapis.payloads.PostDto;
import com.mainpackage.blogappapis.payloads.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer psotId);

    PostDto getPostById(Integer id);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    void deletePost(Integer postId);

    PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize);

    PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize);

    List<PostDto> searchPosts(String keyword);
}
