package com.gameloft.profile.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceDTO {
    private Long id;
    private String model;
    private String carrier;
    private String firmware;
}
