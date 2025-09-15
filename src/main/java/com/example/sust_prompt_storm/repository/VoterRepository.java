package com.example.sust_prompt_storm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sust_prompt_storm.entity.Voter;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Long> {
    
    /**
     * Check if a voter with the given voter ID already exists
     * @param voterId the voter ID to check
     * @return true if voter exists, false otherwise
     */
    boolean existsByVoterId(Long voterId);
    
    /**
     * Find a voter by voter ID
     * @param voterId the voter ID to search for
     * @return Optional containing the voter if found
     */
    Optional<Voter> findByVoterId(Long voterId);
}
