package com.example.Collab.Controller;

import com.example.Collab.Model.Message;
import com.example.Collab.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    @GetMapping("/team/{teamId}")
    public List<Message> getMessagesByTeam(@PathVariable Long teamId) {
        return messageService.getMessagesByTeam(teamId);
    }
}
