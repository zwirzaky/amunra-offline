package de.katzenpapst.amunra.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.tile.TileEntityShuttleDockFake;
import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.tile.TileEntityMulti;

public class FakeBlock extends SubBlock implements IPartialSealableBlock, IMassiveBlock {

    public FakeBlock(final String name, final String texture) {
        super(name, texture);
        this.setHardness(1.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setResistance(1000000000000000.0F);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canDropFromExplosion(final Explosion par1Explosion) {
        return false;
    }

    @Override
    public boolean hasTileEntity(final int metadata) {
        return true;
    }

    @Override
    public float getBlockHardness(final World par1World, final int par2, final int par3, final int par4) {
        final TileEntity tileEntity = par1World.getTileEntity(par2, par3, par4);

        if (tileEntity instanceof TileEntityMulti) {
            final BlockVec3 mainBlockPosition = ((TileEntityMulti) tileEntity).mainBlockPosition;

            if (mainBlockPosition != null) {
                return mainBlockPosition.getBlock(par1World).getBlockHardness(par1World, par2, par3, par4);
            }
        }

        return this.blockHardness;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs getCreativeTabToDisplayOn() {
        return null;
    }

    @Override
    public void breakBlock(final World world, final int x, final int y, final int z, final Block par5, final int par6) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityMulti) {
            ((TileEntityMulti) tileEntity).onBlockRemoval();
        }

        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    public boolean onBlockActivated(final World par1World, final int x, final int y, final int z, final EntityPlayer par5EntityPlayer, final int par6,
            final float par7, final float par8, final float par9) {
        final TileEntityMulti tileEntity = (TileEntityMulti) par1World.getTileEntity(x, y, z);
        return tileEntity.onBlockActivated(par1World, x, y, z, par5EntityPlayer);
    }

    @Override
    public int quantityDropped(final Random par1Random) {
        return 0;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createTileEntity(final World var1, final int meta) {
        return new TileEntityShuttleDockFake();
    }

    @Override
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z, final EntityPlayer player) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityMulti) {
            final BlockVec3 mainBlockPosition = ((TileEntityMulti) tileEntity).mainBlockPosition;

            if (mainBlockPosition != null) {
                final Block mainBlockID = world.getBlock(mainBlockPosition.x, mainBlockPosition.y, mainBlockPosition.z);

                if (Blocks.air != mainBlockID) {
                    return mainBlockID.getPickBlock(
                            target,
                            world,
                            mainBlockPosition.x,
                            mainBlockPosition.y,
                            mainBlockPosition.z,
                            player);
                }
            }
        }

        return null;
    }

    @Override
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
        return this.getPickBlock(target, world, x, y, z, null);
    }

    @Override
    public boolean isSealed(final World world, final int x, final int y, final int z, final ForgeDirection direction) {
        return true;
    }

    @Override
    public float getMass(final World w, final int x, final int y, final int z, final int meta) {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(final World worldObj, final MovingObjectPosition target, final EffectRenderer effectRenderer) {
        final TileEntity tileEntity = worldObj.getTileEntity(target.blockX, target.blockY, target.blockZ);

        if (tileEntity instanceof TileEntityMulti) {
            final BlockVec3 mainBlockPosition = ((TileEntityMulti) tileEntity).mainBlockPosition;

            if (mainBlockPosition != null) {
                effectRenderer
                        .addBlockHitEffects(mainBlockPosition.x, mainBlockPosition.y, mainBlockPosition.z, target);
            }
        }

        return super.addHitEffects(worldObj, target, effectRenderer);
    }

}
