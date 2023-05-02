package de.katzenpapst.amunra.block.machine.mothershipEngine;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.GuiIds;
import de.katzenpapst.amunra.block.machine.AbstractBlockMothershipRestricted;
import de.katzenpapst.amunra.item.ItemDamagePair;
import de.katzenpapst.amunra.tile.TileEntityMothershipEngineAbstract;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public abstract class MothershipEngineJetBase extends AbstractBlockMothershipRestricted {

    protected String iconTexture;

    public MothershipEngineJetBase(final String name, final String texture, final String iconTexture) {
        super(name, texture);

        this.iconTexture = iconTexture;
    }

    @Override
    public String getItemIconName() {
        return iconTexture;
    }

    /**
     * Not sure why I have to do this here, but...
     */
    abstract protected ItemDamagePair getItem();

    protected TileEntityMothershipEngineAbstract getMyTileEntity(final World world, final int x, final int y, final int z) {
        final TileEntity t = world.getTileEntity(x, y, z);
        if (t == null || !(t instanceof TileEntityMothershipEngineAbstract)) {
            // TODO throw exception instead
            return null;
        }
        return (TileEntityMothershipEngineAbstract) t;
    }

    @Override
    public void onBlockPlacedBy(final World w, final int x, final int y, final int z, final EntityLivingBase user, final ItemStack stack) {}

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
    public int onBlockPlaced(final World w, final int x, final int y, final int z, final int side, final float hitX, final float hitY, final float hitZ, final int meta) {
        return meta;
    }

    @Override
    public boolean onUseWrench(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX,
            final float hitY, final float hitZ) {
        // TODO rotate the tile entity
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return AmunRa.dummyRendererId;
    }

    @Override
    public Item getItem(final World worldIn, final int x, final int y, final int z) {
        return this.getItem().getItem();
    }

    @Override
    public Item getItemDropped(final int meta, final Random random, final int fortune) {
        /**
         * Returns whether or not this bed block is the head of the bed.
         */
        return this.getItem().getItem();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    protected void openGui(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer) {
        entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_MS_ROCKET_ENGINE, world, x, y, z);
    }

    @Override
    public boolean hasTileEntity(final int metadata) {
        return true;
    }

    @Override
    public int damageDropped(final int meta) {
        return getItem().getDamage();
    }

    @Override
    public String getShiftDescription(final int meta) {
        return GCCoreUtil.translate("tile.mothershipEngineRocket.description");
    }

    @Override
    public boolean canBeMoved(final World world, final int x, final int y, final int z) {
        return !this.getMyTileEntity(world, x, y, z).isInUse();
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
