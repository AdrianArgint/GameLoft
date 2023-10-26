package com.gameloft.profile.util;

import com.gameloft.profile.entity.Player;

import java.util.List;
import java.util.Map;

public class RangeMatcher implements PlayerMatcher {
    private Long min;
    private Long max;
    private String criterion;

    public RangeMatcher(String criterion, Map<String, Object> params) {
        this.min = (params.get("min") instanceof Long) ? (Long) params.get("min") : null;
        this.max = (params.get("max") instanceof Long) ? (Long) params.get("max") : null;
        this.criterion = criterion;
    }

    @Override
    public boolean matches(Player player) {
        List<String> playerValues = player.getValuesForKey(this.criterion);
        if (playerValues.size() == 0) {
            return false;
        }

        Double playerValue = Double.valueOf(playerValues.get(0));
        return (this.min == null || playerValue >= this.min) &&
               (this.max == null || playerValue <= this.max);
    }
}
