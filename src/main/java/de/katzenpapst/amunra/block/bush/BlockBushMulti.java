package de.katzenpapst.amunra.block.bush;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;

import de.katzenpapst.amunra.block.BlockBasicMeta;
import de.katzenpapst.amunra.block.SubBlock;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

// SEE net.minecraft.block.BlockBush
public class BlockBushMulti extends BlockBasicMeta implements IGrowable, IShearable, IPlantable {

    public BlockBushMulti(final String name, final Material mat) {
        super(name, mat);
        this.setTickRandomly(true);
    }

    public BlockBushMulti(final String name, final Material mat, final int numSubBlocks) {
        super(name, mat, numSubBlocks);
        this.setTickRandomly(true);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(final World world, final int x, final int y, final int z, final Random rand) {
        this.checkAndDropBlock(world, x, y, z);
    }

    /**
     * checks if the block can stay, if not drop as item
     */
    protected void checkAndDropBlock(final World world, final int x, final int y, final int z) {
        if (!this.canBlockStay(world, x, y, z)) {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlock(x, y, z, getBlockById(0), 0, 2);
        }
    }

    @Override
    public BlockMetaPair addSubBlock(final int meta, final SubBlock sb) {
        if (!(sb instanceof SubBlockBush)) {
            throw new IllegalArgumentException("BlockBushMulti can only accept SubBlockBush");
        }
        return super.addSubBlock(meta, sb);
    }

    @Override
    public boolean isShearable(final ItemStack item, final IBlockAccess world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        return ((SubBlockBush) this.getSubBlock(meta)).isShearable(item, world, x, y, z);
    }

    @Override
    public ArrayList<ItemStack> onSheared(final ItemStack item, final IBlockAccess world, final int x, final int y,
            final int z, final int fortune) {

        final int meta = world.getBlockMetadata(x, y, z);
        return ((SubBlockBush) this.getSubBlock(meta)).onSheared(item, world, x, y, z, fortune);
    }

    /**
     * func_149851_a is basically a stillGrowing() method. It returns (or should return) true if the growth stage is
     * less than the max growth stage.
     *
     * info source: http://www.minecraftforge.net/forum/index.php?topic=22571.0
     */
    @Override
    public boolean func_149851_a(final World world, final int x, final int y, final int z,
            final boolean isWorldRemote) {
        final int meta = world.getBlockMetadata(x, y, z);
        return ((SubBlockBush) this.getSubBlock(meta)).func_149851_a(world, x, y, z, isWorldRemote);
    }

    /**
     * func_149852_a is basically a canBoneMealSpeedUpGrowth() method. I usually just return true, but depends on your
     * crop.
     */
    @Override
    public boolean func_149852_a(final World world, final Random rand, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        return ((SubBlockBush) this.getSubBlock(meta)).func_149852_a(world, rand, x, y, z);
    }

    /**
     * func_149853_b is basically an incrementGrowthStage() method. In vanilla crops the growth stage is stored in
     * metadata so then in this method you would increment it if it wasn't already at maximum and store back in
     * metadata.
     *
     */
    @Override
    public void func_149853_b(final World world, final Random rand, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        ((SubBlockBush) this.getSubBlock(meta)).func_149853_b(world, rand, x, y, z);

    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World worldIn, final int x, final int y, final int z) {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return 1;
    }

    @Override
    public EnumPlantType getPlantType(final IBlockAccess world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        return ((SubBlockBush) this.getSubBlock(meta)).getPlantType(world, x, y, z);
    }

    @Override
    public Block getPlant(final IBlockAccess world, final int x, final int y, final int z) {
        return this;
    }

    @Override
    public int getPlantMetadata(final IBlockAccess world, final int x, final int y, final int z) {
        return world.getBlockMetadata(x, y, z);
    }

    public boolean canPlaceOn(final BlockMetaPair blockToCheck, final int meta) {
        return ((SubBlockBush) this.getSubBlock(meta)).canPlaceOn(blockToCheck, 0);
    }

    public boolean canPlaceOn(final Block blockToCheck, final int metaToCheck, final int meta) {
        return ((SubBlockBush) this.getSubBlock(meta)).canPlaceOn(blockToCheck, metaToCheck, 0);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    @Override
    public void onNeighborBlockChange(final World world, final int x, final int y, final int z, final Block b) {
        super.onNeighborBlockChange(world, x, y, z, b);
        this.checkAndDropBlock(world, x, y, z);
    }
    /*
     * @Override public boolean isCollidable() { return false; }
     */
}
