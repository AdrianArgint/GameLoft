package com.gameloft.profile.util;

import com.gameloft.profile.entity.Player;

public interface PlayerMatcher {
    boolean matches(Player player);
}
