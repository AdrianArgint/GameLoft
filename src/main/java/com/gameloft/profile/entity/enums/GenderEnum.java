package com.gameloft.profile.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum GenderEnum {
    MALE("male"), FEMALE("female"), OTHER("other");

    @Getter
    private final String externalName;
}
