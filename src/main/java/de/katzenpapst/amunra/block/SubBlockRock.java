package de.katzenpapst.amunra.block;

import java.util.Random;

import net.minecraft.item.Item;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

/**
 * Subblock which drops some kind of cobble when harvested
 */
public class SubBlockRock extends SubBlock {

    BlockMetaPair blockToDrop = null;

    public SubBlockRock(final String name, final String texture) {
        super(name, texture);
    }

    public SubBlockRock(final String name, final String texture, final String tool, final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
    }

    public SubBlockRock(final String name, final String texture, final String tool, final int harvestLevel,
            final float hardness, final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
    }

    public SubBlockRock setBlockToDrop(final BlockMetaPair block) {
        this.blockToDrop = block;
        return this;
    }

    @Override
    public boolean dropsSelf() {
        return false;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Item.getItemFromBlock(this.blockToDrop.getBlock());
    }

    @Override
    public int damageDropped(int meta) {
        return this.blockToDrop.getMetadata();
    }

}
