package de.katzenpapst.amunra.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class UnderwaterGrass extends SubBlockGrass {

    private final BlockMetaPair dirtVersion;

    public UnderwaterGrass(final String name, final String textureTop, final String textureSide,
            final String textureBottom) {
        super(name, textureTop, textureSide, textureBottom);

        this.dirtVersion = new BlockMetaPair(Blocks.clay, (byte) 0);
    }

    @Override
    public int quantityDropped(final int meta, final int fortune, final Random random) {
        return this.getDirtBlock().getBlock().quantityDropped(this.getDirtBlock().getMetadata(), fortune, random);
    }

    /**
     * Return the block what this should revert to if the conditions are bad
     * 
     * @return
     */
    @Override
    public BlockMetaPair getDirtBlock() {
        return this.dirtVersion;
    }

    /**
     * Return true if the current conditions are good for this grasses survival, usually light stuff The Multiblock will
     * replace it with this.getDirtBlock() Will also be called for dirt neighbors of this in order to check if this
     * *could* live there
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    @Override
    public boolean canLiveHere(final World world, final int x, final int y, final int z) {
        // this can only live underwater
        // TODO add special check for fences, grasses etc
        // Blocks.
        final Block blockAbove = world.getBlock(x, y + 1, z);
        return blockAbove == Blocks.water || blockAbove == Blocks.flowing_water;
    }

    /**
     *
     */
    @Override
    public boolean canSpread(final World world, final int x, final int y, final int z) {
        // if it can live, then it can spread, no extra checks
        return true;
    }

}
