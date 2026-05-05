package com.example.Collab.Service;

import com.example.Collab.Model.Post;
import com.example.Collab.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll(
                org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "id"));
    }

    public List<Post> getPostsByHackathon(Long hackathonId) {
        return postRepository.findByHackathonId(hackathonId);
    }

    public List<Post> getPostsByType(Post.PostType type) {
        return postRepository.findByType(type);
    }

    // AI / Heuristic Matchmaking
    public List<Post> getRecommendedPosts(List<String> userSkills) {
        List<Post> allPosts = postRepository.findAll();

        return allPosts.stream()
                .sorted(Comparator.comparingInt(post -> -calculateMatchScore(post, userSkills))) // Descending order
                .limit(10)
                .collect(Collectors.toList());
    }

    private int calculateMatchScore(Post post, List<String> userSkills) {
        if (post.getRequiredTechStack() == null || userSkills == null)
            return 0;

        // Count how many required skills the user has
        long matches = post.getRequiredTechStack().stream()
                .filter(skill -> userSkills.contains(skill))
                .count();
        return (int) matches;
    }
}
