package com.gameloft.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "campaign")
public class Campaign {
    @Id
    @GeneratedValue
    private Long id;

    private String campaignName;

    @ManyToMany(mappedBy = "activeCampaigns")
    private Set<Player> players;
}
