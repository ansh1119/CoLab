package com.example.Collab.Service;

import com.example.Collab.Model.Team;
import com.example.Collab.Model.User;
import com.example.Collab.Repository.TeamRepository;
import com.example.Collab.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team addMember(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        team.getMembers().add(user);
        return teamRepository.save(team);
    }

    public List<Team> getTeamsByUser(Long userId) {
        return teamRepository.findByMembersId(userId);
    }

    public List<Team> getTeamsByHackathon(Long hackathonId) {
        return teamRepository.findByHackathonId(hackathonId);
    }

    public Team createDm(Long user1Id, Long user2Id) {
        // Return existing DM if one already exists between these two users
        List<Team> user1Teams = teamRepository.findByMembersId(user1Id);
        for (Team t : user1Teams) {
            boolean hasUser2 = t.getMembers().stream().anyMatch(m -> m.getId().equals(user2Id));
            if (hasUser2 && t.getHackathon() == null) {
                return t;
            }
        }

        User user1 = userRepository.findById(user1Id).orElseThrow();
        User user2 = userRepository.findById(user2Id).orElseThrow();

        Team team = new Team();
        team.setName(user1.getUsername() + " & " + user2.getUsername());
        team.getMembers().add(user1);
        team.getMembers().add(user2);

        return teamRepository.save(team);
    }
}
