package com.gameloft.profile.controller;

import com.gameloft.profile.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PlayerController {
    private PlayerService playerService;
}
