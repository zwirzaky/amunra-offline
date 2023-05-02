package de.katzenpapst.amunra.world.maahes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.IChunkProvider;

import de.katzenpapst.amunra.block.ARBlocks;
import de.katzenpapst.amunra.mob.entity.EntityAlienBug;
import de.katzenpapst.amunra.mob.entity.EntityPorcodon;
import de.katzenpapst.amunra.world.AmunraChunkProvider;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;

public class MaahesChunkProvider extends AmunraChunkProvider {

    public MaahesChunkProvider(final World world, final long seed, final boolean mapFeaturesEnabled) {
        super(world, seed, mapFeaturesEnabled);
    }

    @Override
    protected BiomeDecoratorSpace getBiomeGenerator() {
        return new MaahesBiomeDecorator();
    }

    // This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
    // and the fact that biomes are outside the scope of this tutorial
    @Override
    protected BiomeGenBase[] getBiomesForGeneration() {
        return new BiomeGenBase[] { BiomeGenBase.desert };
    }

    @Override
    protected SpawnListEntry[] getCreatures() {

        // entityClass, weightedProbability, minGroupCount, maxGroupCount
        final SpawnListEntry pig = new SpawnListEntry(EntityPorcodon.class, 50, 4, 10);
        return new SpawnListEntry[] { pig };

        // SpawnListEntry villager = new SpawnListEntry(EntityAlienVillager.class, 1, 0, 2);
        // return new SpawnListEntry[]{villager};
    }

    @Override
    protected BlockMetaPair getDirtBlock() {
        return ARBlocks.blockMethaneDirt;
    }

    @Override
    protected BlockMetaPair getGrassBlock() {
        return ARBlocks.blockMethaneGrass;
    }

    @Override
    protected BlockMetaPair getStoneBlock() {
        return ARBlocks.blockBasalt;
    }

    /**
     * Seems to affect the baseheight doesn't affect the bedrock holes
     */
    @Override
    public double getHeightModifier() {
        return 10;
    }

    @Override
    protected SpawnListEntry[] getMonsters() {
        final SpawnListEntry bug = new SpawnListEntry(EntityAlienBug.class, 100, 4, 4);

        return new SpawnListEntry[] { bug };
    }

    @Override
    public double getMountainHeightModifier() {
        return 0;
    }

    /**
     * medium terrain height, doesn't affect the bedrock holes
     */
    @Override
    protected int getSeaLevel() {
        return 56;
    }

    /**
     * doesn't affect the bedrock holes
     */
    @Override
    public double getSmallFeatureHeightModifier() {
        return 0;
    }

    /**
     * doesn't affect the bedrock holes
     */
    @Override
    public double getValleyHeightModifier() {
        return 0;
    }

    @Override
    protected List<MapGenBaseMeta> getWorldGenerators() {
        // TODO fill in with caves and villages
        return new ArrayList<>();
    }

    @Override
    public void onChunkProvide(final int arg0, final int arg1, final Block[] arg2, final byte[] arg3) {}

    @Override
    public void onPopulate(final IChunkProvider arg0, final int arg1, final int arg2) {}

    @Override
    public boolean chunkExists(final int x, final int y) {
        return false;
    }

}
