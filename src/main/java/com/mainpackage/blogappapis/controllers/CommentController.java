package com.mainpackage.blogappapis.controllers;

import com.mainpackage.blogappapis.payloads.ApiResponse;
import com.mainpackage.blogappapis.payloads.CommentDto;
import com.mainpackage.blogappapis.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId) {
        CommentDto comment1 = commentService.createComment(comment, postId);
        return new ResponseEntity<>(comment1, HttpStatus.CREATED);
    }

    @DeleteMapping("deleteComment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {

        commentService.deleteCommment(commentId);
        return new ResponseEntity<>(new ApiResponse("comment deleted successfully", true), HttpStatus.OK);
    }
}
