package de.katzenpapst.amunra.block.bush;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;

import de.katzenpapst.amunra.block.SubBlock;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class SubBlockBush extends SubBlock implements IGrowable, IShearable, IPlantable {

    public SubBlockBush(final String name, final String texture) {
        super(name, texture, null, 0);
    }

    public SubBlockBush(final String name, final String texture, final String tool, final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
    }

    public SubBlockBush(final String name, final String texture, final String tool, final int harvestLevel, final float hardness, final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
    }

    @Override
    public boolean isShearable(final ItemStack item, final IBlockAccess world, final int x, final int y, final int z) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(final ItemStack item, final IBlockAccess world, final int x, final int y, final int z, final int fortune) {
        final ArrayList<ItemStack> result = new ArrayList<>();
        result.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
        return result;
    }

    @Override
    public boolean func_149851_a(final World worldIn, final int x, final int y, final int z,
            final boolean isWorldRemote) {
        return false;
    }

    @Override
    public boolean func_149852_a(final World worldIn, final Random random, final int x, final int y,
            final int z) {
        return false;
    }

    @Override
    public void func_149853_b(final World worldIn, final Random random, final int x, final int y,
            final int z) {

    }

    public boolean canPlaceOn(final BlockMetaPair blockToCheck, final int meta) {
        return canPlaceOn(blockToCheck.getBlock(), blockToCheck.getMetadata(), meta);
    }

    public boolean canPlaceOn(final Block blockToCheck, final int metaToCheck, final int meta) {

        return true;
    }

    @Override
    public EnumPlantType getPlantType(final IBlockAccess world, final int x, final int y, final int z) {
        return EnumPlantType.Plains;
    }

    @Override
    public Block getPlant(final IBlockAccess world, final int x, final int y, final int z) {
        return (Block) parent;
    }

    @Override
    public int getPlantMetadata(final IBlockAccess world, final int x, final int y, final int z) {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    public boolean getBlocksMovement(final IBlockAccess par1World, final int x, final int y, final int z) {
        return false;
    }

}
