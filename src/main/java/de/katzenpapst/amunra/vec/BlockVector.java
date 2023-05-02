package de.katzenpapst.amunra.vec;

import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

/**
 * This is supposed to hold everything necessary to find a block
 *
 */
public class BlockVector extends Vector3int {

    public World world;

    public BlockVector(final World world, final int x, final int y, final int z) {
        super(x, y, z);
        this.world = world;
    }

    public BlockMetaPair getBlockMetaPair() {
        return new BlockMetaPair(world.getBlock(x, y, z), (byte) world.getBlockMetadata(x, y, z));
    }

    public boolean isBlockMetaPair(final BlockMetaPair bmp) {
        return world.getBlock(x, y, z) == bmp.getBlock() && world.getBlockMetadata(x, y, z) == bmp.getMetadata();
    }

    @Override
    public int hashCode() {
        return world.hashCode() ^ super.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof BlockVector)) {
            return false;
        }
        return world.equals(((BlockVector) other).world) && super.equals(other);
    }
}
