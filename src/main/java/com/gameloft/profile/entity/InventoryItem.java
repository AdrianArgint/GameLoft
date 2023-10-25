package com.gameloft.profile.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_item")
public class InventoryItem {
    @Id
    @GeneratedValue
    private Long id;

    private String itemName;
    private int itemQuantity;

    @ManyToOne
    private Player player;

}
