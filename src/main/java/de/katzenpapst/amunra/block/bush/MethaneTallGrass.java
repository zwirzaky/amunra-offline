package de.katzenpapst.amunra.block.bush;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import de.katzenpapst.amunra.block.ARBlocks;

public class MethaneTallGrass extends SubBlockBush {

    public MethaneTallGrass(final String name, final String texture) {
        super(name, texture);
    }

    public MethaneTallGrass(final String name, final String texture, final String tool, final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
    }

    public MethaneTallGrass(final String name, final String texture, final String tool, final int harvestLevel,
            final float hardness, final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
    }

    @Override
    public boolean canPlaceOn(final Block blockToCheck, final int metaToCheck, final int meta) {
        return blockToCheck == ARBlocks.blockMethaneGrass.getBlock()
                && metaToCheck == ARBlocks.blockMethaneGrass.getMetadata()
                || blockToCheck == ARBlocks.blockVacuumGrass.getBlock()
                        && metaToCheck == ARBlocks.blockVacuumGrass.getMetadata();
    }

    @Override
    public boolean canBlockStay(World worldIn, int x, int y, int z) {
        final Block belowBlock = worldIn.getBlock(x, y - 1, z);
        final int myMeta = worldIn.getBlockMetadata(x, y, z);
        final int belowMeta = worldIn.getBlockMetadata(x, y - 1, z);
        return this.canPlaceOn(belowBlock, belowMeta, myMeta);
    }

}
