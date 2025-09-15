package com.example.sust_prompt_storm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotersListResponse {
    
    private List<VoterInfo> voters;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoterInfo {
        private Long voter_id;
        private String name;
        private Integer age;
    }
}
