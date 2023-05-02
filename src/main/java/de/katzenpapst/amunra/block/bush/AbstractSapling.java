package de.katzenpapst.amunra.block.bush;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

abstract public class AbstractSapling extends SubBlockBush {

    protected BlockMetaPair wood;
    protected BlockMetaPair leaves;

    public AbstractSapling(final String name, final String texture) {
        super(name, texture);
    }

    public AbstractSapling setWood(final BlockMetaPair wood) {
        this.wood = wood;
        return this;
    }

    public AbstractSapling setLeaves(final BlockMetaPair leaves) {
        this.leaves = leaves;
        return this;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(final World world, final int x, final int y, final int z, final Random rand) {

        if (world.getBlockLightValue(x, y + 1, z) >= 9 && rand.nextInt(7) == 0) {
            this.prepareGrowTree(world, x, y, z, rand);
        }

    }

    public void prepareGrowTree(final World world, final int x, final int y, final int z, final Random rand) {
        final int l = world.getBlockMetadata(x, y, z);

        if ((l & 8) == 0) {
            world.setBlockMetadataWithNotify(x, y, z, l | 8, 4);
        } else {
            this.growTree(world, x, y, z, rand);
        }
    }

    public void growTree(final World world, final int x, final int y, final int z, final Random rand) {

        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, x, y, z)) return;

        final int meta = world.getBlockMetadata(x, y, z) & 7;
        final Block block = Blocks.air;

        // self-removal before tree generation
        world.setBlock(x, y, z, block, 0, 4);
        if (!generate(world, rand, x, y, z, true)) {
            // return self on failure
            world.setBlock(x, y, z, (Block) this.parent, meta, 4);
        }

    }

    public boolean checkBlockAt(final World world, final int x, final int y, final int z, final int metadata) {
        return world.getBlock(x, y, z) == this && (world.getBlockMetadata(x, y, z) & 7) == metadata;
    }

    /**
     * func_149851_a is basically a stillGrowing() method. It returns (or should return) true if the growth stage is
     * less than the max growth stage.
     *
     * info source: http://www.minecraftforge.net/forum/index.php?topic=22571.0
     */
    @Override
    public boolean func_149851_a(final World world, final int x, final int y, final int z, final boolean isWorldRemote) {
        return true;
    }

    /**
     * func_149852_a is basically a canBoneMealSpeedUpGrowth() method. I usually just return true, but depends on your
     * crop.
     */
    @Override
    public boolean func_149852_a(final World world, final Random rand, final int x, final int y, final int z) {
        return world.rand.nextFloat() < 0.45D;
    }

    /**
     * func_149853_b is basically an incrementGrowthStage() method. In vanilla crops the growth stage is stored in
     * metadata so then in this method you would increment it if it wasn't already at maximum and store back in
     * metadata.
     *
     */
    @Override
    public void func_149853_b(final World worldIn, final Random random, final int x, final int y,
            final int z) {
        this.prepareGrowTree(worldIn, x, y, z, random);
    }

    protected boolean isBlockReplaceable(final Block block) {
        return block instanceof BlockBushMulti || block.getMaterial() == Material.air
                || block.getMaterial() == Material.leaves
                || block == Blocks.grass
                || block == Blocks.dirt
                || block == Blocks.log
                || block == Blocks.log2
                || block == Blocks.sapling
                || block == Blocks.vine;
    }

    protected boolean canReplaceBlock(final World world, final int x, final int y, final int z) {
        final Block block = world.getBlock(x, y, z);
        // int meta = world.getBlockMetadata(x, y, z);
        return block.isAir(world, x, y, z) || block.isLeaves(world, x, y, z)
                || block.isWood(world, x, y, z)
                || isBlockReplaceable(block);
    }

    protected void setBlockAndNotifyAdequately(final World world, final int x, final int y, final int z, final Block block, final int meta,
            final boolean notify) {
        if (notify) {
            world.setBlock(x, y, z, block, meta, 3);
        } else {
            world.setBlock(x, y, z, block, meta, 2);
        }
    }

    // abstract protected boolean canGenerateHere(World world, Random rand, int x, int y, int z, int curHeight);

    abstract public boolean generate(World world, Random rand, int x, int y, int z, boolean notify);

}
