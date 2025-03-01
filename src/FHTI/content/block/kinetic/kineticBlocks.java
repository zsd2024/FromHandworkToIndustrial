package FHTI.content.block.kinetic;

import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawHeatOutput;
import mindustry.world.draw.DrawMulti;
import mindustry.world.meta.BuildVisibility;

public class kineticBlocks {
    public static KineticProducer kinetic_source;

    public static void load() {
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
            }
        };
    }
}
