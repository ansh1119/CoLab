package com.example.Collab.Controller;


import com.example.Collab.Service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TweetController {

    @Autowired
    private TweetService service;
}
