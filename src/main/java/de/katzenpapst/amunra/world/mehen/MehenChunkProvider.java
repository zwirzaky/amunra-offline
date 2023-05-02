package de.katzenpapst.amunra.world.mehen;

import java.util.Arrays;
import java.util.List;

import de.katzenpapst.amunra.block.ARBlocks;
import de.katzenpapst.amunra.mob.entity.EntitySentry;
import de.katzenpapst.amunra.world.asteroidWorld.AmunRaAsteroidsChunkProvider;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class MehenChunkProvider extends AmunRaAsteroidsChunkProvider {

    public MehenChunkProvider(final World world, final long seed, final boolean mapFeaturesEnabled) {
        super(world, seed, mapFeaturesEnabled);
    }

    @Override
    public List<SpawnListEntry> getPossibleCreatures(final EnumCreatureType creatureType, final int x, final int y, final int z) {
        if (creatureType == EnumCreatureType.monster) {
            return Arrays.asList(new SpawnListEntry(EntitySentry.class, 3000, 1, 3));
        }
        return null;
    }

    @Override
    protected void initBlockTypes() {
        super.initBlockTypes();

        addBlockToHandler(coreHandler, ARBlocks.oreDiamondAsteroid, 1, .1);
        addBlockToHandler(coreHandler, ARBlocks.oreRubyAsteroid, 1, .1);
        addBlockToHandler(coreHandler, ARBlocks.oreEmeraldAsteroid, 1, .1);
        addBlockToHandler(coreHandler, ARBlocks.oreCopperAsteroid, 15, .2);
        addBlockToHandler(coreHandler, ARBlocks.oreLeadAsteroid, 8, .2);
        addBlockToHandler(coreHandler, ARBlocks.oreUraniumAsteroid, 4, .05);
        addBlockToHandler(coreHandler, ARBlocks.blockDarkmatter, 1, .05);
    }

    @Override
    protected int adjustBrightnessValue(int count) {
        count += 8;

        if (count > 12) count = 12;

        return count;
    }

}
