package FromHandcraftToIndustrial;

import FromHandcraftToIndustrial.content.block.ModBlocks;
import FromHandcraftToIndustrial.content.item.ModItems;
import arc.util.Log;
import mindustry.mod.Mod;

public class FromHandcraftToIndustrialMod extends Mod {
    public FromHandcraftToIndustrialMod() {
        Log.info("Loaded FromHandcraftToIndustrialMod constructor.");
    }

    @Override
    public void loadContent() {
        Log.info("Loading some FromHandcraftToIndustrial content.");
        ModItems.load();
        ModBlocks.load();
    }
}
