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
        return new BlockMetaPair(
                this.world.getBlock(this.x, this.y, this.z),
                (byte) this.world.getBlockMetadata(this.x, this.y, this.z));
    }

    public boolean isBlockMetaPair(final BlockMetaPair bmp) {
        return this.world.getBlock(this.x, this.y, this.z) == bmp.getBlock()
                && this.world.getBlockMetadata(this.x, this.y, this.z) == bmp.getMetadata();
    }

    @Override
    public int hashCode() {
        return this.world.hashCode() ^ super.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof BlockVector blockVector)) {
            return false;
        }
        return this.world.equals(blockVector.world) && super.equals(other);
    }
}
