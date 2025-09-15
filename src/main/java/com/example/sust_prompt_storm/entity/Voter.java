package com.example.sust_prompt_storm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "voters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voter {
    
    @Id
    @Column(name = "voter_id")
    private Long voterId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "age", nullable = false)
    private Integer age;
    
    @Column(name = "has_voted", nullable = false)
    private Boolean hasVoted = false;
    
    // Constructor for registration (without hasVoted parameter)
    public Voter(Long voterId, String name, Integer age) {
        this.voterId = voterId;
        this.name = name;
        this.age = age;
        this.hasVoted = false;
    }
}
