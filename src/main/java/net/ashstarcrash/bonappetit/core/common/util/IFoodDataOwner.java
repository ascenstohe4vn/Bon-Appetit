package net.ashstarcrash.bonappetit.core.common.util;

import net.minecraft.world.entity.player.Player;

public interface IFoodDataOwner {
    void bonappetit$setOwner(Player player);
    Player bonappetit$getOwner();
}