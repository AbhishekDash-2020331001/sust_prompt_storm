package com.example.sust_prompt_storm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sust_prompt_storm.dto.VoterRegistrationRequest;
import com.example.sust_prompt_storm.dto.VoterRegistrationResponse;
import com.example.sust_prompt_storm.dto.VoterUpdateRequest;
import com.example.sust_prompt_storm.dto.VotersListResponse;
import com.example.sust_prompt_storm.service.VoterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class VoterController {
    
    @Autowired
    private VoterService voterService;
    
    /**
     * Register a new voter
     * @param request the voter registration request
     * @return ResponseEntity with voter registration response
     */
    @PostMapping("/voters")
    public ResponseEntity<VoterRegistrationResponse> registerVoter(
            @Valid @RequestBody VoterRegistrationRequest request) {
        
        VoterRegistrationResponse response = voterService.registerVoter(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Get all voters
     * @return ResponseEntity with list of all voters
     */
    @GetMapping("/voters")
    public ResponseEntity<VotersListResponse> getAllVoters() {
        VotersListResponse response = voterService.getAllVoters();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    /**
     * Update voter information
     * @param voterId the voter ID to update
     * @param request the voter update request
     * @return ResponseEntity with empty body for successful update
     */
    @PutMapping("/voters/{voter_id}")
    public ResponseEntity<Void> updateVoter(
            @PathVariable("voter_id") Long voterId,
            @Valid @RequestBody VoterUpdateRequest request) {
        
        voterService.updateVoter(voterId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    /**
     * Get voter by ID
     * @param voterId the voter ID to retrieve
     * @return ResponseEntity with voter information
     */
    @GetMapping("/voters/{voter_id}")
    public ResponseEntity<VoterRegistrationResponse> getVoterById(
            @PathVariable("voter_id") Long voterId) {
        
        VoterRegistrationResponse response = voterService.getVoterById(voterId);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }
}
