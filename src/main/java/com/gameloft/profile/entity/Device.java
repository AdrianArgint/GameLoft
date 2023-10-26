package com.gameloft.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue
    private Long id;
    private String model;
    private String carrier;
    private String firmware;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;
}
