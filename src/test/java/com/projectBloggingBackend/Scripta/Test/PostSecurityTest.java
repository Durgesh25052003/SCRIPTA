package com.projectBloggingBackend.Scripta.Test;

import com.projectBloggingBackend.Scripta.model.Like;
import com.projectBloggingBackend.Scripta.model.Post;
import com.projectBloggingBackend.Scripta.model.User;
import com.projectBloggingBackend.Scripta.repository.ScriptaLikeRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaPostRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaUserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyLong;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PostSecurityTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ScriptaPostRepo scriptaPostRepo;

    @MockitoBean
    private ScriptaUserRepo scriptaUserRepo;

    @MockitoBean
    private ScriptaLikeRepo scriptaLikeRepo;

    @Test

    void unauthenticatedUserCannotCreatePost()throws Exception{
        mockMvc.perform(
                post("/api/v1/scripta/post").contentType(MediaType.APPLICATION_JSON).content("""
                        {
                        "title": "Hello",
                        "content": "My first post"
                        }
                        """)
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username="Mic123@gmail.com" , roles="USER")
    void authenticatedUserCanCreatePost() throws Exception {
        // 1. Setup User Mock
        User mockUser = new User();
        mockUser.setUserID(1L);
        mockUser.setEmail("Mic123@gmail.com");

        // 2. Setup Post Mock
        Post mockSavedPost = new Post();
        mockSavedPost.setPostId(11L);
        mockSavedPost.setTitle("Hello");
        mockSavedPost.setContent("My first post");
        mockSavedPost.setAuthor(mockUser); // Link them to avoid NPE in DTO mapping

        // 3. Stubbing (Ensure these are mocks!)
        Mockito.when(scriptaUserRepo.findByEmail("Mic123@gmail.com"))
                .thenReturn(Optional.of(mockUser));

        Mockito.when(scriptaPostRepo.save(Mockito.any(Post.class)))
                .thenReturn(mockSavedPost);

        // 4. Perform with CSRF
        mockMvc.perform(
                post("/api/v1/scripta/post/createPost")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "title": "Hello",
                      "content": "My first post"
                    }
                    """)
        ).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username="Mic123@gmail.com" , roles="USER")
    void nonOwnerCannotDeletePost()throws Exception{
        String loggedInEmail = "Mic123@gmail.com";
         User owner=new User();
         owner.setUserID(2l);
         owner.setEmail("user@gmail.com");

         Post post = new Post();
         post.setPostId(4L);
         post.setTitle("adc");
         post.setCreatedAt(LocalDateTime.now());
         post.setContent("adc");
         post.setAuthor(owner);

        Mockito.when(scriptaPostRepo.existsById(4L)).thenReturn(true);
        Mockito.when(scriptaPostRepo.findById(4L))
                .thenReturn(Optional.of(post));
        User loggedInUser = new User();
        loggedInUser.setUserID(99L);
        loggedInUser.setEmail(loggedInEmail);
// Mock the user repo so the controller finds the "authenticated" user
        Mockito.when(scriptaUserRepo.findByEmail(loggedInEmail))
                .thenReturn(Optional.of(loggedInUser));
        mockMvc.perform(
                        delete("/api/v1/scripta/post/deletePost/4")
                )
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "dns123@gmail.com", roles = "ADMIN") // Roles="ADMIN" maps to ROLE_ADMIN
    void adminCanDeleteAnyPost() throws Exception {
        Long postId = 10L;
        String adminEmail = "dns123@gmail.com";

        // 1. Create the Post and the Admin User objects
        Post post = new Post();
        post.setPostId(postId);

        User adminUser = new User();
        adminUser.setEmail(adminEmail);
        adminUser.setUserID(999L); // ID doesn't matter for Admin, but object must exist

        // 2. Mock the POST repository (not Like repository)
        Mockito.when(scriptaPostRepo.existsById(postId)).thenReturn(true);
        Mockito.when(scriptaPostRepo.findById(postId)).thenReturn(Optional.of(post));

        // 3. Mock the USER repository so the Admin is found
        Mockito.when(scriptaUserRepo.findByEmail(adminEmail))
                .thenReturn(Optional.of(adminUser));

        // 4. Perform the request
        mockMvc.perform(
                        delete("/api/v1/scripta/post/deletePost/" + postId)
                                .with(csrf())
                )
                .andExpect(status().isOk());
    }
}
