package de.katzenpapst.amunra.block;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.world.World;

import de.katzenpapst.amunra.item.ARItems;

public class DustBlock extends SubBlock implements IMassiveBlock {

    public DustBlock(final String name, final String texture) {
        super(name, texture);
    }

    public DustBlock(final String name, final String texture, final String tool, final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
    }

    public DustBlock(final String name, final String texture, final String tool, final int harvestLevel, final float hardness, final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
    }

    @Override
    public boolean dropsSelf() {
        return false;
    }

    @Override
    public Item getItemDropped(final int meta, final Random random, final int fortune) {
        return ARItems.dustMote.getItem();
    }

    @Override
    public int damageDropped(final int meta) {
        return ARItems.dustMote.getDamage();
    }

    @Override
    public int quantityDropped(final int meta, final int fortune, final Random random) {
        return Math.min(random.nextInt(3) + random.nextInt(10) * fortune, 9);
    }

    @Override
    public float getMass(final World w, final int x, final int y, final int z, final int meta) {
        return 0.01F;
    }
}
