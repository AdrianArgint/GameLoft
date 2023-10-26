package com.gameloft.profile.service;

import com.gameloft.profile.entity.Campaign;
import com.gameloft.profile.entity.Player;
import com.gameloft.profile.exception.PlayerNotFoundException;
import com.gameloft.profile.repository.CampaignRepository;
import com.gameloft.profile.repository.PlayerRepository;
import com.gameloft.profile.service.dto.CampaignDTO;
import com.gameloft.profile.service.dto.PlayerDTO;
import com.gameloft.profile.service.mapper.CampaignMapper;
import com.gameloft.profile.service.mapper.PlayerMapper;
import com.gameloft.profile.util.PlayerMatcher;
import com.gameloft.profile.util.PlayerMatcherFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class PlayerService {
    private PlayerRepository playerRepository;
    private PlayerMapper playerMapper;
    private ActiveCampaignService activeCampaignService;
    private CampaignMapper campaignMapper;
    private CampaignRepository campaignRepository;

    public PlayerDTO findByPlayerId(UUID playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(String.format("Player with id %s not found.", playerId)));

        CampaignDTO campaignDTO = activeCampaignService.getActiveCampaign();

        if (campaignDTO.getEnabled() && doesPlayerMatch(player, campaignDTO.getMatchers())) {
            Campaign campaign = campaignRepository.findByName(campaignDTO.getName())
                    .orElseGet(() -> {
                        var campaignObject = campaignMapper.toEntity(campaignDTO);
                        return campaignRepository.save(campaignObject);
                    });

            player.addActiveCampaign(campaign);
            this.playerRepository.save(player);
        }

        return playerMapper.toDTO(player);
    }

    private boolean doesPlayerMatch(Player player, Map<String, Map<String, Object>> matchers) {
        for (Map.Entry<String, Map<String, Object>> entry : matchers.entrySet()) {
            String criterion = entry.getKey();
            Map<String, Object> params = entry.getValue();
            PlayerMatcher matcher = PlayerMatcherFactory.createMatcher(criterion, params);

            if (!matcher.matches(player)) {
                return false;
            }
        }
        return true;
    }
}
