package de.katzenpapst.amunra.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public class SubBlockMachine extends SubBlock implements ItemBlockDesc.IBlockShiftDesc {

    public SubBlockMachine(final String name, final String texture) {
        super(name, texture);
        // TODO Auto-generated constructor stub
    }

    public SubBlockMachine(final String name, final String texture, final String tool, final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
        // TODO Auto-generated constructor stub
    }

    public SubBlockMachine(final String name, final String texture, final String tool, final int harvestLevel,
            final float hardness, final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getShiftDescription(final int meta) {
        return GCCoreUtil.translate("tile." + this.blockNameFU + ".description");
    }

    @Override
    public boolean showDescription(final int meta) {
        return true;
    }

    /**
     * Called when the machine is right clicked by the player
     *
     * @return True if something happens
     */
    public boolean onMachineActivated(final World world, final int x, final int y, final int z,
            final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ) {
        return false;
    }

    public boolean onSneakUseWrench(final World world, final int x, final int y, final int z,
            final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean onUseWrench(final World world, final int x, final int y, final int z,
            final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean onSneakMachineActivated(final World world, final int x, final int y, final int z,
            final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ) {
        // TODO Auto-generated method stub
        return false;
    }

}
