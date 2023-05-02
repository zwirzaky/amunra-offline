package de.katzenpapst.amunra.world.mapgen.populator;

import net.minecraft.world.World;

import de.katzenpapst.amunra.helper.CoordHelper;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;

abstract public class AbstractPopulator {

    protected int x;
    protected int y;
    protected int z;

    public abstract boolean populate(World world);

    public AbstractPopulator(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean isInChunk(final int chunkX, final int chunkZ) {
        return CoordHelper.getChunkBB(chunkX, chunkZ).isVecInside(x, y, z);
    }

    public BlockVec3 getBlockVec3() {
        return new BlockVec3(x, y, z);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
