package de.katzenpapst.amunra.world.horus;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.IChunkProvider;

import de.katzenpapst.amunra.block.ARBlocks;
import de.katzenpapst.amunra.world.AmunraChunkProvider;
import de.katzenpapst.amunra.world.mapgen.pyramid.BossRoom;
import de.katzenpapst.amunra.world.mapgen.pyramid.ChestRoom;
import de.katzenpapst.amunra.world.mapgen.pyramid.PitRoom;
import de.katzenpapst.amunra.world.mapgen.pyramid.PyramidGenerator;
import de.katzenpapst.amunra.world.mapgen.pyramid.PyramidRoom;
import de.katzenpapst.amunra.world.mapgen.volcano.VolcanoGenerator;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;

public class HorusChunkProvider extends AmunraChunkProvider {

    protected final BlockMetaPair stoneBlock = new BlockMetaPair(Blocks.obsidian, (byte) 0);
    protected PyramidGenerator pyramid = new PyramidGenerator();
    private final VolcanoGenerator volcanoGen;

    public HorusChunkProvider(final World world, final long seed, final boolean mapFeaturesEnabled) {
        super(world, seed, mapFeaturesEnabled);
        this.pyramid.setFillMaterial(ARBlocks.blockBasaltBrick);
        this.pyramid.setFloorMaterial(ARBlocks.blockSmoothBasalt);
        this.pyramid.setWallMaterial(ARBlocks.blockObsidianBrick);
        this.pyramid.addComponentType(ChestRoom.class, 0.25F);
        this.pyramid.addComponentType(PitRoom.class, 0.25F);
        this.pyramid.addComponentType(PyramidRoom.class, 0.5F);
        this.pyramid.addMainRoomType(BossRoom.class, 1.0F);

        this.volcanoGen = new VolcanoGenerator(
                new BlockMetaPair(Blocks.lava, (byte) 0),
                this.stoneBlock,
                this.stoneBlock,
                15,
                true);
    }

    @Override
    protected BiomeDecoratorSpace getBiomeGenerator() {
        return new HorusBiomeDecorator();
    }

    @Override
    protected BiomeGenBase[] getBiomesForGeneration() {
        return new BiomeGenBase[] { BiomeGenBase.desert };
    }

    @Override
    protected int getSeaLevel() {
        return 64;
    }

    @Override
    protected List<MapGenBaseMeta> getWorldGenerators() {
        return Arrays.asList(this.volcanoGen, this.pyramid);
    }

    @Override
    protected SpawnListEntry[] getMonsters() {
        final SpawnListEntry skele = new SpawnListEntry(EntityEvolvedSkeleton.class, 100, 4, 4);
        final SpawnListEntry creeper = new SpawnListEntry(EntityEvolvedCreeper.class, 100, 4, 4);
        final SpawnListEntry zombie = new SpawnListEntry(EntityEvolvedZombie.class, 100, 4, 4);

        return new SpawnListEntry[] { skele, creeper, zombie };
    }

    @Override
    protected SpawnListEntry[] getCreatures() {
        return new SpawnListEntry[] {};
    }

    @Override
    protected BlockMetaPair getGrassBlock() {
        return ARBlocks.blockObsidiSand;
    }

    @Override
    protected BlockMetaPair getDirtBlock() {
        return ARBlocks.blockObsidiGravel;
    }

    @Override
    protected BlockMetaPair getStoneBlock() {
        return this.stoneBlock;
    }

    @Override
    public double getHeightModifier() {
        return 20;
    }

    @Override
    public double getSmallFeatureHeightModifier() {
        return 40;
    }

    @Override
    public double getMountainHeightModifier() {
        return 60;
    }

    @Override
    public double getValleyHeightModifier() {
        return 60;
    }

    @Override
    public void onChunkProvide(final int cX, final int cZ, final Block[] blocks, final byte[] metadata) {}

    @Override
    public void onPopulate(final IChunkProvider provider, final int cX, final int cZ) {}

    @Override
    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {
        super.populate(p_73153_1_, p_73153_2_, p_73153_3_);

        this.pyramid.populate(this, this.worldObj, p_73153_2_, p_73153_3_);
        // this.pyramid.populate(this, worldObj, chunkX, chunkZ);

        // this.villageTest.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
    }

}
