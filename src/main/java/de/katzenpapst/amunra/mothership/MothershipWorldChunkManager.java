package de.katzenpapst.amunra.mothership;

import net.minecraft.world.biome.BiomeGenBase;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldChunkManagerSpace;
import micdoodle8.mods.galacticraft.core.world.gen.BiomeGenBaseOrbit;

public class MothershipWorldChunkManager extends WorldChunkManagerSpace {

    @Override
    public BiomeGenBase getBiome() {
        return BiomeGenBaseOrbit.space;
    }
}
