package de.katzenpapst.amunra.world;

import java.util.Random;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import de.katzenpapst.amunra.block.BlockBasicMeta;
import de.katzenpapst.amunra.block.bush.AbstractSapling;

public class WorldGenTreeBySapling extends WorldGenAbstractTree {

    protected AbstractSapling sapling;

    public WorldGenTreeBySapling(boolean doBlockNotify, int minTreeHeight, BlockMetaPair sapling) {
        super(doBlockNotify);
        this.sapling = (AbstractSapling) ((BlockBasicMeta) sapling.getBlock()).getSubBlock(sapling.getMetadata());
    }

    public WorldGenTreeBySapling(boolean doBlockNotify, int minTreeHeight, AbstractSapling sapling) {
        super(doBlockNotify);
        this.sapling = sapling;
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        return this.sapling.generate(world, rand, x, y, z, false);
    }
}
