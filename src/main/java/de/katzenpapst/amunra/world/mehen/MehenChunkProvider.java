package de.katzenpapst.amunra.world.mehen;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

import de.katzenpapst.amunra.block.ARBlocks;
import de.katzenpapst.amunra.mob.entity.EntitySentry;
import de.katzenpapst.amunra.world.asteroidWorld.AmunRaAsteroidsChunkProvider;

public class MehenChunkProvider extends AmunRaAsteroidsChunkProvider {

    public MehenChunkProvider(final World world, final long seed, final boolean mapFeaturesEnabled) {
        super(world, seed, mapFeaturesEnabled);
    }

    @Override
    public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_) {
        if (p_73155_1_ == EnumCreatureType.monster) {
            return Arrays.asList(new SpawnListEntry(EntitySentry.class, 3000, 1, 3));
        }
        return null;
    }

    @Override
    protected void initBlockTypes() {
        super.initBlockTypes();

        this.addBlockToHandler(this.coreHandler, ARBlocks.oreDiamondAsteroid, 1, .1);
        this.addBlockToHandler(this.coreHandler, ARBlocks.oreRubyAsteroid, 1, .1);
        this.addBlockToHandler(this.coreHandler, ARBlocks.oreEmeraldAsteroid, 1, .1);
        this.addBlockToHandler(this.coreHandler, ARBlocks.oreCopperAsteroid, 15, .2);
        this.addBlockToHandler(this.coreHandler, ARBlocks.oreLeadAsteroid, 8, .2);
        this.addBlockToHandler(this.coreHandler, ARBlocks.oreUraniumAsteroid, 4, .05);
        this.addBlockToHandler(this.coreHandler, ARBlocks.blockDarkmatter, 1, .05);
    }

    @Override
    protected int adjustBrightnessValue(int count) {
        count += 8;

        if (count > 12) count = 12;

        return count;
    }

}
