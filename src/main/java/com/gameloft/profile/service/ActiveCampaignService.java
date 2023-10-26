package com.gameloft.profile.service;

import com.gameloft.profile.service.dto.CampaignDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class ActiveCampaignService {
    public CampaignDTO getActiveCampaign() {
        return CampaignDTO.builder()
                .game("mygame")
                .name("mycampaign")
                .priority(10.5)
                .matchers(Map.of(
                        "level", Map.of("min", 1L, "max", 3L),
                        "total_spent", Map.of("min", 150L),
                        "total_transactions", Map.of("min", 5L),
                        "total_refund", Map.of("min", 10L),
                        "has", Map.of("country", List.of("US", "RO", "CA"),
                                            "items", List.of("Inventory Item 2", "Inventory Item 10"),
                                            "gender", List.of("female"),
                                              "clan", List.of("Eternal Guardians")),
                        "does_not_have", Map.of("items", List.of("Inventory Item 1"))
                ))
                .startDate(Instant.parse("2022-01-25T00:00:00Z"))
                .endDate(Instant.parse("2022-02-25T00:00:00Z"))
                .enabled(Boolean.TRUE)
                .lastUpdated(Instant.parse("2021-07-13T11:46:58Z"))
                .build();
    }
}
