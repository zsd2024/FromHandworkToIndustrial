package FHTI.content.block.wearable.wearableBlocks.multicraft;

import arc.func.Prov;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;

public class IOEntry {
    public Seq<ItemStack> items = new Seq<>(ItemStack.class);
    public Seq<LiquidStack> fluids = new Seq<>(LiquidStack.class);
    public float power = 0f;
    public float heat = 0f;
    @Nullable
    public Prov<TextureRegion> icon;
    @Nullable
    public Color iconColor;
    public ObjectSet<Item> itemsUnique = new ObjectSet<>();
    public ObjectSet<Liquid> fluidsUnique = new ObjectSet<>();

    public IOEntry(Seq<ItemStack> items, Seq<LiquidStack> fluids) {
        this(items, fluids, 0f, 0f);
    }

    public IOEntry(Seq<ItemStack> items, Seq<LiquidStack> fluids, float power) {
        this(items, fluids, power, 0f);
    }

    public IOEntry(Seq<ItemStack> items, Seq<LiquidStack> fluids, float power, float heat) {
        this.items = items;
        this.fluids = fluids;
        this.power = power;
        this.heat = heat;
    }

    public IOEntry() {
    }

    public void cacheUnique() {
        for (ItemStack item : items) {
            itemsUnique.add(item.item);
        }
        for (LiquidStack fluid : fluids) {
            fluidsUnique.add(fluid.liquid);
        }
    }

    public void shrinkSize() {
        items.shrink();
        fluids.shrink();
    }

    public boolean isEmpty() {
        return items.isEmpty() && fluids.isEmpty() && power <= 0f && heat <= 0f;
    }

    public int maxItemAmount() {
        int max = 0;
        for (ItemStack item : items) {
            max = Math.max(item.amount, max);
        }
        return max;
    }

    public float maxFluidAmount() {
        float max = 0;
        for (LiquidStack fluid : fluids) {
            max = Math.max(fluid.amount, max);
        }
        return max;
    }

    @Override
    public String toString() {
        return "IOEntry{" +
                "items=" + items +
                "fluids=" + fluids +
                "power=" + power +
                "heat=" + heat +
                "}";
    }
}
