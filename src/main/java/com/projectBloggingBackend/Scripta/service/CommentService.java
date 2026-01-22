package com.projectBloggingBackend.Scripta.service;

import com.projectBloggingBackend.Scripta.dtos.CommentRequestDTO;
import com.projectBloggingBackend.Scripta.dtos.CommentResponseDTO;
import com.projectBloggingBackend.Scripta.exception.ForbiddenAccess;
import com.projectBloggingBackend.Scripta.model.Comment;
import com.projectBloggingBackend.Scripta.model.Post;
import com.projectBloggingBackend.Scripta.model.User;
import com.projectBloggingBackend.Scripta.repository.ScriptaCommentRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaPostRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaUserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final ScriptaPostRepo scriptaPostRepo;
    private final ScriptaUserRepo scriptaUserRepo;
    private final ScriptaCommentRepo scriptaCommentRepo;

    public CommentService(ScriptaPostRepo scriptaPostRepo,ScriptaUserRepo scriptaUserRepo,ScriptaCommentRepo scriptaCommentRepo){
        this.scriptaPostRepo=scriptaPostRepo;
        this.scriptaUserRepo=scriptaUserRepo;
        this.scriptaCommentRepo=scriptaCommentRepo;
    }

    public List<CommentResponseDTO> getAllComments(int page,int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Comment> commentsList=scriptaCommentRepo.findAll(pageable);
        List<CommentResponseDTO>commentResponseDTOList=commentsList.stream().map(this::mapCommentDTO).toList();
        return commentResponseDTOList;
    }


   public ResponseEntity<CommentResponseDTO> addComment(Long id, CommentRequestDTO commentRequestDTO){
       //Post Instance Fetching
       Post post=scriptaPostRepo.findById(id).orElseThrow(()->new RuntimeException("Post Dosen't Exists..."));
       //Fetching User
       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
       String email=authentication.getName();
       User user=scriptaUserRepo.findByEmail(email);
       Comment comment=new Comment();
       comment.setAuthor(user);
       comment.setContent(commentRequestDTO.getContent());
       comment.setPost(post);
       comment.setCreatedAt(LocalDateTime.now());

       Comment commentPersisted=scriptaCommentRepo.save(comment);
       return new ResponseEntity<>(mapCommentDTO(commentPersisted), HttpStatus.CREATED);

   }
   public ResponseEntity<String>deleteComment(long id){
        if (!scriptaCommentRepo.existsById(id)) {
            return new ResponseEntity<>("Comment doesn't Exists...", HttpStatus.NOT_FOUND);
        }
        Comment comment = scriptaCommentRepo.findById(id).orElseThrow(()->new RuntimeException("Comment Doesn't Exists..."));
       //Getting authenticated User
       String email=SecurityContextHolder.getContext().getAuthentication().getName();
       User isUser=scriptaUserRepo.findByEmail(email);
       if(comment.getAuthor().getUserID()!=isUser.getUserID()){
           throw new ForbiddenAccess("Forbidden Access...");
       }
       scriptaCommentRepo.deleteById(id);
       return new ResponseEntity<>("Comment Deleted Successfully",HttpStatus.GONE);
   }
   public CommentResponseDTO mapCommentDTO(Comment comment){
        CommentResponseDTO commentResponseDTO= new CommentResponseDTO();
        commentResponseDTO.setId(comment.getCommentId());
        commentResponseDTO.setContent(comment.getContent());
        commentResponseDTO.setAuthorUsername(comment.getAuthor().getUserName());
        commentResponseDTO.setCreatedAt(comment.getCreatedAt());
        commentResponseDTO.setPostId(comment.getPost().getPostId());
        return commentResponseDTO;
   }
}
