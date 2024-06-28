package com.example.Collab.Repository;

import com.example.Collab.Model.Tweets;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface TweetRepository extends JpaRepository<Tweets, Integer> {
}
