package de.katzenpapst.amunra.block;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.tile.TileEntityBossDungeonSpawner;

public class SubBlockBossSpawner extends SubBlock {

    public SubBlockBossSpawner(final String name, final String texture) {
        super(name, texture);
    }

    @Override
    public boolean hasTileEntity(final int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new TileEntityBossDungeonSpawner();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int wx, final int wy, final int wz) {
        return null;
    }

    @Override
    public boolean canHarvestBlock(final EntityPlayer player, final int meta) {
        return false;
    }

    @Override
    public Item getItemDropped(final int meta, final Random random, final int par3) {
        return null;
    }

    @Override
    public int quantityDropped(final int meta, final int fortune, final Random random) {
        return 0;
    }

    @Override
    public boolean canSilkHarvest(final World world, final EntityPlayer player, final int x, final int y, final int z, final int metadata) {
        return false;
    }

    @Override
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World world, final int x, final int y, final int z) {
        return AxisAlignedBB.getBoundingBox(x + 0.0D, y + 0.0D, z + 0.0D, x + 0.0D, y + 0.0D, z + 0.0D);
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean dropsSelf() {
        return false;
    }
}
