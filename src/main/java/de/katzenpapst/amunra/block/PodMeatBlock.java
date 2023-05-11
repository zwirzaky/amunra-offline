package de.katzenpapst.amunra.block;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PodMeatBlock extends SubBlock {

    public PodMeatBlock(final String name, final String texture) {
        super(name, texture, "axe", 1);
    }

    @Override
    public boolean dropsSelf() {
        return false;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Item.getItemFromBlock(ARBlocks.blockPodSapling.getBlock());
    }

    @Override
    public int damageDropped(int meta) {
        return ARBlocks.blockPodSapling.getMetadata();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return super.shouldSideBeRendered(worldIn, x, y, z, 1 - side);
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(100) > 90 ? 1 : 0;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return random.nextInt(100) > 90 - 10 * fortune ? 1 : 0;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random) {
        // Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
        return this.quantityDroppedWithBonus(fortune, random);
    }

}
