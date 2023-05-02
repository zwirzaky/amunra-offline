package de.katzenpapst.amunra.world;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;

public class WorldGenOre extends WorldGenMinableMeta {

    public final int minY;
    public final int maxY;

    public final int amountPerChunk;

    /**
     * I just overrode this because I hated the constructor
     *
     * @param minableBlock   Block to place, usually ore
     * @param numberOfBlocks Rarity in some way, not sure yet. I think cluster size.
     * @param fillerBlock    Block to replace with the minable Block
     * @param amountPerChunk How often this generator is called per chunk
     * @param minY           min height to generate
     * @param maxY           max dito
     */
    public WorldGenOre(final BlockMetaPair minableBlock, final int numberOfBlocks, final BlockMetaPair fillerBlock,
            final int amountPerChunk, final int minY, final int maxY) {
        super(
                minableBlock.getBlock(),
                numberOfBlocks,
                minableBlock.getMetadata(),
                true,
                fillerBlock.getBlock(),
                fillerBlock.getMetadata());
        this.minY = minY;
        this.maxY = maxY;
        this.amountPerChunk = amountPerChunk;
    }

}
