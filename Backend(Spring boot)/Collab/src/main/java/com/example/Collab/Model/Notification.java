package com.example.Collab.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private boolean isRead = false;

    @ManyToOne
    private User recipient;

    private String type; // e.g., "TEAM_INVITE", "JOIN_REQUEST"

    private Long relatedEntityId; // e.g., Team ID
}
