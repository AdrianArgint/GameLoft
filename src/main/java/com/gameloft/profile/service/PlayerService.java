package com.gameloft.profile.service;

import com.gameloft.profile.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PlayerService {
    private PlayerRepository playerRepository;
}
