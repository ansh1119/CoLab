package com.example.Collab.Service;


import com.example.Collab.Model.Tweets;
import com.example.Collab.Repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    @Autowired
    private TweetRepository repo;

    public void addTweet(Tweets tweet) {
        repo.save(tweet);
    }

    public List<Tweets> getTweets(){
        return repo.findAll();
    }

    public Tweets getTweetById(int id){
        return (Tweets) repo.findById(id).orElse(new Tweets());
    }
}
