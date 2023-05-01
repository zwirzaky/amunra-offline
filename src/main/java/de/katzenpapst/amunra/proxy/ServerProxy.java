package de.katzenpapst.amunra.proxy;

import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import de.katzenpapst.amunra.AmunRa;

public class ServerProxy extends ARSidedProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        try {
            MinecraftServer s = MinecraftServer.getServer();
            if (s.isDedicatedServer() && !s.isServerInOnlineMode()) {
                AmunRa.LOGGER.fatal("Server is running in offline mode. This is not supported.");
                FMLCommonHandler.instance().exitJava(-10, false);
            }
        } catch (Exception e) {
            AmunRa.LOGGER.error(
                    "Could not detect whenever server is in online mode. Things might break if the server is in offline mode.");
        }
    }

}
