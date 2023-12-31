package com.gameloft.profile.service.mapper;

import com.gameloft.profile.entity.Campaign;
import com.gameloft.profile.entity.InventoryItem;
import com.gameloft.profile.entity.Player;
import com.gameloft.profile.entity.enums.GenderEnum;
import com.gameloft.profile.service.dto.PlayerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = {DeviceMapper.class, ClanMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class PlayerMapper {


    @Mapping(target = "activeCampaigns", source = "player.activeCampaigns", qualifiedByName = "activeCampaignsMapper")
    @Mapping(target = "inventory", source = "player.inventory", qualifiedByName = "inventoryMapper")
    @Mapping(target = "gender", source = "player.gender", qualifiedByName = "genderMapper")
    public abstract PlayerDTO toDTO(Player player);

    @Named("activeCampaignsMapper")
    Set<String> mapActiveCampaigns(Set<Campaign> activeCampaigns) {
        if (Objects.isNull(activeCampaigns)) {
            return Set.of();
        }
        return activeCampaigns.stream().map(Campaign::getName).collect(Collectors.toSet());
    }

    @Named("inventoryMapper")
    Map<String, Long> mapInventory(List<InventoryItem> inventoryItems) {
        if (Objects.isNull(inventoryItems)) {
            return Map.of();
        }
        return inventoryItems.stream().collect(Collectors.toMap(InventoryItem::getName, InventoryItem::getQuantity));
    }


    @Named("genderMapper")
    String mapGender(GenderEnum gender) {
        return Objects.isNull(gender) ? null : gender.getName();
    }

}
