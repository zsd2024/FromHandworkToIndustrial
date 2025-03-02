package FHTI.content.block.wearable.wearableBlocks.kinetic;

import arc.Core;
import arc.graphics.Color;
import arc.math.*;
import arc.util.Log;
import arc.util.io.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

/**
 * 一个动能生产器
 */
public class KineticProducer extends GenericCrafter {

    /**
     * 该可磨损方块的使用寿命
     */
    public float serviceLife;

    /**
     * 该生产器产生的动能
     */
    public float kineticOutput = 10f;
    /**
     * 该生产器的动能产生速率
     */
    public float energyProductionRate = 0.15f;
    /**
     * 动能状态
     */
    public StatUnit kineticUnits;

    public KineticProducer(String name) {
        super(name);

        // 该生产器的绘制器
        drawer = new DrawMulti(new DrawDefault(), new DrawKineticOutput());
        // 该生产器可以旋转
        rotateDraw = false;
        rotate = true;
        canOverdrive = false;
        drawArrow = true;
    }

    @Override
    public void setStats() {
        super.setStats();

        // 在 stats 窗口中显示该生产器的动能
        kineticUnits = new StatUnit("kineticUnits", "[blue]动能[]");
        stats.add(Stat.output, kineticOutput, kineticUnits);
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("wearlevel", (KineticProducerBuild entity) -> new Bar(
                () -> Core.bundle.get("stat.from-handcraft-to-industrial-wearlevel"),
                () -> Color.HSVtoRGB((1.0f - entity.wearLevelf()) * 0.5f * 360, 100,
                        100),
                entity::wearLevelf));
        // 在 stats 窗口中显示该生产器的动能百分比
        addBar("kinetic",
                (KineticProducerBuild entity) -> new Bar("bar.from-handcraft-to-industrial-kinetic", Pal.lightOrange,
                        () -> entity.kinetic / kineticOutput));
    }

    public class KineticProducerBuild extends GenericCrafterBuild implements KineticBlock {

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

        // 该生产器当前的动能
        public float kinetic;

        @Override
        public void updateTile() {
            super.updateTile();

            // 该生产器的动能朝目标值 approach，以动能产生速率为速度
            kinetic = Mathf.approachDelta(kinetic, kineticOutput * efficiency, energyProductionRate * delta());
        }

        @Override
        public float kineticFrac() {
            // 该生产器的动能百分比
            return kinetic / kineticOutput;
        }

        @Override
        public float kinetic() {
            // 该生产器当前的动能
            return kinetic;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            // 该生产器的动能
            write.f(kinetic);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            // 该生产器的动能
            kinetic = read.f();
        }
    }
}
