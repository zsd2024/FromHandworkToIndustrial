package FHTI;

import FHTI.content.block.ModBlocks;
import FHTI.content.item.ModItems;
import FHTI.others.ChangeModName;
import FHTI.content.ModPlanets;
import FHTI.content.ZetasTechTree;
// import arc.util.Log;
import mindustry.mod.Mod;

public class FromHandworkToIndustrialMod extends Mod {
    public FromHandworkToIndustrialMod() {
    }

    @Override
    public void loadContent() {
        ChangeModName.load();
        ModItems.load();
        ModBlocks.load();
        ModPlanets.load();
        ZetasTechTree.load();
    }
}
