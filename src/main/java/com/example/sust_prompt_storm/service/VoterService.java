package com.example.sust_prompt_storm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.sust_prompt_storm.dto.VoterRegistrationRequest;
import com.example.sust_prompt_storm.dto.VoterRegistrationResponse;
import com.example.sust_prompt_storm.entity.Voter;
import com.example.sust_prompt_storm.repository.VoterRepository;

@Service
public class VoterService {
    
    @Autowired
    private VoterRepository voterRepository;
    
    /**
     * Register a new voter with validation
     * @param request the voter registration request
     * @return the voter registration response
     * @throws ResponseStatusException if voter already exists or validation fails
     */
    public VoterRegistrationResponse registerVoter(VoterRegistrationRequest request) {
        // Check if voter with the same ID already exists
        if (voterRepository.existsByVoterId(request.getVoterId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, 
                "Voter with ID " + request.getVoterId() + " already exists");
        }
        
        // Additional age validation (though already validated in DTO)
        if (request.getAge() < 18) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Age must be at least 18 years");
        }
        
        // Create new voter entity
        Voter voter = new Voter(request.getVoterId(), request.getName(), request.getAge());
        
        // Save to database
        Voter savedVoter = voterRepository.save(voter);
        
        // Create and return response
        return new VoterRegistrationResponse(
            savedVoter.getVoterId(),
            savedVoter.getName(),
            savedVoter.getAge(),
            savedVoter.getHasVoted()
        );
    }
}
