package FHTI.content.block;

import static mindustry.type.ItemStack.with;

import FHTI.content.block.core.coreBlocks;
import FHTI.content.block.wearable.wearableBlocks.defense.Wall;
import FHTI.content.block.wearable.wearableBlocks.kinetic.KineticProducer;
import FHTI.content.block.wearable.wearableBlocks.production.GenericCrafter;
import FHTI.content.item.ModItems;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawHeatOutput;
import mindustry.world.draw.DrawMulti;
import mindustry.world.meta.BuildVisibility;

public class ModBlocks {
    public static KineticProducer kinetic_source;
    public static Wall wood_wall;
    public static GenericCrafter log_cutter;
    public static GenericCrafter plank_cutter;

    public static void load() {
        coreBlocks.load();
        kinetic_source = new KineticProducer("kinetic-source") {
            {
                requirements(Category.crafting, BuildVisibility.sandboxOnly, ItemStack.with());
                drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput());
                rotateDraw = false;
                size = 1;
                kineticOutput = 1000f;
                energyProductionRate = 1000f;
                regionRotated1 = 1;
                ambientSound = Sounds.none;
                serviceLife = 114514;
            }
        };
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
                outputItems = with(ModItems.wood_block, 6, ModItems.wood_chip, 30);
                craftTime = 350f;
                size = 4;
                hasItems = true;
                consumeItem(ModItems.log, 2);
                serviceLife = 300;
            }
        };
        plank_cutter = new GenericCrafter("plank-cutter") {
            {
                requirements(Category.crafting, with(ModItems.log, 5, ModItems.wood_block, 8));

                craftEffect = Fx.pulverizeMedium;
                outputItems = with(ModItems.wood_plank, 4, ModItems.wood_chip, 10);
                craftTime = 200f;
                size = 4;
                hasItems = true;
                consumeItem(ModItems.wood_block, 1);
                serviceLife = 200;
            }
        };
        // Events.run(EventType.Trigger.update, () -> {
        // if (Vars.state.isPlaying() && Vars.state.isGame()) {
        // }
        // });
    }
}
