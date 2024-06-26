package de.katzenpapst.amunra.block.ore;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import de.katzenpapst.amunra.block.SubBlockDropItem;

public class SubBlockOre extends SubBlockDropItem {

    protected String[] oredictNames = {};

    protected ItemStack smeltItem = null;

    public SubBlockOre setOredictNames(final String... newNames) {
        this.oredictNames = newNames;
        return this;
    }

    public String[] getOredictNames() {
        return this.oredictNames;
    }

    public ItemStack getSmeltItem() {
        return this.smeltItem;
    }

    public SubBlockOre setSmeltItem(final Item item, final int num, final int metadata) {
        this.smeltItem = new ItemStack(item, num, metadata);
        return this;
    }

    public SubBlockOre setSmeltItem(final Item item, final int num) {
        this.smeltItem = new ItemStack(item, num, 0);
        return this;
    }

    public SubBlockOre setSmeltItem(final ItemStack stack) {
        this.smeltItem = stack;
        return this;
    }

    public SubBlockOre(final String name, final String texture) {
        super(name, texture);
        this.isValuable = true;
    }

    public SubBlockOre(final String name, final String texture, final String tool, final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
        this.isValuable = true;
    }

    public SubBlockOre(final String name, final String texture, final String tool, final int harvestLevel,
            final float hardness, final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
        this.isValuable = true;
    }

}
