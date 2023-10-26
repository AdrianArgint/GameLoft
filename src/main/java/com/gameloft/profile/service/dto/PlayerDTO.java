package com.gameloft.profile.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private UUID playerId;

    private String credential;

    private Instant created;
    private Instant modified;
    private Instant lastSession;

    private Double totalSpent;
    private Double totalRefund;
    private Long totalTransactions;
    private Instant lastPurchase;

    private Set<String> activeCampaigns;

    private List<DeviceDTO> devices;

    private Long level;
    private Double xp;
    private Long totalPlaytime;

    private String country;
    private String language;
    private Instant birthdate;
    private String gender;

    private Map<String, Long> inventory;

    private ClanDTO clan;
}
