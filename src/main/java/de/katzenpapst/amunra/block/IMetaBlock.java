package de.katzenpapst.amunra.block;

import net.minecraft.block.Block;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public interface IMetaBlock {

    BlockMetaPair addSubBlock(int meta, SubBlock sb);

    int getMetaByName(String name);

    SubBlock getSubBlock(int meta);

    String getUnlocalizedSubBlockName(int meta);

    void register();

    int getNumPossibleSubBlocks();

    /**
     * This should take a metadata, and return only the part of it which is used for subblock distinction, aka, strip
     * off things like rotational information
     */
    default int getDistinctionMeta(final int meta) {
        final int numSubBlocks = this.getNumPossibleSubBlocks();
        if (numSubBlocks < 4) {
            return meta & 1;
        }
        if (numSubBlocks < 8) {
            return meta & 3;
        }
        if (numSubBlocks < 16) {
            return meta & 7;
        }
        return meta;
    }

    /**
     * Gets the rotation meta, downshifted if needed
     */
    default int getRotationMeta(final int meta) {
        return (meta & 12) >> 2;
    }

    default BlockMetaPair getBlockMetaPair(final String name) {
        return new BlockMetaPair((Block) this, (byte) this.getMetaByName(name));
    }

    /**
     * Adds rotationmeta to some other metadata
     */
    default int addRotationMeta(final int baseMeta, final int rotationMeta) {
        return baseMeta | rotationMeta << 2;
    }
}
