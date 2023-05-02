package de.katzenpapst.amunra.block.machine.mothershipEngine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.GuiIds;
import de.katzenpapst.amunra.tile.TileEntityMothershipEngineBooster;
import de.katzenpapst.amunra.tile.TileEntityMothershipEngineBoosterIon;
import de.katzenpapst.amunra.vec.Vector3int;

public class MothershipEngineBoosterIon extends MothershipEngineBoosterBase {

    public MothershipEngineBoosterIon(final String name, final String texture, final String activeTexture) {
        super(name, texture, activeTexture);
        // TODO Auto-generated constructor stub
    }

    public MothershipEngineBoosterIon(final String name, final String texture, final String activeTexture,
            final String tool, final int harvestLevel) {
        super(name, texture, activeTexture, tool, harvestLevel);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onMachineActivated(final World world, final int x, final int y, final int z,
            final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ) {
        final TileEntity leTile = world.getTileEntity(x, y, z);
        if (leTile == null || !(leTile instanceof TileEntityMothershipEngineBooster tile)) {
            return false;
        }
        if (tile.hasMaster()) {
            final Vector3int pos = tile.getMasterPosition();

            entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_MS_ION_ENGINE, world, pos.x, pos.y, pos.z);
            return true;
        }
        return false;
    }

    public MothershipEngineBoosterIon(final String name, final String texture, final String activeTexture,
            final String tool, final int harvestLevel, final float hardness, final float resistance) {
        super(name, texture, activeTexture, tool, harvestLevel, hardness, resistance);
        // TODO Auto-generated constructor stub
    }

    @Override
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new TileEntityMothershipEngineBoosterIon();
    }

    @Override
    public ResourceLocation getBoosterTexture() {
        return new ResourceLocation(AmunRa.ASSETPREFIX, "textures/blocks/jet-base-ion.png");
    }

}
