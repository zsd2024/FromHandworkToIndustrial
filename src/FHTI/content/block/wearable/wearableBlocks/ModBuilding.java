package FHTI.content.block.wearable.wearableBlocks;

import java.util.Arrays;

import FHTI.content.block.wearable.wearableBlocks.kinetic.KineticBlock;
import FHTI.content.block.wearable.wearableBlocks.kinetic.KineticConductor;
import FHTI.content.block.wearable.wearableBlocks.kinetic.KineticConductor.KineticConductorBuild;
import arc.math.Mathf;
import arc.struct.IntSet;
import mindustry.Vars;
import mindustry.gen.Building;

public class ModBuilding extends Building {
    public float calculateKinetic(float[] sideKinetic) {
        return calculateKinetic(sideKinetic, null);
    }

    public float calculateKinetic(float[] sideKinetic, IntSet cameFrom) {
        Arrays.fill(sideKinetic, 0f);
        if (cameFrom != null)
            cameFrom.clear();

        float kinetic = 0f;

        for (Building build : proximity) {
            if (build != null && build.team == team && build instanceof KineticBlock) {
                KineticBlock kineticer = (KineticBlock) build;
                // boolean split = build.block instanceof KineticConductor cond &&
                // cond.splitKinetic;
                boolean split = build.block instanceof KineticConductor;
                if (split) {
                    KineticConductor cond = (KineticConductor) build.block;
                    split = cond.splitKinetic;
                }
                // 非路由器必须面向我们，路由器必须面向相反 - 在重定向器旁边，它们将被强制面向相反方向
                if (!build.block.rotate || (!split && (relativeTo(build) + 2) % 4 == build.rotation)
                        || (split && relativeTo(build) != build.rotation)) { // TODO hacky

                    // 如果存在环路，忽略其动能
                    if (!(build instanceof KineticConductorBuild
                            && ((KineticConductorBuild) build).cameFrom.contains(id()))) {
                        // x/y 坐标差异在接触点
                        float diff = (Math.min(Math.abs(build.x - x), Math.abs(build.y - y)) / Vars.tilesize);
                        // 该块与其他块的接触点数
                        int contactPoints = Math.min((int) (block.size / 2f + build.block.size / 2f - diff),
                                Math.min(build.block.size, block.size));

                        // 动能被分配到建筑的尺寸
                        float add = kineticer.kinetic() / build.block.size * contactPoints;
                        if (split) {
                            // 动能路由器将动能分配到 3 个表面
                            add /= 3f;
                        }

                        sideKinetic[Mathf.mod(relativeTo(build), 4)] += add;
                        kinetic += add;
                    }

                    // 记录遍历的环路
                    if (cameFrom != null) {
                        cameFrom.add(build.id);
                        if (build instanceof KineticConductorBuild) {
                            cameFrom.addAll(((KineticConductorBuild) build).cameFrom);
                        }
                    }

                    // 一个巨大的 hack 但是 我不在乎了
                    if (build instanceof KineticConductorBuild) {
                        ((KineticConductorBuild) build).updateKinetic();
                    }
                }
            }
        }
        return kinetic;
    }
}
