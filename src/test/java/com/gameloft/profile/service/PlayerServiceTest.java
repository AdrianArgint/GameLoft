package com.gameloft.profile.service;

import com.gameloft.profile.entity.Campaign;
import com.gameloft.profile.entity.InventoryItem;
import com.gameloft.profile.entity.Player;
import com.gameloft.profile.exception.PlayerNotFoundException;
import com.gameloft.profile.repository.CampaignRepository;
import com.gameloft.profile.repository.PlayerRepository;
import com.gameloft.profile.service.dto.CampaignDTO;
import com.gameloft.profile.service.dto.PlayerDTO;
import com.gameloft.profile.service.mapper.CampaignMapper;
import com.gameloft.profile.service.mapper.PlayerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private CampaignMapper campaignMapper;
    @Mock
    private PlayerMapper playerMapper;

    @Mock
    private ActiveCampaignService activeCampaignService;

    private Player createMockPlayer(UUID playerId) {
        return Player.builder()
                .playerId(playerId)
                .level(2L)
                .activeCampaigns(new HashSet<>())
                .build();
    }

    private CampaignDTO createMockCampaignDTO(boolean enabled, Map<String, Map<String, Object>> matchers) {
        return CampaignDTO.builder()
                .name("test-campaign")
                .game("test-game")
                .matchers(matchers)
                .enabled(enabled)
                .build();
    }

    private Campaign createMockCampaign() {
        return Campaign.builder()
                .name("test-campaign")
                .build();
    }

    @Test
    public void testFindByPlayerId_PlayerFoundAndCampaignEnabled() {
        UUID playerId = UUID.randomUUID();
        Player player = createMockPlayer(playerId);
        CampaignDTO campaignDTO = createMockCampaignDTO(true, Map.of());

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        when(activeCampaignService.getActiveCampaign()).thenReturn(campaignDTO);

        when(campaignRepository.findByName(campaignDTO.getName())).thenReturn(Optional.empty());
        when(campaignMapper.toEntity(campaignDTO)).thenReturn(createMockCampaign());
        when(campaignRepository.save(any(Campaign.class))).thenAnswer(invocation -> {
            Campaign campaign = invocation.getArgument(0);
            campaign.setId(1L);
            return campaign;
        });
        when(playerMapper.toDTO(any(Player.class))).thenAnswer(invocation -> {
            Player playerArgument = invocation.getArgument(0);
            return PlayerDTO.builder()
                    .playerId(playerArgument.getPlayerId())
                    .level(playerArgument.getLevel())
                    .activeCampaigns(playerArgument.getActiveCampaigns().stream().map(Campaign::getName).collect(Collectors.toSet()))
                    .build();
        });
        PlayerDTO result = playerService.findByPlayerId(playerId);

        assertNotNull(result);
        checkPlayerAndPlayerDTOAreSimilar(player, result);
        assertEquals(Set.of(campaignDTO.getName()), result.getActiveCampaigns());

    }

    @Test
    public void testFindByPlayerId_PlayerFoundAndCampaignDisabled() {
        // Arrange
        UUID playerId = UUID.randomUUID();
        Player player = createMockPlayer(playerId);
        CampaignDTO campaignDTO = createMockCampaignDTO(false, null);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(activeCampaignService.getActiveCampaign()).thenReturn(campaignDTO);
        when(playerMapper.toDTO(any(Player.class))).thenAnswer(invocation -> {
            Player playerArgument = invocation.getArgument(0);
            return PlayerDTO.builder()
                    .playerId(playerArgument.getPlayerId())
                    .level(playerArgument.getLevel())
                    .activeCampaigns(playerArgument.getActiveCampaigns().stream().map(Campaign::getName).collect(Collectors.toSet()))
                    .build();
        });
        // Act
        PlayerDTO result = playerService.findByPlayerId(playerId);

        assertNotNull(result);
        checkPlayerAndPlayerDTOAreSimilar(player, result);
        assertEquals(0, result.getActiveCampaigns().size());
    }

    @Test
    public void testFindByPlayerId_PlayerFoundAndCampaignMatchersNull() {
        UUID playerId = UUID.randomUUID();
        Player player = createMockPlayer(playerId);
        CampaignDTO campaignDTO = createMockCampaignDTO(true, null);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        when(activeCampaignService.getActiveCampaign()).thenReturn(campaignDTO);

        when(campaignRepository.findByName(campaignDTO.getName())).thenReturn(Optional.empty());
        when(campaignMapper.toEntity(campaignDTO)).thenReturn(createMockCampaign());
        when(campaignRepository.save(any(Campaign.class))).thenAnswer(invocation -> {
            Campaign campaign = invocation.getArgument(0);
            campaign.setId(1L);
            return campaign;
        });
        when(playerMapper.toDTO(any(Player.class))).thenAnswer(invocation -> {
            Player playerArgument = invocation.getArgument(0);
            return PlayerDTO.builder()
                    .playerId(playerArgument.getPlayerId())
                    .level(playerArgument.getLevel())
                    .activeCampaigns(playerArgument.getActiveCampaigns().stream().map(Campaign::getName).collect(Collectors.toSet()))
                    .build();
        });
        PlayerDTO result = playerService.findByPlayerId(playerId);

        assertNotNull(result);
        checkPlayerAndPlayerDTOAreSimilar(player, result);
        assertEquals(Set.of(campaignDTO.getName()), result.getActiveCampaigns());
    }

    @Test
    public void testFindByPlayerId_PlayerNotFound() {
        // Arrange
        UUID playerId = UUID.randomUUID();

        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(PlayerNotFoundException.class, () -> {
            playerService.findByPlayerId(playerId);
        });
    }

    @Test
    public void testFindByPlayerId_ActiveCampaignIsNull() {
        // Arrange
        UUID playerId = UUID.randomUUID();
        Player player = createMockPlayer(playerId);

        when(playerRepository.findById(playerId)).thenReturn(Optional.ofNullable(player));
        when(activeCampaignService.getActiveCampaign()).thenReturn(null);
        when(playerMapper.toDTO(any(Player.class))).thenAnswer(invocation -> {
            Player playerArgument = invocation.getArgument(0);
            return PlayerDTO.builder()
                    .playerId(playerArgument.getPlayerId())
                    .level(playerArgument.getLevel())
                    .activeCampaigns(playerArgument.getActiveCampaigns().stream().map(Campaign::getName).collect(Collectors.toSet()))
                    .build();
        });

        PlayerDTO result = playerService.findByPlayerId(playerId);

        assertNotNull(result);
        checkPlayerAndPlayerDTOAreSimilar(player, result);
        assertEquals(0, result.getActiveCampaigns().size());
    }

    @Test
    public void testFindByPlayerId_CampaignWithSameNameExists() {
        // Arrange
        UUID playerId = UUID.randomUUID();
        Player player = createMockPlayer(playerId);
        CampaignDTO campaignDTO = createMockCampaignDTO(true, null);
        Campaign existingCampaign = createMockCampaign();
        existingCampaign.setId(1L);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(activeCampaignService.getActiveCampaign()).thenReturn(campaignDTO);
        when(campaignRepository.findByName(campaignDTO.getName())).thenReturn(Optional.of(existingCampaign));
        when(playerMapper.toDTO(any(Player.class))).thenAnswer(invocation -> {
            Player playerArgument = invocation.getArgument(0);
            return PlayerDTO.builder()
                    .playerId(playerArgument.getPlayerId())
                    .level(playerArgument.getLevel())
                    .activeCampaigns(playerArgument.getActiveCampaigns().stream().map(Campaign::getName).collect(Collectors.toSet()))
                    .build();
        });
        // Act
        PlayerDTO result = playerService.findByPlayerId(playerId);

        assertNotNull(result);
        checkPlayerAndPlayerDTOAreSimilar(player, result);
        assertEquals(1, result.getActiveCampaigns().size());
        assertEquals(Set.of(campaignDTO.getName()), result.getActiveCampaigns());
    }


    @Test
    public void testFindByPlayerId_MatcherWithLevelPass() {
        UUID playerId = UUID.randomUUID();
        Player player = createMockPlayer(playerId);
        CampaignDTO campaignDTO = createMockCampaignDTO(true, Map.of("level", Map.of("min", 1, "max", 3)));

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        when(activeCampaignService.getActiveCampaign()).thenReturn(campaignDTO);

        when(campaignRepository.findByName(campaignDTO.getName())).thenReturn(Optional.empty());
        when(campaignMapper.toEntity(campaignDTO)).thenReturn(createMockCampaign());
        when(campaignRepository.save(any(Campaign.class))).thenAnswer(invocation -> {
            Campaign campaign = invocation.getArgument(0);
            campaign.setId(1L);
            return campaign;
        });
        when(playerMapper.toDTO(any(Player.class))).thenAnswer(invocation -> {
            Player playerArgument = invocation.getArgument(0);
            return PlayerDTO.builder()
                    .playerId(playerArgument.getPlayerId())
                    .level(playerArgument.getLevel())
                    .activeCampaigns(playerArgument.getActiveCampaigns().stream().map(Campaign::getName).collect(Collectors.toSet()))
                    .build();
        });
        PlayerDTO result = playerService.findByPlayerId(playerId);

        assertNotNull(result);
        checkPlayerAndPlayerDTOAreSimilar(player, result);
        assertEquals(1, result.getActiveCampaigns().size());
        assertEquals(Set.of(campaignDTO.getName()), result.getActiveCampaigns());
    }

    @Test
    public void testFindByPlayerId_MatcherWithLevelFails() {
        UUID playerId = UUID.randomUUID();
        Player player = createMockPlayer(playerId);
        CampaignDTO campaignDTO = createMockCampaignDTO(true, Map.of("level", Map.of("min", 5L, "max", 10L)));

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        when(activeCampaignService.getActiveCampaign()).thenReturn(campaignDTO);

        when(playerMapper.toDTO(any(Player.class))).thenAnswer(invocation -> {
            Player playerArgument = invocation.getArgument(0);
            return PlayerDTO.builder()
                    .playerId(playerArgument.getPlayerId())
                    .level(playerArgument.getLevel())
                    .activeCampaigns(playerArgument.getActiveCampaigns().stream().map(Campaign::getName).collect(Collectors.toSet()))
                    .build();
        });
        PlayerDTO result = playerService.findByPlayerId(playerId);

        assertNotNull(result);
        checkPlayerAndPlayerDTOAreSimilar(player, result);
        assertEquals(0, result.getActiveCampaigns().size());
    }


    @Test
    public void testFindByPlayerId_MatcherWithHasItemPass() {
        UUID playerId = UUID.randomUUID();
        Player player = createMockPlayer(playerId);
        player.setInventory(List.of(InventoryItem.builder().name("Inventory Item 1").quantity(4L).build()));
        CampaignDTO campaignDTO = createMockCampaignDTO(true, Map.of("has",
                Map.of( "items", List.of("Inventory Item 1", "Inventory Item 2"))));

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        when(activeCampaignService.getActiveCampaign()).thenReturn(campaignDTO);

        when(campaignRepository.findByName(campaignDTO.getName())).thenReturn(Optional.empty());
        when(campaignMapper.toEntity(campaignDTO)).thenReturn(createMockCampaign());
        when(campaignRepository.save(any(Campaign.class))).thenAnswer(invocation -> {
            Campaign campaign = invocation.getArgument(0);
            campaign.setId(1L);
            return campaign;
        });
        when(playerMapper.toDTO(any(Player.class))).thenAnswer(invocation -> {
            Player playerArgument = invocation.getArgument(0);
            return PlayerDTO.builder()
                    .playerId(playerArgument.getPlayerId())
                    .level(playerArgument.getLevel())
                    .activeCampaigns(playerArgument.getActiveCampaigns().stream().map(Campaign::getName).collect(Collectors.toSet()))
                    .build();
        });
        PlayerDTO result = playerService.findByPlayerId(playerId);

        assertNotNull(result);
        checkPlayerAndPlayerDTOAreSimilar(player, result);
        assertEquals(1, result.getActiveCampaigns().size());
        assertEquals(Set.of(campaignDTO.getName()), result.getActiveCampaigns());
    }

    @Test
    public void testFindByPlayerId_MatcherWithHasItemFails() {
        UUID playerId = UUID.randomUUID();
        Player player = createMockPlayer(playerId);
        player.setInventory(List.of(InventoryItem.builder().name("Inventory Item 5").quantity(4L).build()));
        CampaignDTO campaignDTO = createMockCampaignDTO(true, Map.of("has",
                Map.of( "items", List.of("Inventory Item 1", "Inventory Item 2"))));

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        when(activeCampaignService.getActiveCampaign()).thenReturn(campaignDTO);

        when(playerMapper.toDTO(any(Player.class))).thenAnswer(invocation -> {
            Player playerArgument = invocation.getArgument(0);
            return PlayerDTO.builder()
                    .playerId(playerArgument.getPlayerId())
                    .level(playerArgument.getLevel())
                    .activeCampaigns(playerArgument.getActiveCampaigns().stream().map(Campaign::getName).collect(Collectors.toSet()))
                    .build();
        });
        PlayerDTO result = playerService.findByPlayerId(playerId);

        assertNotNull(result);
        checkPlayerAndPlayerDTOAreSimilar(player, result);
        assertEquals(0, result.getActiveCampaigns().size());
    }


    private void checkPlayerAndPlayerDTOAreSimilar(Player player, PlayerDTO result) {
        assertEquals(player.getPlayerId(), result.getPlayerId());
        assertEquals(player.getLevel(), result.getLevel());
    }

}
