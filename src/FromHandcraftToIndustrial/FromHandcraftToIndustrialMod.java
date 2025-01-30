package FromHandcraftToIndustrial;

import FromHandcraftToIndustrial.content.block.ModBlocks;
import FromHandcraftToIndustrial.content.item.ModItems;
import FromHandcraftToIndustrial.others.ChangeModName;
import arc.util.Log;
import mindustry.mod.Mod;

public class FromHandcraftToIndustrialMod extends Mod {
    public FromHandcraftToIndustrialMod() {
        Log.info("Loaded FromHandcraftToIndustrialMod constructor.");
    }

    @Override
    public void loadContent() {
        Log.info("Loading some FromHandcraftToIndustrial content.");
        ChangeModName.load();
        ModItems.load();
        ModBlocks.load();
    }
}
