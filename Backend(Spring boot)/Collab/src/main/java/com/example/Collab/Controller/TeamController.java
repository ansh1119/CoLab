package com.example.Collab.Controller;

import com.example.Collab.Model.Team;
import com.example.Collab.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "*")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    @GetMapping("/hackathon/{hackathonId}")
    public List<Team> getTeamsByHackathon(@PathVariable Long hackathonId) {
        return teamService.getTeamsByHackathon(hackathonId);
    }

    @PostMapping("/{teamId}/members/{userId}")
    public Team addMember(@PathVariable Long teamId, @PathVariable Long userId) {
        return teamService.addMember(teamId, userId);
    }

    @GetMapping("/user/{userId}")
    public List<Team> getTeamsByUser(@PathVariable Long userId) {
        return teamService.getTeamsByUser(userId);
    }

    @PostMapping("/dm")
    public Team createDm(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return teamService.createDm(user1Id, user2Id);
    }
}
