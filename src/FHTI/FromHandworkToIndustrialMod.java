package FHTI;

import FHTI.content.block.ModBlocks;
import FHTI.content.item.ModItems;
import FHTI.others.ChangeModName;
import FHTI.content.ModPlanets;
import FHTI.content.ModUnits;
import FHTI.content.ZetasTechTree;
import arc.util.Log;
import mindustry.mod.Mod;

public class FromHandworkToIndustrialMod extends Mod {
    public FromHandworkToIndustrialMod() {
    }

    @Override
    public void loadContent() {
        Log.info("Loading content...");
        Log.info("Loading class ChangeModName...");
        ChangeModName.load();
        Log.info("Loading class ChangeModName done.");
        Log.info("Loading class ModUnits...");
        ModUnits.load();
        Log.info("Loading class ModUnits done.");
        Log.info("Loading class ModItems...");
        ModItems.load();
        Log.info("Loading class ModItems done.");
        Log.info("Loading class ModBlocks...");
        ModBlocks.load();
        Log.info("Loading class ModBlocks done.");
        Log.info("Loading class ModPlanets...");
        ModPlanets.load();
        Log.info("Loading class ModPlanets done.");
        Log.info("Loading class ZetasTechTree...");
        ZetasTechTree.load();
        Log.info("Loading class ZetasTechTree done.");
        Log.info("Loading content done.");
    }
}
