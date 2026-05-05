package com.example.Collab.Config;

import com.example.Collab.Model.Hackathon;
import com.example.Collab.Model.Post;
import com.example.Collab.Model.User;
import com.example.Collab.Repository.HackathonRepository;
import com.example.Collab.Repository.PostRepository;
import com.example.Collab.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final HackathonRepository hackathonRepository;
    private final PostRepository postRepository;

    public DataInitializer(UserRepository userRepository, HackathonRepository hackathonRepository,
            PostRepository postRepository) {
        this.userRepository = userRepository;
        this.hackathonRepository = hackathonRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if (userRepository.count() == 0) {
                System.out.println("Seeding Database with Dummy Data...");

                // Users
                User alice = createUser("Alice", "alice@example.com", "Full Stack Dev looking for innovative projects.",
                        Arrays.asList("React", "Node.js", "MongoDB"));
                User bob = createUser("Bob", "bob@example.com", "Android Enthusiast and Kotlin Expert.",
                        Arrays.asList("Kotlin", "Android", "Jetpack Compose"));
                User charlie = createUser("Charlie", "charlie@example.com", "AI/ML Engineer.",
                        Arrays.asList("Python", "TensorFlow", "PyTorch"));
                User david = createUser("David", "david@example.com", "Backend Wizard.",
                        Arrays.asList("Java", "Spring Boot", "SQL"));
                User eve = createUser("Eve", "eve@example.com", "UI/UX Designer and Frontend Dev.",
                        Arrays.asList("Figma", "CSS", "React"));

                // Hackathons
                Hackathon hackMIT = createHackathon("HackMIT 2026", "One of the largest undergraduate hackathons.",
                        "2026-10-01");
                Hackathon ethGlobal = createHackathon("ETHGlobal London", "Build the future of Web3.", "2026-11-15");
                Hackathon googleHash = createHackathon("Google Hash Code", "Team-based programming competition.",
                        "2026-02-20");

                // Posts
                createPost(Post.PostType.LOOKING_FOR_TEAM, "Looking for a team for HackMIT. I do backend.",
                        Arrays.asList("Java", "Spring Boot"), alice, hackMIT);
                createPost(Post.PostType.LOOKING_FOR_MEMBER, "Need a UI Designer for our FinTech app idea.",
                        Arrays.asList("Figma", "React"), bob, null);
                createPost(Post.PostType.LOOKING_FOR_TEAM, "Beginner looking to learn and contribute.",
                        Arrays.asList("Python", "HTML"), charlie, googleHash);
                createPost(Post.PostType.LOOKING_FOR_MEMBER, "Building an AI-powered Chatbot. Need NLP expert.",
                        Arrays.asList("Python", "NLP", "OpenAI"), david, ethGlobal);
                createPost(Post.PostType.LOOKING_FOR_TEAM, "Expert Frontend Dev looking for a serious team.",
                        Arrays.asList("React", "TypeScript", "Next.js"), eve, null);
                createPost(Post.PostType.LOOKING_FOR_MEMBER, "Need a mobile dev for a health tracking app.",
                        Arrays.asList("Flutter", "Dart", "Firebase"), alice, null);

                System.out.println("Database Seeding Complete!");
            }
        } catch (Exception e) {
            System.err.println("Error seeding database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private User createUser(String username, String email, String bio, List<String> techStack) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("password"); // Default password
        user.setBio(bio);
        user.setTechStack(techStack);
        return userRepository.save(user);
    }

    private Hackathon createHackathon(String title, String description, String date) {
        Hackathon hackathon = new Hackathon();
        hackathon.setTitle(title);
        hackathon.setDescription(description);
        hackathon.setDate(LocalDate.parse(date));
        hackathon.setOrganizerName("CoLabCraft Team");
        return hackathonRepository.save(hackathon);
    }

    private void createPost(Post.PostType type, String description, List<String> techStack, User user,
            Hackathon hackathon) {
        Post post = new Post();
        post.setType(type);
        post.setDescription(description);
        post.setRequiredTechStack(techStack);
        post.setUser(user);
        post.setHackathon(hackathon);
        postRepository.save(post);
    }
}
