package com.gameloft.profile.entity;


import com.gameloft.profile.entity.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue
    @Column(name = "player_id")
    private String playerId;

    private String credential;

    private Instant created;
    private Instant modified;
    private Instant lastSession;

    private Double totalSpent;
    private Double totalRefund;
    private Long totalTransactions;
    private Instant lastPurchase;

    @ManyToMany
    @JoinTable(name = "player_campaigns",
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
    private Instant birthDate;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @OneToMany(mappedBy = "player", orphanRemoval = true)
    private List<InventoryItem> inventory;

    @OneToOne(orphanRemoval = true)
    private Clan clan;

    @PrePersist
    public void prePersist() {
        this.created = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = Instant.now();
    }

}
