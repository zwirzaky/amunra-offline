package de.katzenpapst.amunra.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class BlockGrassMeta extends BlockBasicMeta implements IGrowable {

    public BlockGrassMeta(final String name, final Material mat) {
        super(name, mat);
        this.setTickRandomly(true);
    }

    @Override
    public BlockMetaPair addSubBlock(final int meta, final SubBlock sb) {
        if (!(sb instanceof SubBlockGrass)) {
            throw new IllegalArgumentException("BlockGrassMulti can only accept SubBlockGrass");
        }
        return super.addSubBlock(meta, sb);
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
        return true;
    }

    /**
     * func_149852_a is basically a canBoneMealSpeedUpGrowth() method. I usually just return true, but depends on your
     * crop.
     */
    @Override
    public boolean func_149852_a(final World world, final Random rand, final int x, final int y, final int z) {
        return true;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(final World world, final int x, final int y, final int z, final Random rand) {
        final int meta = world.getBlockMetadata(x, y, z);
        final SubBlockGrass sb = (SubBlockGrass) this.getSubBlock(meta);
        final BlockMetaPair dirtForm = sb.getDirtBlock();
        if (!world.isRemote) {
            if (!sb.canLiveHere(world, x, y, z)) {
                world.setBlock(x, y, z, dirtForm.getBlock(), dirtForm.getMetadata(), 3);
            } else if (sb.canSpread(world, x, y, z)) {
                for (int l = 0; l < 4; ++l) {
                    final int nbX = x + rand.nextInt(3) - 1;
                    final int nbY = y + rand.nextInt(5) - 3;
                    final int nbZ = z + rand.nextInt(3) - 1;
                    final Block block = world.getBlock(nbX, nbY, nbZ);
                    final int blockMeta = world.getBlockMetadata(nbX, nbY, nbZ);

                    if (block == dirtForm.getBlock() && blockMeta == dirtForm.getMetadata()) {
                        final boolean canLive = sb.canLiveHere(world, nbX, nbY, nbZ);

                        if (canLive) {
                            world.setBlock(nbX, nbY, nbZ, this, meta, 3);
                        }
                    }
                }
            }
        }
    }

    /**
     * func_149853_b is basically an incrementGrowthStage() method. In vanilla crops the growth stage is stored in
     * metadata so then in this method you would increment it if it wasn't already at maximum and store back in
     * metadata.
     *
     */
    @Override
    public void func_149853_b(final World world, final Random rand, final int x, final int y, final int z) {
        int l = 0;
        final int meta = world.getBlockMetadata(x, y, z);
        final SubBlockGrass sb = (SubBlockGrass) this.getSubBlock(meta);

        while (l < 128) {
            int blockAboveX = x;
            int blockAboveY = y + 1;
            int blockAboveZ = z;
            int grassNearby = 0;

            while (true) {
                if (grassNearby < l / 16) // why 1/16??
                {
                    blockAboveX += rand.nextInt(3) - 1;
                    blockAboveY += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2;
                    blockAboveZ += rand.nextInt(3) - 1;
                    if (world.getBlock(blockAboveX, blockAboveY - 1, blockAboveZ) == this && // I hope I can use "this"
                                                                                             // here
                            world.getBlockMetadata(blockAboveX, blockAboveY, blockAboveZ) == meta
                            && !world.getBlock(blockAboveX, blockAboveY, blockAboveZ).isNormalCube()) {
                        ++grassNearby;
                        continue;
                    }
                } else if (world.getBlock(blockAboveX, blockAboveY, blockAboveZ).getMaterial() == Material.air) {
                    sb.growPlantsOnTop(world, rand, blockAboveX, blockAboveY, blockAboveZ);
                }

                ++l;
                break;
            }
        }

    }

}
