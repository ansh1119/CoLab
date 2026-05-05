package com.example.Collab.Service;

import com.example.Collab.Model.Message;
import com.example.Collab.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByTeam(Long teamId) {
        return messageRepository.findByTeamIdOrderByTimestampAsc(teamId);
    }
}
