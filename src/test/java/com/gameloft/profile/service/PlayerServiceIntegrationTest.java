package com.gameloft.profile.service;
import com.gameloft.profile.entity.Player;
import com.gameloft.profile.exception.PlayerNotFoundException;
import com.gameloft.profile.repository.PlayerRepository;
import com.gameloft.profile.service.dto.PlayerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PlayerServiceIntegrationTest {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testFindPlayerById() {
        Player player = new Player();
        player.setPlayerId(UUID.randomUUID());
        player.setCredential("test_player");
        playerRepository.save(player);

        PlayerDTO playerDTO = playerService.findByPlayerId(player.getPlayerId());

        assertEquals(player.getCredential(), playerDTO.getCredential());
    }
    @Test
    public void testFindPlayerById_ThrowsException() {
        UUID playerUUid = UUID.randomUUID();

        assertThrows(PlayerNotFoundException.class,
                () -> playerService.findByPlayerId(playerUUid));
    }
}
