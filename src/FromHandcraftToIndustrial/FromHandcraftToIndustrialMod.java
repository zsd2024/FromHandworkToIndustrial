package FromHandcraftToIndustrial;

import FromHandcraftToIndustrial.content.block.ModBlocks;
import FromHandcraftToIndustrial.content.item.ModItems;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.mod.Mod;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.meta.BuildVisibility;

public class FromHandcraftToIndustrialMod extends Mod {
    public FromHandcraftToIndustrialMod() {
        Log.info("Loaded FromHandcraftToIndustrialMod constructor.");
    }

    @Override
    public void loadContent() {
        Log.info("Loading some FromHandcraftToIndustrial content.");
        ModItems.load();
        ModBlocks.load();
        Seq<Block> blocks = Vars.content.blocks();
        for (Block block : blocks) {
            if (block instanceof Floor) {
                block.buildVisibility = BuildVisibility.shown;
                block.alwaysUnlocked = true;
            }
        }
    }
}
