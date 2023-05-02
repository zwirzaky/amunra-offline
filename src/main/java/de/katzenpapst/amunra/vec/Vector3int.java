package de.katzenpapst.amunra.vec;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.api.vector.Vector3;

public class Vector3int {

    public int x;
    public int y;
    public int z;

    public Vector3int(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3int(final BlockVec3 blockVec) {
        this.x = blockVec.x;
        this.y = blockVec.y;
        this.z = blockVec.z;
    }

    public Vector3int(final NBTTagCompound nbt) {
        this.x = nbt.getInteger("x");
        this.y = nbt.getInteger("y");
        this.z = nbt.getInteger("z");
    }

    public Vector3int(final TileEntity tile) {
        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
    }

    public NBTTagCompound toNBT() {
        final NBTTagCompound nbt = new NBTTagCompound();

        nbt.setInteger("x", x);
        nbt.setInteger("y", y);
        nbt.setInteger("z", z);

        return nbt;
    }

    public BlockVec3 toBlockVec3() {
        return new BlockVec3(x, y, z);
    }

    public Vector3 toVector3() {
        return new Vector3(x, y, z);
    }

    @Override
    public int hashCode() {
        // now, what do I do for 3 values?
        return x << 20 ^ y << 10 ^ z;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Vector3int)) {
            return false;
        }
        return x == ((Vector3int) other).x && y == ((Vector3int) other).y && z == ((Vector3int) other).z;
    }

    @Override
    public String toString() {
        return "[" + x + "/" + y + "/" + z + "]";
    }

}
