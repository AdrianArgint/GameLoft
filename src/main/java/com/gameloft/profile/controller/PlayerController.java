package com.gameloft.profile.controller;

import com.gameloft.profile.service.PlayerService;
import com.gameloft.profile.service.dto.PlayerDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/get_client_config")
@AllArgsConstructor
public class PlayerController {
    private PlayerService playerService;

    @GetMapping("/{player_id}")
    public PlayerDTO findAllPlayers(@PathVariable(name = "player_id") UUID playerId){
        return playerService.findByPlayerId(playerId);
    }
}
