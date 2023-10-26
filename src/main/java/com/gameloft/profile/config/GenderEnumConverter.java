package com.gameloft.profile.config;
import com.gameloft.profile.entity.enums.GenderEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderEnumConverter implements AttributeConverter<GenderEnum, String> {

    @Override
    public String convertToDatabaseColumn(GenderEnum gender) {
        return gender.getName();
    }

    @Override
    public GenderEnum convertToEntityAttribute(String externalName) {
        for (GenderEnum gender : GenderEnum.values()) {
            if (gender.getName().equals(externalName)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid externalName: " + externalName);
    }
}
