package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.content.blockentity.CookingPotMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BAMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, BonAppetit.ID);

    public static final Supplier<MenuType<CookingPotMenu>> COOKING_POT = MENU_TYPES
            .register("cooking_pot", () -> IMenuTypeExtension.create(CookingPotMenu::new));
}