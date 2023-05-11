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

    public SubBlockBush(final String name, final String texture, final String tool, final int harvestLevel,
            final float hardness, final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
        final ArrayList<ItemStack> result = new ArrayList<>();
        result.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
        return result;
    }

    /**
     * func_149851_a is basically a stillGrowing() method. It returns (or should return) true if the growth stage is
     * less than the max growth stage.
     *
     * @see <a href=https://forums.minecraftforge.net/topic/22365-understanding-igrowable/#comment-115221>Understanding
     *      IGrowable - Modder Support - Forge Forums</a>
     */
    @Override
    public boolean func_149851_a(World worldIn, int x, int y, int z, boolean isClient) {
        return false;
    }

    /**
     * func_149852_a is basically a canBoneMealSpeedUpGrowth() method. I usually just return true, but depends on your
     * crop.
     *
     * @see <a href=https://forums.minecraftforge.net/topic/22365-understanding-igrowable/#comment-115221>Understanding
     *      IGrowable - Modder Support - Forge Forums</a>
     */
    @Override
    public boolean func_149852_a(World worldIn, Random random, int x, int y, int z) {
        return false;
    }

    /**
     * func_149853_b is basically an incrementGrowthStage() method. In vanilla crops the growth stage is stored in
     * metadata so then in this method you would increment it if it wasn't already at maximum and store back in
     * metadata.
     *
     * @see <a href=https://forums.minecraftforge.net/topic/22365-understanding-igrowable/#comment-115221>Understanding
     *      IGrowable - Modder Support - Forge Forums</a>
     */
    @Override
    public void func_149853_b(World worldIn, Random random, int x, int y, int z) {}

    public boolean canPlaceOn(final BlockMetaPair blockToCheck, final int meta) {
        return this.canPlaceOn(blockToCheck.getBlock(), blockToCheck.getMetadata(), meta);
    }

    public boolean canPlaceOn(final Block blockToCheck, final int metaToCheck, final int meta) {
        return true;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Plains;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return (Block) this.parent;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess worldIn, int x, int y, int z) {
        return false;
    }

}
