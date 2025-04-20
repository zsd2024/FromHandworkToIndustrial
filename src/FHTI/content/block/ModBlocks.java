package FHTI.content.block;

import static mindustry.type.ItemStack.with;

import FHTI.content.ModUnits;
import FHTI.content.block.wearable.wearableBlocks.defense.Wall;
import FHTI.content.block.wearable.wearableBlocks.kinetic.KineticProducer;
import FHTI.content.block.wearable.wearableBlocks.production.KineticCrafter;
import FHTI.content.block.wearable.wearableBlocks.storage.CoreBlock;
import FHTI.content.item.ModItems;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawHeatOutput;
import mindustry.world.draw.DrawMulti;
import mindustry.world.meta.BuildVisibility;

public class ModBlocks {

    public static Block log_wall, wooden_wall, // 墙
            log_cutter, plank_cutter, // 工厂
            kinetic_source, // 动能
            core_primitive; // 核心

    public static void load() {
        // region 墙
        log_wall = new Wall("log-wall") {
            {
                requirements(Category.defense, BuildVisibility.shown, with(ModItems.log, 10));
                health = 100;
                size = 1;
                buildCostMultiplier = 2f;
                serviceLife = 600;
            }
        };
        wooden_wall = new Wall("wooden-wall") {
            {
                requirements(Category.defense, BuildVisibility.shown, with(ModItems.wood_block, 10));
                health = 175;
                size = 1;
                buildCostMultiplier = 1.5f;
                serviceLife = 800;
            }
        };
        // region 工厂
        log_cutter = new KineticCrafter("log-cutter") {
            {
                requirements(Category.crafting, with(ModItems.log, 10));

                craftEffect = Fx.pulverizeMedium;
                outputItems = with(ModItems.wood_block, 6, ModItems.wood_chip, 30);
                itemCapacity = 30;
                craftTime = 350f;
                size = 2;
                hasItems = true;
                consumeItem(ModItems.log, 2);
                serviceLife = 300;

                kineticRequirement = 20f;
                overkineticScale = 0.5f;
                maxEfficiency = 2f;
            }
        };
        plank_cutter = new KineticCrafter("plank-cutter") {
            {
                requirements(Category.crafting, with(ModItems.log, 5, ModItems.wood_block, 8));

                craftEffect = Fx.pulverizeMedium;
                outputItems = with(ModItems.wood_plank, 4, ModItems.wood_chip, 10);
                itemCapacity = 15;
                craftTime = 200f;
                size = 4;
                hasItems = true;
                consumeItem(ModItems.wood_block, 1);
                serviceLife = 200;

                kineticRequirement = 2f;
                overkineticScale = 0.5f;
                maxEfficiency = 2f;
            }
        };
        // region 动能
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
        // region 核心
        core_primitive = new CoreBlock("core-primitive") {
            {
                requirements(Category.effect, new ItemStack[] { new ItemStack(ModItems.log, 100) });
                alwaysUnlocked = true;
                isFirstTier = true;
                unitType = ModUnits.primitive_silicon_based_life;
                health = 300;
                itemCapacity = 300;
                size = 2;
                unitCapModifier = 4;
                serviceLife = 900;
            }
        };
        // Events.run(EventType.Trigger.update, () -> {
        // if (Vars.state.isPlaying() && Vars.state.isGame()) {
        // }
        // });
    }
}
