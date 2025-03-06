package FHTI.content.block;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.*;

import FHTI.content.block.core.coreBlocks;
import FHTI.content.block.wearable.wearableBlocks.kineticBlocks;
import FHTI.content.block.wearable.wearableBlocks.defense.Wall;
import FHTI.content.block.wearable.WearableBlock;
import FHTI.content.item.ModItems;

public class ModBlocks {
    public static Wall wood_wall;
    public static GenericCrafter log_cutter;
    public static WearableBlock wearable_block;

    public static void load() {
        coreBlocks.load();
        kineticBlocks.load();
        wood_wall = new Wall("log-wall") {
            {
                requirements(Category.defense, BuildVisibility.shown, with(ModItems.log, 10));
                health = 100;
                size = 1;
                buildCostMultiplier = 2f;
                serviceLife = 600;
            }
        };
        log_cutter = new GenericCrafter("log-cutter") {
            {
                requirements(Category.crafting, with(ModItems.log, 10));

                craftEffect = Fx.pulverizeMedium;
                outputItem = new ItemStack(Items.graphite, 1);
                craftTime = 90f;
                size = 4;
                hasItems = true;
                consumeItem(ModItems.log, 2);
            }
        };
    }
}
