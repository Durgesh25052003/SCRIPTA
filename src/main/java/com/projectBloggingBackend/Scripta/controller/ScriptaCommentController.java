package com.projectBloggingBackend.Scripta.controller;

import com.projectBloggingBackend.Scripta.dtos.CommentRequestDTO;
import com.projectBloggingBackend.Scripta.dtos.CommentResponseDTO;
import com.projectBloggingBackend.Scripta.model.Comment;
import com.projectBloggingBackend.Scripta.repository.ScriptaCommentRepo;
import com.projectBloggingBackend.Scripta.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scripta/comment")
public class ScriptaCommentController {
    private final CommentService commentService;

    public ScriptaCommentController(CommentService commentService){
        this.commentService=commentService;
    }


    @GetMapping("/")
    public List<CommentResponseDTO>getAllComment(@RequestParam int page,@RequestParam int size){
        return commentService.getAllComments(page,size);
    }

    @PostMapping("/{postId}/addComment")
    public ResponseEntity<CommentResponseDTO> addPost(@PathVariable long postId, @RequestBody CommentRequestDTO comment){
        return commentService.addComment(postId,comment);
    }
    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<String>deleteComment(@PathVariable long commentId){
        return commentService.deleteComment(commentId);
    }
}
