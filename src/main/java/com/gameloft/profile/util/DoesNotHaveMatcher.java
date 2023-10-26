package com.gameloft.profile.util;

import com.gameloft.profile.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoesNotHaveMatcher implements PlayerMatcher {
    private Map<String, List<String>> doesNotHaveCriteria;

    public DoesNotHaveMatcher(Map<String, Object> params) {
        this.doesNotHaveCriteria = new HashMap<>();
        params.forEach((key, value) -> {
            if (value instanceof List) {
                doesNotHaveCriteria.put(key, (List<String>) value);
            } else {
                throw new IllegalArgumentException("Invalid value type for key: " + key);
            }
        });
    }

    @Override
    public boolean matches(Player player) {
        for (Map.Entry<String, List<String>> entry : doesNotHaveCriteria.entrySet()) {
            String key = entry.getKey();
            List<String> itemsToExclude = entry.getValue();
            List<String> playerItems = player.getValuesForKey(key);

            for (String item : itemsToExclude) {
                if (playerItems.contains(item)) {
                    return false;
                }
            }
        }

        return true;
    }
}
