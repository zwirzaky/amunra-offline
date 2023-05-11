package de.katzenpapst.amunra.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import de.katzenpapst.amunra.mothership.Mothership;
import de.katzenpapst.amunra.mothership.MothershipWorldProvider;

public class CommandMothershipForceArrive extends CommandBase {

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandName() {
        return "mothership_force_arrival";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + this.getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (sender.getEntityWorld().provider instanceof MothershipWorldProvider shipProvider) {
            if (!((Mothership) shipProvider.getCelestialBody()).isInTransit()) {
                sender.addChatMessage(new ChatComponentText("Mothership not in transit"));
            } else {
                ((Mothership) shipProvider.getCelestialBody()).forceArrival();
            }
        } else {
            sender.addChatMessage(new ChatComponentText("Not on a mothership"));
        }
    }

}
