package com.gameloft.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clan")
public class Clan {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    private String name;
}
