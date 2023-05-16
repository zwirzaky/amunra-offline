package de.katzenpapst.amunra.world.maahes;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

import de.katzenpapst.amunra.block.ARBlocks;
import de.katzenpapst.amunra.mob.entity.EntityAlienBug;
import de.katzenpapst.amunra.mob.entity.EntityPorcodon;
import de.katzenpapst.amunra.world.AmunraChunkProvider;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;

public class MaahesChunkProvider extends AmunraChunkProvider {

    public MaahesChunkProvider(final World world, final long seed, final boolean mapFeaturesEnabled) {
        super(world, seed, mapFeaturesEnabled);
        this.creatures = new SpawnListEntry[] {new SpawnListEntry(EntityPorcodon.class, 50, 4, 10)};
        this.monsters = new SpawnListEntry[] {new SpawnListEntry(EntityAlienBug.class, 100, 4, 4)};
    }

    @Override
    protected BiomeDecoratorSpace getBiomeGenerator() {
        return new MaahesBiomeDecorator();
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

    @Override
    public double getHeightModifier() {
        return 10;
    }

    @Override
    public double getMountainHeightModifier() {
        return 0;
    }

    @Override
    protected int getSeaLevel() {
        return 56;
    }

    @Override
    public double getSmallFeatureHeightModifier() {
        return 0;
    }

    @Override
    public double getValleyHeightModifier() {
        return 0;
    }

}
