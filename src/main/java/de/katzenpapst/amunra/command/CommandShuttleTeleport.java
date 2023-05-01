package de.katzenpapst.amunra.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.entity.spaceship.EntityShuttle;
import micdoodle8.mods.galacticraft.api.entity.IRocketType;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;

public class CommandShuttleTeleport extends CommandBase {

    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + this.getCommandName() + " [<player>]";
    }

    @Override
    public String getCommandName() {
        return "shuttle_tp";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP playerBase = null;

        if (args.length < 2) {
            try {
                if (args.length == 1) {
                    playerBase = PlayerUtil.getPlayerBaseServerFromPlayerUsername(args[0], true);
                } else {
                    playerBase = PlayerUtil.getPlayerBaseServerFromPlayerUsername(sender.getCommandSenderName(), true);
                }

                if (playerBase != null) {
                    MinecraftServer server = MinecraftServer.getServer();
                    WorldServer worldserver = server
                            .worldServerForDimension(server.worldServers[0].provider.dimensionId);
                    ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();
                    GCPlayerStats stats = GCPlayerStats.get(playerBase);
                    stats.rocketStacks = new ItemStack[2];
                    stats.rocketType = IRocketType.EnumRocketType.DEFAULT.ordinal();
                    stats.rocketItem = GCItems.rocketTier1;
                    stats.fuelLevel = 1000;
                    stats.coordsTeleportedFromX = chunkcoordinates.posX;
                    stats.coordsTeleportedFromZ = chunkcoordinates.posZ;

                    try {
                        EntityShuttle.toCelestialSelection(playerBase, stats, Integer.MAX_VALUE, false);
                        // WorldUtil.toCelestialSelection(playerBase, stats, Integer.MAX_VALUE);
                    } catch (Exception e) {
                        AmunRa.LOGGER.error("Failed to open celestial selection", e);
                        throw e;
                    }

                    // VersionUtil.notifyAdmins(sender, this, "commands.dimensionteleport", new Object[] {
                    // String.valueOf(EnumColor.GREY + "[" + playerBase.getCommandSenderName()), "]" });
                } else {
                    throw new Exception("Could not find player with name: " + args[0]);
                }
            } catch (final Exception e) {
                throw new CommandException(e.getMessage());
            }
        } else {
            throw new WrongUsageException(
                    GCCoreUtil.translateWithFormat("commands.dimensiontp.tooMany", this.getCommandUsage(sender)));
        }
    }

}
