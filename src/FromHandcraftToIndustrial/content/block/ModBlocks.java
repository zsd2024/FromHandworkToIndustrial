package FromHandcraftToIndustrial.content.block;

import FromHandcraftToIndustrial.content.block.core.corePrimitive;
import FromHandcraftToIndustrial.content.item.ModItems;
import mindustry.type.Category;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.*;

public class ModBlocks {
    public static Wall wood_wall;

    public static void load() {
        corePrimitive.load();
        wood_wall = new Wall("wood-wall") {
            {
                requirements(Category.defense, BuildVisibility.shown, with(ModItems.wood, 10));
                health = 100;
                size = 1;
                buildCostMultiplier = 2f;
            }
        };
    }
}
