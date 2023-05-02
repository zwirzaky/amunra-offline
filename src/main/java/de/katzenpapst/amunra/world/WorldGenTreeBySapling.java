package de.katzenpapst.amunra.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import de.katzenpapst.amunra.block.BlockBasicMeta;
import de.katzenpapst.amunra.block.bush.AbstractSapling;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class WorldGenTreeBySapling extends WorldGenAbstractTree {

    protected AbstractSapling sapling;

    public WorldGenTreeBySapling(final boolean doBlockNotify, final int minTreeHeight, final BlockMetaPair sapling) {
        super(doBlockNotify);
        this.sapling = (AbstractSapling) ((BlockBasicMeta) sapling.getBlock()).getSubBlock(sapling.getMetadata());
    }

    public WorldGenTreeBySapling(final boolean doBlockNotify, final int minTreeHeight, final AbstractSapling sapling) {
        super(doBlockNotify);
        this.sapling = sapling;
    }

    @Override
    public boolean generate(final World world, final Random rand, final int x, final int y, final int z) {
        return this.sapling.generate(world, rand, x, y, z, false);
    }
}
