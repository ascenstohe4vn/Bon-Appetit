package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.BonAppetit;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = BonAppetit.ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onFoodBar(RenderGuiLayerEvent.Pre event) {
        if (!BAConfig.HUNGER_BAR_ENABLED.get() && event.getName().equals(VanillaGuiLayers.FOOD_LEVEL)) {
            event.setCanceled(true);
        }
    }
}