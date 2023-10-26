package com.gameloft.profile.util;

import java.util.Map;

public class PlayerMatcherFactory {
    public static PlayerMatcher createMatcher(String criterion, Map<String, Object> params) {
        return switch (criterion) {
            case "level", "total_spent", "total_transactions", "total_refund" -> new RangeMatcher(criterion, params);
            case "has" -> new HasMatcher(params);
            case "does_not_have" -> new DoesNotHaveMatcher(params);
            default -> throw new IllegalArgumentException("Unknown criterion: " + criterion);
        };
    }

}
