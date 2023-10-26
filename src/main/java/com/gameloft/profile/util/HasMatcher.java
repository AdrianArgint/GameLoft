package com.gameloft.profile.util;

import com.gameloft.profile.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HasMatcher implements PlayerMatcher {
    private Map<String, List<String>> hasCriteria;

    public HasMatcher(Map<String, Object> params) {
        hasCriteria = new HashMap<>();
        params.forEach((key, value) -> {
            hasCriteria.put(key, (List<String>) value);
        });
    }

    @Override
    public boolean matches(Player player) {
        for (Map.Entry<String, List<String>> entry : hasCriteria.entrySet()) {
            String key = entry.getKey();
            List<String> requiredValues = entry.getValue();
            List<String> playerValues = player.getValuesForKey(key);

            if (Collections.disjoint(requiredValues, playerValues)) {
                return false;
            }
        }

        return true;
    }
}
