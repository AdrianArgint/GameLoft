package com.gameloft.profile.service.mapper;


import com.gameloft.profile.entity.Clan;
import com.gameloft.profile.service.dto.ClanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClanMapper {
    ClanDTO toDTO(Clan clan);
}
