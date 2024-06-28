package com.example.Collab.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    private int id;
    private String name;
    private String email;
    private int year;
    private String branch;
    private String domain1;
    private String domain2;
    private List<Integer> tweets;
}
