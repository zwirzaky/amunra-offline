package de.katzenpapst.amunra.proxy;

import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import de.katzenpapst.amunra.AmunRa;

public class ServerProxy extends ARSidedProxy {

    @Override
    public void init(final FMLInitializationEvent event) {
        try {
            final MinecraftServer s = MinecraftServer.getServer();
            if (s.isDedicatedServer() && !s.isServerInOnlineMode() && AmunRa.config.mothershipUserMatchUUID) {
                AmunRa.LOGGER.fatal("Server is running in offline mode. This is not supported.");
            }
        } catch (NullPointerException e) {
            AmunRa.LOGGER.error(
                    "Could not detect whenever server is in online mode. Things might break if the server is in offline mode.");
        }
    }

}
