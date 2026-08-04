package net.ashstarcrash.bonappetit.core.common.util;

import java.util.HashSet;
import java.util.Set;

public class FoodDiscoveryData {
    private final Set<String> eatenFoodIds = new HashSet<>();

    public void markEaten(String itemId) {
        eatenFoodIds.add(itemId);
    }

    public boolean hasEaten(String itemId) {
        return eatenFoodIds.contains(itemId);
    }

    public Set<String> getEatenFoodIds() {
        return eatenFoodIds;
    }
}