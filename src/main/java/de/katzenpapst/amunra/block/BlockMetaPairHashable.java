package de.katzenpapst.amunra.block;

import net.minecraft.block.Block;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

/**
 * Like BlockMetaPair, but should work in HashMaps
 * 
 * @author katzenpapst
 *
 */
public class BlockMetaPairHashable extends BlockMetaPair {

    public BlockMetaPairHashable(final Block block, final byte metadata) {
        super(block, metadata);
    }

    public BlockMetaPairHashable(final BlockMetaPair bmp) {
        super(bmp.getBlock(), bmp.getMetadata());
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof BlockMetaPair)) {
            return false;
        }
        final BlockMetaPair otherReal = (BlockMetaPair) other;
        return otherReal.getBlock() == getBlock() && otherReal.getMetadata() == getMetadata();
    }

    @Override
    public int hashCode() {
        // the block's hash code, and the meta in the first 4 bits
        return this.getBlock().hashCode() ^ getMetadata() << 28;
    }

}
