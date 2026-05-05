package com.example.Collab.Service;

import com.example.Collab.Model.Hackathon;
import com.example.Collab.Repository.HackathonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HackathonService {

    @Autowired
    private HackathonRepository hackathonRepository;

    public Hackathon createHackathon(Hackathon hackathon) {
        return hackathonRepository.save(hackathon);
    }

    public List<Hackathon> getAllHackathons() {
        return hackathonRepository.findAll();
    }

    public Hackathon getHackathonById(Long id) {
        return hackathonRepository.findById(id).orElseThrow(() -> new RuntimeException("Hackathon not found"));
    }
}
