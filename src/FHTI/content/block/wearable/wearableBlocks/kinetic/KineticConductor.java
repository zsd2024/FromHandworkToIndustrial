package FHTI.content.block.wearable.wearableBlocks.kinetic;

import FHTI.content.block.wearable.wearableBlocks.ModBuilding;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.struct.IntSet;
import arc.util.Eachable;
// import arc.util.Log;
import mindustry.Vars;
import mindustry.entities.units.BuildPlan;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;

/**
 * 一个动能传导器,这个动能传导器可以将动能从一个方向传递到另一个方向,
 * 但是动能传导器自身不能产生动能,也不能处理动能。
 */
public class KineticConductor extends Block {

    /**
     * 该可磨损方块的使用寿命
     */
    public float serviceLife;

    /**
     * 该动能传导器能承载的动能上限,这个值用于在bar上显示动能的
     * progress,如果动能传导器的动能超过了这个值,那么bar上显示的
     * progress将会溢出
     */
    public float visualMaxKinetic = 15f;
    public DrawBlock drawer = new DrawDefault();
    /**
     * 该动能传导器是否可以对动能进行分配,
     * 如果该动能传导器可以对动能进行分配,那么它将会将动能分配
     * 到它的四个方向,否则,它将会将动能传递到它的某一个方向
     */
    public boolean splitKinetic = false;

    public KineticConductor(String name) {
        super(name);
        update = solid = rotate = true;
        rotateDraw = false;
        size = 3;
    }

    /**
     * 在bar上显示动能的progress
     */
    @Override
    public void setBars() {
        super.setBars();

        addBar("wearlevel", (KineticConductorBuild entity) -> new Bar(
                () -> Core.bundle.get("stat.from-handcraft-to-industrial-wearlevel"),
                () -> Color.HSVtoRGB((1.0f - entity.wearLevelf()) * 0.5f * 360, 100,
                        100),
                entity::wearLevelf));

        // TODO show number
        addBar("kinetic",
                (KineticConductorBuild entity) -> new Bar(
                        () -> Core.bundle.format("bar.from-handcraft-to-industrial-kineticamount",
                                (int) (entity.kinetic + 0.001f)),
                        () -> Pal.lightOrange, () -> entity.kinetic / visualMaxKinetic));
    }

    /**
     * 加载动能传导器的图像
     */
    @Override
    public void load() {
        super.load();

        drawer.load(this);
    }

    /**
     * 在计划中显示动能传导器
     */
    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawer.drawPlan(this, plan, list);
    }

    /**
     * 获取动能传导器的图像
     */
    @Override
    public TextureRegion[] icons() {
        return drawer.finalIcons(this);
    }

    /**
     * 该动能传导器的实现
     */
    public class KineticConductorBuild extends ModBuilding implements KineticBlock, KineticConsumer {

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
                // Log.info("lastUpdate: " + lastUpdate);
                serviceTime += (System.nanoTime() - lastUpdate) * 1e-9 * getUsageIncrementPerSecond();

                lastUpdate = System.nanoTime();
            }
            // Log.info("Current serviceTime: " + serviceTime);
            if (serviceTime >= serviceLife) {
                kill();
            }
        }

        /// WearableBlockBuild Code End

        /**
         * 该动能传导器当前的动能
         */
        public float kinetic = 0f;
        /**
         * 该动能传导器的四个方向的动能
         */
        public float[] sideKinetic = new float[4];
        /**
         * 该动能传导器的动能来源
         */
        public IntSet cameFrom = new IntSet();
        /**
         * 该动能传导器的最后一次动能更新的updateId
         */
        public long lastKineticUpdate = -1;

        /**
         * 在世界中显示动能传导器
         */
        @Override
        public void draw() {
            drawer.draw(this);
        }

        /**
         * 在世界中显示动能传导器的光
         */
        @Override
        public void drawLight() {
            super.drawLight();
            drawer.drawLight(this);
        }

        /**
         * 获取动能传导器的四个方向的动能
         */
        @Override
        public float[] sideKinetic() {
            return sideKinetic;
        }

        /**
         * 获取动能传导器的动能需求
         */
        @Override
        public float kineticRequirement() {
            return visualMaxKinetic;
        }

        /**
         * 在每个tick中更新动能传导器的动能
         */
        @Override
        public void updateTile() {
            super.updateTile();
            updateServiceTime();
            updateKinetic();
        }

        /**
         * 更新动能传导器的动能
         */
        public void updateKinetic() {
            if (lastKineticUpdate == Vars.state.updateId)
                return;

            lastKineticUpdate = Vars.state.updateId;
            kinetic = calculateKinetic(sideKinetic, cameFrom);
        }

        /**
         * 获取动能传导器的动能
         */
        @Override
        public float warmup() {
            return kinetic;
        }

        /**
         * 获取动能传导器的动能
         */
        @Override
        public float kinetic() {
            return kinetic;
        }

        /**
         * 获取动能传导器的动能百分比
         */
        @Override
        public float kineticFrac() {
            return (kinetic / visualMaxKinetic) / (splitKinetic ? 3f : 1);
        }
    }
}
