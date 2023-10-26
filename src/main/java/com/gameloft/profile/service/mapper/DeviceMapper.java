package com.gameloft.profile.service.mapper;


import com.gameloft.profile.entity.Device;
import com.gameloft.profile.service.dto.DeviceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DeviceMapper {
    DeviceDTO toDTO(Device device);
}
