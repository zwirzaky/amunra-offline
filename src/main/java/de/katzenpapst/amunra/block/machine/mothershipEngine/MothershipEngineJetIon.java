package de.katzenpapst.amunra.block.machine.mothershipEngine;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.GuiIds;
import de.katzenpapst.amunra.item.ARItems;
import de.katzenpapst.amunra.item.ItemDamagePair;
import de.katzenpapst.amunra.tile.TileEntityMothershipEngineAbstract;
import de.katzenpapst.amunra.tile.TileEntityMothershipEngineIon;

public class MothershipEngineJetIon extends MothershipEngineJetBase {

    protected ItemDamagePair item = null;

    public MothershipEngineJetIon(final String name, final String texture, final String iconTexture) {
        super(name, texture, iconTexture);
    }

    @Override
    protected TileEntityMothershipEngineIon getMyTileEntity(final World world, final int x, final int y, final int z) {
        final TileEntity t = world.getTileEntity(x, y, z);
        if (t == null || !(t instanceof TileEntityMothershipEngineIon)) {
            return null;
        }
        return (TileEntityMothershipEngineIon) t;
    }

    @Override
    public boolean onMachineActivated(final World world, final int x, final int y, final int z,
            final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ) {
        // do the isRemote thing here, too?
        entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_MS_ION_ENGINE, world, x, y, z);
        return true;
        // return false;
    }

    @Override
    public boolean hasTileEntity(final int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new TileEntityMothershipEngineIon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return AmunRa.dummyRendererId;
    }

    @Override
    protected ItemDamagePair getItem() {
        if (this.item == null) {
            this.item = ARItems.jetItemIon;
        }
        return this.item;
    }

    @Override
    public Item getItem(final World worldIn, final int x, final int y, final int z) {
        return this.item.getItem();
    }

    @Override
    public Item getItemDropped(final int meta, final Random random, final int fortune) {
        /**
         * Returns whether or not this bed block is the head of the bed.
         */
        return this.item.getItem();
    }

    @Override
    public int damageDropped(final int meta) {
        return this.item.getDamage();
    }

    @Override
    public void onNeighborBlockChange(final World world, final int x, final int y, final int z, final Block block) {
        final TileEntity leTile = world.getTileEntity(x, y, z);
        if (leTile instanceof TileEntityMothershipEngineAbstract) {
            ((TileEntityMothershipEngineAbstract) leTile).scheduleUpdate();
            // world.markBlockForUpdate(x, y, z);
        }
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     *
     */
    @Override
    public int onBlockPlaced(final World w, final int x, final int y, final int z, final int side, final float hitX,
            final float hitY, final float hitZ, final int meta) {
        return meta;
    }

    @Override
    public boolean onUseWrench(final World world, final int x, final int y, final int z,
            final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ) {
        // TODO rotate the tile entity
        return false;
    }

}
