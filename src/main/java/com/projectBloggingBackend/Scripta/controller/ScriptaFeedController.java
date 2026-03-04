package com.projectBloggingBackend.Scripta.controller;

import com.projectBloggingBackend.Scripta.dtos.PostResponseDTO;
import com.projectBloggingBackend.Scripta.service.FeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scripta/feed")
public class ScriptaFeedController {
    private final FeedService feedService;


    public ScriptaFeedController ( FeedService feedService){
        this.feedService=feedService;
    }
    @GetMapping("/")
    public List<PostResponseDTO> feed(){
        return feedService.feed();
    }
    
}
