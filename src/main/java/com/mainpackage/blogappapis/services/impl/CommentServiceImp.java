package com.mainpackage.blogappapis.services.impl;

import com.mainpackage.blogappapis.entities.Comment;
import com.mainpackage.blogappapis.entities.Post;
import com.mainpackage.blogappapis.exceptions.ResourceNotFoundException;
import com.mainpackage.blogappapis.payloads.CommentDto;
import com.mainpackage.blogappapis.repositories.CommentRepo;
import com.mainpackage.blogappapis.repositories.PostRepo;
import com.mainpackage.blogappapis.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post"," id " , postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment saveComment = commentRepo.save(comment);
        return modelMapper.map(saveComment,CommentDto.class);
    }

    @Override
    public void deleteCommment(Integer commentId) {

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment"," id ",commentId));

        commentRepo.delete(comment);
    }
}
