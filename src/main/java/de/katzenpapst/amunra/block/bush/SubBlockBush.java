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

    public SubBlockBush(String name, String texture) {
        super(name, texture, null, 0);
    }

    public SubBlockBush(String name, String texture, String tool, int harvestLevel) {
        super(name, texture, tool, harvestLevel);
    }

    public SubBlockBush(String name, String texture, String tool, int harvestLevel, float hardness, float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();
        result.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
        return result;
    }

    @Override
    public boolean func_149851_a(World worldIn, int x, int y, int z,
            boolean isWorldRemote) {
        return false;
    }

    @Override
    public boolean func_149852_a(World worldIn, Random random, int x, int y,
            int z) {
        return false;
    }

    @Override
    public void func_149853_b(World worldIn, Random random, int x, int y,
            int z) {

    }

    public boolean canPlaceOn(BlockMetaPair blockToCheck, int meta) {
        return canPlaceOn(blockToCheck.getBlock(), blockToCheck.getMetadata(), meta);
    }

    public boolean canPlaceOn(Block blockToCheck, int metaToCheck, int meta) {

        return true;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Plains;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return (Block) parent;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess par1World, int x, int y, int z) {
        return false;
    }

}
