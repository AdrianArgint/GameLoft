package com.gameloft.profile.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CampaignDTO {
    private String game;
    private String name;
    private Double priority;
    private Instant startDate;
    private Instant endDate;
    private Boolean enabled;
    private Instant lastUpdated;
    Map<String, Map<String, Object>> matchers;
}
