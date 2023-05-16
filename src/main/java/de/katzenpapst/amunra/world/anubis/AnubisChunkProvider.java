package de.katzenpapst.amunra.world.anubis;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.IChunkProvider;

import de.katzenpapst.amunra.block.ARBlocks;
import de.katzenpapst.amunra.world.AmunraChunkProvider;
import de.katzenpapst.amunra.world.mapgen.village.BoxHouseComponent;
import de.katzenpapst.amunra.world.mapgen.village.DomedHouseComponent;
import de.katzenpapst.amunra.world.mapgen.village.GridVillageGenerator;
import de.katzenpapst.amunra.world.mapgen.village.SolarField;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;

public class AnubisChunkProvider extends AmunraChunkProvider {

    protected GridVillageGenerator gVillage = new GridVillageGenerator();

    // PyramidGenerator pyramid = new PyramidGenerator();

    // Pyramid testPyramid = new Pyramid();

    public AnubisChunkProvider(final World world, final long seed, final boolean mapFeaturesEnabled) {
        super(world, seed, mapFeaturesEnabled);

        this.gVillage.addComponentType(BoxHouseComponent.class, 0.9F, 2, 4);
        this.gVillage.addComponentType(SolarField.class, 0.7F, 2, 6);
        this.gVillage.addComponentType(DomedHouseComponent.class, 0.7F, 2, 4);
        // gVillage.addComponentType(PyramidHouseComponent.class, 0.7F, 2, 4);
    }

    @Override
    protected BiomeDecoratorSpace getBiomeGenerator() {
        return new AnubisBiomeDecorator();
    }


    @Override
    protected SpawnListEntry[] getCreatures() {
        // SpawnListEntry villager = new SpawnListEntry(EntityAlienVillager.class, 10, 2, 2);
        return new SpawnListEntry[] {};
    }

    @Override
    protected BlockMetaPair getDirtBlock() {
        return ARBlocks.blockBasaltRegolith;
    }

    @Override
    protected BlockMetaPair getGrassBlock() {
        return ARBlocks.blockDust;
    }

    @Override
    protected BlockMetaPair getStoneBlock() {
        return ARBlocks.blockBasalt;
    }

    @Override
    public double getHeightModifier() {
        return 12;
    }

    @Override
    protected SpawnListEntry[] getMonsters() {
        final SpawnListEntry skele = new SpawnListEntry(EntityEvolvedSkeleton.class, 100, 4, 4);
        final SpawnListEntry creeper = new SpawnListEntry(EntityEvolvedCreeper.class, 100, 4, 4);
        final SpawnListEntry zombie = new SpawnListEntry(EntityEvolvedZombie.class, 100, 4, 4);

        return new SpawnListEntry[] { skele, creeper, zombie };
    }

    @Override
    public double getMountainHeightModifier() {
        return 95;
    }

    @Override
    protected int getSeaLevel() {
        return 93;// taken from mars
    }

    @Override
    public double getSmallFeatureHeightModifier() {
        return 26;
    }

    @Override
    public double getValleyHeightModifier() {
        return 60;
    }

    @Override
    protected List<MapGenBaseMeta> getWorldGenerators() {
        // TODO fill in with caves and villages
        return Arrays.asList(this.gVillage/* , this.pyramid */);
    }

    @Override
    public void onChunkProvide(final int cX, final int cZ, final Block[] blocks, final byte[] metadata) {}

    @Override
    public void onPopulate(final IChunkProvider provider, final int cX, final int cZ) {}

    @Override
    public boolean chunkExists(int p_73149_1_, int p_73149_2_) {
        return true; // ?
    }

    @Override
    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {
        super.populate(p_73153_1_, p_73153_2_, p_73153_3_);

        this.gVillage.populate(this, this.worldObj, p_73153_2_, p_73153_3_);
        // this.pyramid.populate(this, worldObj, chunkX, chunkZ);

        // this.villageTest.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
    }

    @Override
    public void recreateStructures(int p_82695_1_, int p_82695_2_) {
        // this.villageTest.func_151539_a(this, this.worldObj, par1, par2, (Block[]) null);
    }

}
