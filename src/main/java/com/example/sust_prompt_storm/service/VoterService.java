package com.example.sust_prompt_storm.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.sust_prompt_storm.dto.VoterRegistrationRequest;
import com.example.sust_prompt_storm.dto.VoterRegistrationResponse;
import com.example.sust_prompt_storm.dto.VoterUpdateRequest;
import com.example.sust_prompt_storm.dto.VotersListResponse;
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
        if (voterRepository.existsByVoterId(request.getVoter_id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, 
                "voter with id: " + request.getVoter_id() + " already exists");
        }
        
        // Additional age validation (though already validated in DTO)
        if (request.getAge() < 18) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, 
                "Age must be 18 or older");
        }
        
        // Create new voter entity
        Voter voter = new Voter(request.getVoter_id(), request.getName(), request.getAge());
        
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
    
    /**
     * Get voter by ID
     * @param voterId the voter ID to search for
     * @return the voter registration response
     * @throws ResponseStatusException if voter not found
     */
    public VoterRegistrationResponse getVoterById(Long voterId) {
        Optional<Voter> voterOptional = voterRepository.findByVoterId(voterId);
        
        if (voterOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, 
                "voter with id: " + voterId + " was not found");
        }
        
        Voter voter = voterOptional.get();
        return new VoterRegistrationResponse(
            voter.getVoterId(),
            voter.getName(),
            voter.getAge(),
            voter.getHasVoted()
        );
    }
    
    /**
     * Get all voters
     * @return list of all voters
     */
    public VotersListResponse getAllVoters() {
        List<Voter> voters = voterRepository.findAll();
        
        List<VotersListResponse.VoterInfo> voterInfos = voters.stream()
            .map(voter -> new VotersListResponse.VoterInfo(
                voter.getVoterId(),
                voter.getName(),
                voter.getAge()
            ))
            .collect(Collectors.toList());
        
        return new VotersListResponse(voterInfos);
    }
    
    /**
     * Update voter information
     * @param voterId the voter ID to update
     * @param request the voter update request
     * @throws ResponseStatusException if voter not found or validation fails
     */
    public void updateVoter(Long voterId, VoterUpdateRequest request) {
        // Check if voter exists
        Optional<Voter> voterOptional = voterRepository.findByVoterId(voterId);
        if (voterOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, 
                "voter with id: " + voterId + " was not found");
        }
        
        // Additional age validation (though already validated in DTO)
        if (request.getAge() < 18) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, 
                "invalid age: " + request.getAge() + ", must be 18 or older");
        }
        
        // Update voter information
        Voter voter = voterOptional.get();
        voter.setName(request.getName());
        voter.setAge(request.getAge());
        
        // Save updated voter
        voterRepository.save(voter);
    }
}
