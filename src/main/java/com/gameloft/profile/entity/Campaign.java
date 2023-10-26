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
    @GeneratedValue(generator = "campaignSequenceGenerator")
    @SequenceGenerator(
            name = "campaignSequenceGenerator",
            sequenceName = "campaign_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "campaign_name")
    private String name;

//    @ManyToMany(mappedBy = "activeCampaigns")
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private Set<Player> players;
}
