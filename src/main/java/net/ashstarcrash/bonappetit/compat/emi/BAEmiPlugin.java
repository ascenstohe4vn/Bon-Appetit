package net.ashstarcrash.bonappetit.compat.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import net.ashstarcrash.bonappetit.compat.ModUtil;

@EmiEntrypoint
public class BAEmiPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        registry.addRecipe(new EmiTisaneRecipe(ModUtil.BA.asResource("tisane")));
    }
}