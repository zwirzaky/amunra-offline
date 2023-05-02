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
        if (!(other instanceof BlockMetaPair otherReal)) {
            return false;
        }
        return otherReal.getBlock() == this.getBlock() && otherReal.getMetadata() == this.getMetadata();
    }

    @Override
    public int hashCode() {
        // the block's hash code, and the meta in the first 4 bits
        return this.getBlock().hashCode() ^ this.getMetadata() << 28;
    }

}
