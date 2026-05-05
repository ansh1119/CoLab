package com.example.Collab.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PostType type; // LOOKING_FOR_TEAM, LOOKING_FOR_MEMBER

    private String description;

    @ElementCollection
    private List<String> requiredTechStack; // or offeredTechStack

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // The person who posted

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon; // Optional: linked to a specific event

    public enum PostType {
        LOOKING_FOR_TEAM,
        LOOKING_FOR_MEMBER
    }
}
