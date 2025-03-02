package FHTI.content.block.wearable;

import arc.Core;
import arc.graphics.Color;
import arc.util.Log;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.Block;

public class WearableBlock extends Block {

    /**
     * 该可磨损方块的使用寿命
     */
    public float serviceLife;

    public WearableBlock(String name) {
        super(name);
        update = true;
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("wearlevel", (WearableBlockBuild entity) -> new Bar(
                () -> Core.bundle.get("stat.from-handcraft-to-industrial-wearlevel"),
                () -> Color.HSVtoRGB((1.0f - entity.wearLevelf()) * 0.5f * 360, 100,
                        100),
                entity::wearLevelf));
    }

    public class WearableBlockBuild extends Building {

        /// WearableBlockBuild Code Start

        /**
         * 该可磨损方块的使用时间
         */
        public double serviceTime;

        /**
         * 上次更新时间
         */
        private long lastUpdate = -1;

        /**
         * 该可磨损方块的磨损百分比
         *
         * @return 磨损百分比
         */
        public float wearLevelf() {
            return (float) (serviceTime / serviceLife);
        }

        /**
         * 获取基础使用寿命增量
         *
         * @return 基础使用寿命增量
         */
        public float getUsageIncrementBesic() {
            return 1.0f;
        }

        /**
         * 获取每秒使用寿命增量
         *
         * @return 每秒使用寿命增量
         */
        public float getUsageIncrementPerSecond() {
            return getUsageIncrementBesic() / healthf();
        }

        /**
         * 更新使用寿命
         */
        public void updateServiceTime() {
            if (lastUpdate == -1) {
                lastUpdate = System.nanoTime();
            } else {
                Log.info("lastUpdate: " + lastUpdate);
                serviceTime += (System.nanoTime() - lastUpdate) * 1e-9 * getUsageIncrementPerSecond();

                lastUpdate = System.nanoTime();
            }
            Log.info("Current serviceTime: " + serviceTime);
            if (serviceTime >= serviceLife) {
                kill();
            }
        }

        /// WearableBlockBuild Code End

        @Override
        public void updateTile() {
            super.updateTile();
            updateServiceTime();
        }
    }
}
