package com.gameloft.profile.entity;


import com.gameloft.profile.config.GenderEnumConverter;
import com.gameloft.profile.entity.enums.GenderEnum;
import com.gameloft.profile.service.dto.CampaignDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "player")
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

        if ("level".equals(key)) {
            values.add(String.valueOf(level));
        } else if ("country".equals(key)) {
            values.add(country);
        } else if ("items".equals(key)) {
            values.addAll(inventory.stream().map(InventoryItem::getName).toList());
        } else if ("total_spent".equals(key)) {
            values.add(String.valueOf(totalSpent));
        } else if ("total_refund".equals(key)) {
            values.add(String.valueOf(totalRefund));
        } else if ("total_transactions".equals(key)) {
            values.add(String.valueOf(totalTransactions));
        } else if ("language".equals(key)) {
            values.add(language);
        } else if ("gender".equals(key)) {
            values.add(gender.getName());
        } else if ("clan".equals(key)) {
            values.add(clan.getName());
        }

        // Add more conditions for other attributes as needed

        return values;
    }

    public void addActiveCampaign(Campaign campaignDTO) {
        this.activeCampaigns.add(campaignDTO);
    }
}
