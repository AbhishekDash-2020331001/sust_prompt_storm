package com.example.sust_prompt_storm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sust_prompt_storm.dto.VoterRegistrationRequest;
import com.example.sust_prompt_storm.dto.VoterRegistrationResponse;
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
}
