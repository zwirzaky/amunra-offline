package de.katzenpapst.amunra.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import micdoodle8.mods.galacticraft.api.vector.Vector3;

public class ARSidedProxy {

    public enum ParticleType {
        PT_MOTHERSHIP_JET_FLAME,
        PT_MOTHERSHIP_ION_FLAME,
        PT_GRAVITY_DUST
    }

    public void preInit(final FMLPreInitializationEvent event) {

    }

    public void init(final FMLInitializationEvent event) {}

    public void postInit(final FMLPostInitializationEvent event) {

    }

    public void spawnParticles(final ParticleType type, final World world, final Vector3 pos, final Vector3 motion) {
        // noop
    }

    public void playTileEntitySound(final TileEntity tile, final ResourceLocation resource) {
        // noop
    }

    /**
     * Doing this because EntityPlayerSP doesn't exist serverside
     * 
     * @param player
     */
    public void handlePlayerArtificalGravity(final EntityPlayer player, final double gravity) {
        // noop on server
    }

    public boolean doCancelGravityEvent(final EntityPlayer player) {
        return false;
    }
}
