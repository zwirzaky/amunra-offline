package de.katzenpapst.amunra.block.bush;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import de.katzenpapst.amunra.block.ARBlocks;

public class MethaneTallGrass extends SubBlockBush {

    public MethaneTallGrass(final String name, final String texture) {
        super(name, texture);
        // TODO Auto-generated constructor stub
    }

    public MethaneTallGrass(final String name, final String texture, final String tool, final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
        // TODO Auto-generated constructor stub
    }

    public MethaneTallGrass(final String name, final String texture, final String tool, final int harvestLevel,
            final float hardness, final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean canPlaceOn(final Block blockToCheck, final int metaToCheck, final int meta) {
        return blockToCheck == ARBlocks.blockMethaneGrass.getBlock()
                && metaToCheck == ARBlocks.blockMethaneGrass.getMetadata()
                || blockToCheck == ARBlocks.blockVacuumGrass.getBlock()
                        && metaToCheck == ARBlocks.blockVacuumGrass.getMetadata();
        // return true;
    }

    @Override
    public boolean canBlockStay(final World world, final int x, final int y, final int z) {
        final Block belowBlock = world.getBlock(x, y - 1, z);
        final int myMeta = world.getBlockMetadata(x, y, z);
        final int belowMeta = world.getBlockMetadata(x, y - 1, z);
        return this.canPlaceOn(belowBlock, belowMeta, myMeta);
    }

}
