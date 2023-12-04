package com.mainpackage.blogappapis.services.impl;

import com.mainpackage.blogappapis.entities.Category;
import com.mainpackage.blogappapis.entities.Post;
import com.mainpackage.blogappapis.entities.User;
import com.mainpackage.blogappapis.exceptions.ResourceNotFoundException;
import com.mainpackage.blogappapis.payloads.CategoryDto;
import com.mainpackage.blogappapis.payloads.PostDto;
import com.mainpackage.blogappapis.payloads.PostResponse;
import com.mainpackage.blogappapis.payloads.UserDto;
import com.mainpackage.blogappapis.repositories.CategoryRepo;
import com.mainpackage.blogappapis.repositories.PostRepo;
import com.mainpackage.blogappapis.repositories.UserRepo;
import com.mainpackage.blogappapis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        Post post = this.modelMapper.map(postDto,Post.class);
        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", " id ",userId));
        Category cat = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", " id ",categoryId));
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(cat);
        Post newPost = postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer psotId) {
        Post post = postRepo.findById(psotId)
                .orElseThrow(()-> new ResourceNotFoundException("User", " id ",psotId));
        User user = userRepo.findById(postDto.getUser().getId())
                .orElseThrow(()-> new ResourceNotFoundException("User", " id ",postDto.getUser().getId()));
        Category category = categoryRepo.findById(postDto.getUser().getId())
                .orElseThrow(()-> new ResourceNotFoundException("Category", " id ",postDto.getUser().getId()));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setAddedDate(postDto.getAddedDate());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = postRepo.save(post);
        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto getPostById(Integer id) {
        Post post = postRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Post", " id ",id));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {

        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }

        Pageable p = PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost = postRepo.findAll(p);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream()
                .map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }
    @Override
    public PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize) {
        Category cat = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category"," id ", categoryId));
        Pageable p = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePost = this.postRepo.findByCategory(cat,p);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map((post) ->
                modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User"," id ", userId));
        Pageable p = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePost = postRepo.findByUser(user,p);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map(post ->
                modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }


//    @Override
//    public List<PostDto> searchPosts(String keyword) {
//        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
//
//        List<PostDto> postDtos = posts.stream()
//                .map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//        return postDtos;
//    }
    //  Now making the above method using query which we have made in the repo
        @Override
        public List<PostDto> searchPosts(String keyword) {
            List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");

            List<PostDto> postDtos = posts.stream()
                    .map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
            return postDtos;
        }
    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", " id ",postId));
        postRepo.delete(post);
    }
}
