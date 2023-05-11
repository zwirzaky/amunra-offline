package de.katzenpapst.amunra.world.anubis;

import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.world.AmunraWorldChunkManager;
import de.katzenpapst.amunra.world.AmunraWorldProvider;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;

public class AnubisWorldProvider extends AmunraWorldProvider {

    @Override
    public double getMeteorFrequency() {
        return 2;
    }

    @Override
    public float getSoundVolReductionAmount() {
        return 20;
    }

    @Override
    public float getThermalLevelModifier() {
        return -10;
    }

    @Override
    public float getWindLevel() {
        return 0;
    }

    @Override
    public CelestialBody getCelestialBody() {
        return AmunRa.instance.planetAnubis;
    }

    @Override
    public double getYCoordinateToTeleport() {
        return 800;
    }

    @Override
    public Vector3 getFogColor() {
        return new Vector3(0, 0, 0);
    }

    @Override
    public Vector3 getSkyColor() {
        return new Vector3(0, 0, 0);
    }

    @Override
    public boolean canRainOrSnow() {
        return false;
    }

    @Override
    public boolean hasSunset() {
        return false;
    }

    @Override
    public long getDayLength() {
        return 32000L;
    }

    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return AnubisChunkProvider.class;
    }

    @Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
        return AmunraWorldChunkManager.class;
    }

    @Override
    protected float getRelativeGravity() {
        return 0.25F;
    }

    @Override
    public boolean isSkyColored() {
        return false;
    }

}
