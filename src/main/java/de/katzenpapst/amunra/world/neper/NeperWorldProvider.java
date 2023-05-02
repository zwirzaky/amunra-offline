package de.katzenpapst.amunra.world.neper;

import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.world.AmunraWorldChunkManager;
import de.katzenpapst.amunra.world.AmunraWorldProvider;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;

public class NeperWorldProvider extends AmunraWorldProvider {

    @Override
    public double getMeteorFrequency() {
        return 7;
    }

    @Override
    public float getSoundVolReductionAmount() {
        return 1;
    }

    @Override
    public float getThermalLevelModifier() {
        return 0;
    }

    @Override
    public float getWindLevel() {
        return 0;
    }

    @Override
    public CelestialBody getCelestialBody() {
        return AmunRa.instance.moonNeper;
    }

    @Override
    public double getYCoordinateToTeleport() {
        return 800;
    }

    @Override
    public Vector3 getFogColor() {
        return new Vector3(0.7529412, 0.84705883, 1.0);
    }

    @Override
    public Vector3 getSkyColor() {
        return new Vector3(0.5, 0.75, 1);
    }

    @Override
    public boolean canRainOrSnow() {
        return false;
    }

    @Override
    public boolean hasSunset() {
        return true;
    }

    @Override
    public long getDayLength() {
        return 18000L;
    }

    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return NeperChunkProvider.class;
    }

    @Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
        return AmunraWorldChunkManager.class;
    }

    @Override
    protected float getRelativeGravity() {
        return 1F;
    }

}
