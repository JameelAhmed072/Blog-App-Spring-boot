package com.mainpackage.blogappapis.services;

import com.mainpackage.blogappapis.payloads.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    CommentDto createComment(CommentDto commentDto,Integer postId);

    void deleteCommment(Integer commentId);
}
