package com.mainpackage.blogappapis.controllers;


import com.mainpackage.blogappapis.config.AppConstants;
import com.mainpackage.blogappapis.payloads.ApiResponse;
import com.mainpackage.blogappapis.payloads.PostDto;
import com.mainpackage.blogappapis.payloads.PostResponse;
import com.mainpackage.blogappapis.services.FileService;
import com.mainpackage.blogappapis.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    //   Create Posts
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
                                              @RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        PostDto createPost = postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }
    // get Posts by User
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
             @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
                                                        ){
        PostResponse postsByUser = postService.getPostsByUser(userId,pageNumber,pageSize);
        return new ResponseEntity<>(postsByUser,HttpStatus.OK);
    }
    //  get Post by Category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
              @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
              @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
                                                            ){
        PostResponse postsByCategory = postService.getPostsByCategory(categoryId,pageNumber,pageSize);
        return new ResponseEntity<>(postsByCategory,HttpStatus.OK);
    }
    //  Get all Posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> allPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    ){
        PostResponse allPosts = postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(allPosts,HttpStatus.OK);
    }
    //   Get Single Post By Id
    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer id){
        PostDto allPosts = postService.getPostById(id);
        return ResponseEntity.ok(allPosts);
    }
    //  Update Post
    @PutMapping("/post/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer id){
        PostDto updatedPost = postService.updatePost(postDto,id);
        return ResponseEntity.ok(updatedPost);
    }
    //  Delete Post By Id
    @DeleteMapping("/post/{id}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer id){
        postService.deletePost(id);
        return new ResponseEntity<>(new ApiResponse("Post deleted Successfully",true),HttpStatus.OK);
    }
    //  Search Post By Title
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
        List<PostDto> result = postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
    }
    // Post Image Upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam MultipartFile image,
            @PathVariable Integer postId
            ) throws IOException {
        PostDto postDto = postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatePost = postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }
    // Method to Serve Files
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
