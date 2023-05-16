package de.katzenpapst.amunra.world.neper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.IChunkProvider;

import de.katzenpapst.amunra.world.AmunraChunkProvider;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;

public class NeperChunkProvider extends AmunraChunkProvider {

    protected final BlockMetaPair dirtBlock = new BlockMetaPair(Blocks.dirt, (byte) 0);
    protected final BlockMetaPair grassBlock = new BlockMetaPair(Blocks.grass, (byte) 0);
    protected final BlockMetaPair stoneBlock = new BlockMetaPair(Blocks.stone, (byte) 0);

    public NeperChunkProvider(final World world, final long seed, final boolean mapFeaturesEnabled) {
        super(world, seed, mapFeaturesEnabled);
    }

    @Override
    protected BiomeDecoratorSpace getBiomeGenerator() {
        return new NeperBiomeDecorator();
    }


    @Override
    protected SpawnListEntry[] getCreatures() {
        // SpawnListEntry villager = new SpawnListEntry(EntityAlienVillager.class, 1, 0, 2);
        // return new SpawnListEntry[]{villager};

        return new SpawnListEntry[] { new SpawnListEntry(EntityCow.class, 25, 2, 4),
                new SpawnListEntry(EntityHorse.class, 1, 1, 2), new SpawnListEntry(EntitySheep.class, 1, 2, 4),
                new SpawnListEntry(EntityPig.class, 25, 2, 4), new SpawnListEntry(EntityChicken.class, 10, 2, 4) };
    }

    @Override
    protected BlockMetaPair getDirtBlock() {
        return this.dirtBlock;
    }

    @Override
    protected BlockMetaPair getGrassBlock() {
        return this.grassBlock;
    }

    @Override
    protected BlockMetaPair getStoneBlock() {
        return this.stoneBlock;
    }

    @Override
    public double getHeightModifier() {
        return 10;
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

    @Override
    protected List<MapGenBaseMeta> getWorldGenerators() {
        return new ArrayList<>();
    }

    @Override
    public void onChunkProvide(final int cX, final int cZ, final Block[] blocks, final byte[] metadata) {}

    @Override
    public void onPopulate(final IChunkProvider provider, final int cX, final int cZ) {}

    @Override
    public boolean chunkExists(int p_73149_1_, int p_73149_2_) {
        return false;
    }

}
