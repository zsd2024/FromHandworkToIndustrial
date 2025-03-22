package FHTI.content.item;

import arc.graphics.Color;
import mindustry.type.Item;

public class ModItems {
    public static Item log;
    public static Item wood_chip;

    public static void load() {
        log = new Item("log", Color.valueOf("#8B4513")) {
            {
                hardness = 1;// 硬度（越高需要越高级的钻头）
                flammability = 0.3f; // 易燃性
                explosiveness = 0.1f; // 爆炸性
                cost = 1; // 建造时间
                alwaysUnlocked = true; // 始终解锁
            }
        };
        wood_chip = new Item("wood-chip", Color.valueOf("#8B4513")) {
            {
                flammability = 0.5f; // 易燃性
                explosiveness = 0.3f; // 爆炸性
                buildable = false; // 不可作为核心中的物品
            }
        };
    }
}
