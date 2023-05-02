package de.katzenpapst.amunra.world.maahes;

import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.world.AmunraWorldChunkManager;
import de.katzenpapst.amunra.world.AmunraWorldProvider;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;

public class MaahesWorldProvider extends AmunraWorldProvider {

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
        return AmunRa.instance.moonMaahes;
    }

    @Override
    public double getYCoordinateToTeleport() {
        return 800;
    }

    @Override
    public Vector3 getFogColor() {
        return new Vector3(0.5569, 1, 0.851);
    }

    @Override
    public Vector3 getSkyColor() {
        return new Vector3(109.0F / 255.0F, 196.0F / 255.0F, 167.0F / 255.0F);
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
        return 16000L;
    }

    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return MaahesChunkProvider.class;
    }

    @Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
        return AmunraWorldChunkManager.class;
    }

    @Override
    protected float getRelativeGravity() {
        return 0.7F;
    }

}
