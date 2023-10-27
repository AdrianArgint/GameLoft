package com.gameloft.profile.entity;


import com.gameloft.profile.config.GenderEnumConverter;
import com.gameloft.profile.entity.enums.GenderEnum;
import com.gameloft.profile.service.dto.CampaignDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "player")
@Builder
public class Player {
    @Id
    @GeneratedValue
    @Column(name = "player_id")
    private UUID playerId;

    private String credential;

    private Instant created;
    private Instant modified;
    private Instant lastSession;

    private Double totalSpent;
    private Double totalRefund;
    private Long totalTransactions;
    private Instant lastPurchase;

    @ManyToMany
    @JoinTable(name = "campaign_player",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "campaign_id"))
    private Set<Campaign> activeCampaigns;

    @OneToMany(mappedBy = "player", orphanRemoval = true)
    private List<Device> devices;

    private Long level;
    private Double xp;
    private Long totalPlaytime;

    private String country;
    private String language;
    private Instant birthdate;
    @Convert(converter = GenderEnumConverter.class)
    private GenderEnum gender;

    @OneToMany(mappedBy = "player", orphanRemoval = true)
    private List<InventoryItem> inventory;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "clan_id", referencedColumnName = "id")
    private Clan clan;

    @PrePersist
    public void prePersist() {
        this.created = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = Instant.now();
    }

    public List<String> getValuesForKey(String key) {
        List<String> values = new ArrayList<>();

        if ("level".equals(key) && !Objects.isNull(level)) {
            values.add(String.valueOf(level));
        } else if ("country".equals(key) && !Objects.isNull(country)) {
            values.add(country);
        } else if ("items".equals(key) && !Objects.isNull(inventory)) {
            values.addAll(
                    Optional.ofNullable(inventory).orElse(List.of())
                            .stream().map(InventoryItem::getName).toList());
        } else if ("total_spent".equals(key) && !Objects.isNull(totalSpent)) {
            values.add(String.valueOf(totalSpent));
        } else if ("total_refund".equals(key) && !Objects.isNull(totalRefund)) {
            values.add(String.valueOf(totalRefund));
        } else if ("total_transactions".equals(key) && !Objects.isNull(totalTransactions)) {
            values.add(String.valueOf(totalTransactions));
        } else if ("language".equals(key) && !Objects.isNull(language)) {
            values.add(language);
        } else if ("gender".equals(key) && !Objects.isNull(gender)) {
            values.add(gender.getName());
        } else if ("clan".equals(key) && !Objects.isNull(clan)) {
            values.add(clan.getName());
        }

        // Add more conditions for other attributes as needed

        return values;
    }

    public void addActiveCampaign(Campaign campaignDTO) {
        this.activeCampaigns.add(campaignDTO);
    }
}
