package FromHandcraftToIndustrial.content.block.kinetic;

import arc.math.*;
import arc.util.io.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

/**
 * 一个动能生产器
 */
public class KineticProducer extends GenericCrafter{
    /**
     * 该生产器产生的动能
     */
    public float kineticOutput = 10f;
    /**
     * 该生产器的动能产生速率
     */
    public float energyProductionRate = 0.15f;

    public KineticProducer(String name){
        super(name);

        // 该生产器的绘制器
        drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput());
        // 该生产器可以旋转
        rotateDraw = false;
        rotate = true;
        canOverdrive = false;
        drawArrow = true;
    }

    @Override
    public void setStats(){
        super.setStats();

        // 在 stats 窗口中显示该生产器的动能
        stats.add(Stat.output, kineticOutput, StatUnit.heatUnits);
    }

    @Override
    public void setBars(){
        super.setBars();

        // 在 stats 窗口中显示该生产器的动能百分比
        addBar("kinetic", (KineticProducerBuild entity) -> new Bar("bar.kinetic", Pal.lightOrange, () -> entity.kinetic / kineticOutput));
    }

    public class KineticProducerBuild extends GenericCrafterBuild implements KineticBlock{
        // 该生产器当前的动能
        public float kinetic;

        @Override
        public void updateTile(){
            super.updateTile();

            // 该生产器的动能朝目标值 approach，以动能产生速率为速度
            kinetic = Mathf.approachDelta(kinetic, kineticOutput * efficiency, energyProductionRate * delta());
        }

        @Override
        public float kineticFrac(){
            // 该生产器的动能百分比
            return kinetic / kineticOutput;
        }

        @Override
        public float kinetic(){
            // 该生产器当前的动能
            return kinetic;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            // 该生产器的动能
            write.f(kinetic);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            // 该生产器的动能
            kinetic = read.f();
        }
    }
}
