package de.katzenpapst.amunra.block.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import de.katzenpapst.amunra.block.SubBlockMachine;
import de.katzenpapst.amunra.mothership.MothershipWorldProvider;

public abstract class AbstractBlockMothershipRestricted extends SubBlockMachine {

    public AbstractBlockMothershipRestricted(final String name, final String texture) {
        super(name, texture);
        // TODO Auto-generated constructor stub
    }

    public AbstractBlockMothershipRestricted(final String name, final String texture, final String tool, final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
        // TODO Auto-generated constructor stub
    }

    public AbstractBlockMothershipRestricted(final String name, final String texture, final String tool, final int harvestLevel, final float hardness,
            final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onMachineActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX,
            final float hitY, final float hitZ) {
        if (world.provider instanceof MothershipWorldProvider) {

            if (((MothershipWorldProvider) world.provider).isPlayerUsagePermitted(entityPlayer)) {
                openGui(world, x, y, z, entityPlayer);

                return true;
            }
            if (world.isRemote) {
                entityPlayer.addChatMessage(new ChatComponentTranslation("gui.message.mothership.chat.wrongUser"));
            }
            return false;
        }
        if (world.isRemote) {
            entityPlayer.addChatMessage(new ChatComponentTranslation("gui.message.mothership.chat.notOnShip"));
        }

        return false;
    }

    protected abstract void openGui(World world, int x, int y, int z, EntityPlayer entityPlayer);

}
