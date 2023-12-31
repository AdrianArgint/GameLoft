package com.gameloft.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
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

}
