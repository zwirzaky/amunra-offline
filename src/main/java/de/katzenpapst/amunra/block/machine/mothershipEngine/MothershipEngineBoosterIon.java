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
        this.boosterTexture = new ResourceLocation(AmunRa.ASSETPREFIX, "textures/blocks/jet-base-ion.png");
    }

    public MothershipEngineBoosterIon(final String name, final String texture, final String activeTexture,
            final String tool, final int harvestLevel) {
        super(name, texture, activeTexture, tool, harvestLevel);
        this.boosterTexture = new ResourceLocation(AmunRa.ASSETPREFIX, "textures/blocks/jet-base-ion.png");
    }

    @Override
    public boolean onMachineActivated(final World world, final int x, final int y, final int z,
            final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ) {
        if (world.getTileEntity(x, y, z) instanceof TileEntityMothershipEngineBooster tileBooster
                && tileBooster.hasMaster()) {
            final Vector3int pos = tileBooster.getMasterPosition();
            entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_MS_ION_ENGINE, world, pos.x, pos.y, pos.z);
            return true;
        }
        return false;
    }

    public MothershipEngineBoosterIon(final String name, final String texture, final String activeTexture,
            final String tool, final int harvestLevel, final float hardness, final float resistance) {
        super(name, texture, activeTexture, tool, harvestLevel, hardness, resistance);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityMothershipEngineBoosterIon();
    }

}
