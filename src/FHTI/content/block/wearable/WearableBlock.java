package FHTI.content.block.wearable;

import arc.Core;
import arc.graphics.Color;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.Block;

public class WearableBlock extends Block {

    public float wearLevel;

    public WearableBlock(String name) {
        super(name);
        update = true;
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("wearlevel", (WearableBlockBuild entity) -> new Bar(
                () -> Core.bundle.get("stat.from-handcraft-to-industrial-wearlevel"),
                () -> Color.HSVtoRGB((1.0f - WearableBlockBuild.class.cast(entity).wearLevelf()) * 0.5f * 360, 100, 100),
                entity::wearLevelf));
    }

    public class WearableBlockBuild extends Building {
        public float WearLevel;

        public float wearLevelf() {
            return WearLevel / wearLevel;
        }

        @Override
        public void updateTile() {
            super.updateTile();
            WearLevel = Math.min(wearLevel, WearLevel + wearLevel * 0.001f);
        }
    }
}
