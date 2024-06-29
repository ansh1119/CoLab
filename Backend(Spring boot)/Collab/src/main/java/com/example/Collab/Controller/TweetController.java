package com.example.Collab.Controller;


import com.example.Collab.Model.Tweets;
import com.example.Collab.Service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private TweetService service;

    @GetMapping("/all")
    public List<Tweets> getTweets(){
        return service.getTweets();
    }

    @PostMapping("/tweet")
    public void tweet(@RequestBody Tweets tweet){
        service.addTweet(tweet);
    }
}
