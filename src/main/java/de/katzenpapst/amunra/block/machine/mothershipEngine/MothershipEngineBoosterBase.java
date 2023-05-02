package de.katzenpapst.amunra.block.machine.mothershipEngine;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.GuiIds;
import de.katzenpapst.amunra.block.machine.AbstractBlockMothershipRestricted;
import de.katzenpapst.amunra.tile.TileEntityMothershipEngineAbstract;
import de.katzenpapst.amunra.tile.TileEntityMothershipEngineBooster;
import de.katzenpapst.amunra.vec.Vector3int;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public class MothershipEngineBoosterBase extends AbstractBlockMothershipRestricted {

    protected String activeTextureName;
    protected IIcon activeBlockIcon;

    public MothershipEngineBoosterBase(final String name, final String texture, final String activeTexture) {
        super(name, texture);
        activeTextureName = activeTexture;
    }

    public MothershipEngineBoosterBase(final String name, final String texture, final String activeTexture, final String tool,
            final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
        activeTextureName = activeTexture;
    }

    @Override
    public boolean onMachineActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX,
            final float hitY, final float hitZ) {
        final TileEntity leTile = world.getTileEntity(x, y, z);
        if (leTile == null || !(leTile instanceof TileEntityMothershipEngineBooster tile)) {
            return false;
        }
        if (tile.hasMaster()) {
            return super.onMachineActivated(world, x, y, z, entityPlayer, side, hitX, hitY, hitZ);
        }
        return false;
    }

    @Override
    protected void openGui(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer) {
        // try this
        if (world.isRemote) {
            return;
        }
        final TileEntity leTile = world.getTileEntity(x, y, z);
        if (leTile != null) {
            final TileEntityMothershipEngineBooster tile = (TileEntityMothershipEngineBooster) leTile;
            final Vector3int pos = tile.getMasterPosition();

            entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_MS_ROCKET_ENGINE, world, pos.x, pos.y, pos.z);
        }
    }

    @Override
    public boolean hasTileEntity(final int metadata) {
        return true;
    }

    public MothershipEngineBoosterBase(final String name, final String texture, final String activeTexture, final String tool, final int harvestLevel,
            final float hardness, final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
        activeTextureName = activeTexture;
    }

    // TileEntityMothershipEngineBooster.java
    @Override
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new TileEntityMothershipEngineBooster();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister reg) {
        super.registerBlockIcons(reg);
        this.activeBlockIcon = reg.registerIcon(this.activeTextureName);
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        if (side <= 1) {
            return this.blockIcon;
        }
        return activeBlockIcon;
    }

    @Override
    public void onNeighborBlockChange(final World w, final int x, final int y, final int z, final Block block) {
        // these are MY coords
        final TileEntity leTile = w.getTileEntity(x, y, z);
        if (leTile == null) return;

        if (leTile instanceof TileEntityMothershipEngineAbstract) {
            ((TileEntityMothershipEngineAbstract) leTile).scheduleUpdate();
        } else if (leTile instanceof TileEntityMothershipEngineBooster) {
            ((TileEntityMothershipEngineBooster) leTile).updateMaster(false);
            // attept to continue the process
            // find next
            final Vector3int pos = ((TileEntityMothershipEngineBooster) leTile).getPossibleNextBooster();
            if (pos != null) {
                w.notifyBlockOfNeighborChange(
                        pos.x,
                        pos.y,
                        pos.z,
                        ((TileEntityMothershipEngineBooster) leTile).blockType);
            }
        }
    }

    @Override
    public String getShiftDescription(final int meta) {
        return GCCoreUtil.translate("tile.mothershipEngineRocket.description");
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isBlockNormalCube() {
        return true;
    }

    @Override
    public boolean isNormalCube() {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return AmunRa.msBoosterRendererId;
    }

    public ResourceLocation getBoosterTexture() {
        return new ResourceLocation(AmunRa.ASSETPREFIX, "textures/blocks/jet-base.png");
    }

    @Override
    public boolean canBeMoved(final World world, final int x, final int y, final int z) {
        final TileEntity te = world.getTileEntity(x, y, z);
        if (te == null || !(te instanceof TileEntityMothershipEngineBooster)) {
            return true;
        }
        final TileEntityMothershipEngineAbstract master = ((TileEntityMothershipEngineBooster) te).getMasterTile();
        return master == null || !master.isInUse();
    }

    @Override
    public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z, final boolean willHarvest) {
        return removedByPlayer(world, player, x, y, z);
    }

    @Override
    @Deprecated
    public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z) {
        if (this.canBeMoved(world, x, y, z)) {
            return super.removedByPlayer(world, player, x, y, z);
        }
        return false;
    }

}
