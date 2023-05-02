package de.katzenpapst.amunra.block.machine.mothershipEngine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.block.BlockMachineMetaDummyRender;
import de.katzenpapst.amunra.block.SubBlock;

public class BlockMothershipBoosterMeta extends BlockMachineMetaDummyRender {

    public BlockMothershipBoosterMeta(final String name, final Material material) {
        super(name, material);
    }

    @Override
    public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase entityLiving, final ItemStack itemStack) {
        final int metadata = world.getBlockMetadata(x, y, z);
        final SubBlock sb = this.getSubBlock(metadata);
        if (sb != null) {
            sb.onBlockPlacedBy(world, x, y, z, entityLiving, itemStack);
        }
    }

    @Override
    public boolean onUseWrench(final World par1World, final int x, final int y, final int z, final EntityPlayer par5EntityPlayer, final int side,
            final float hitX, final float hitY, final float hitZ) {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isBlockNormalCube() {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return AmunRa.msBoosterRendererId;
    }

    @Override
    public void dropEntireInventory(final World world, final int x, final int y, final int z, final Block block, final int par6) {
        return; // NOOP
    }

}
