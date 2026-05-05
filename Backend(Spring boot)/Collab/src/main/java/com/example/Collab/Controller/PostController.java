package com.example.Collab.Controller;

import com.example.Collab.Model.Post;
import com.example.Collab.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private com.example.Collab.Repository.PostRepository postRepository;

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/type/{type}")
    public List<Post> getPostsByType(@PathVariable Post.PostType type) {
        return postService.getPostsByType(type);
    }

    @GetMapping("/hackathon/{hackathonId}")
    public List<Post> getPostsByHackathon(@PathVariable Long hackathonId) {
        return postService.getPostsByHackathon(hackathonId);
    }

    @PostMapping("/recommend")
    public List<Post> getRecommendedPosts(@RequestBody List<String> userSkills) {
        return postService.getRecommendedPosts(userSkills);
    }

    @PostMapping("/{id}/like")
    public Post likePost(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setLikeCount(post.getLikeCount() + 1);
        return postRepository.save(post);
    }

    @PostMapping("/{id}/comment")
    public Post commentPost(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setCommentCount(post.getCommentCount() + 1);
        return postRepository.save(post);
    }

    @PostMapping("/{id}/repost")
    public Post repostPost(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setRepostCount(post.getRepostCount() + 1);
        return postRepository.save(post);
    }
}
