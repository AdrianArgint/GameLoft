package com.gameloft.profile.service.mapper;


import com.gameloft.profile.entity.Campaign;
import com.gameloft.profile.repository.CampaignRepository;
import com.gameloft.profile.service.dto.CampaignDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CampaignMapper {
     Campaign toEntity(CampaignDTO device);

}
