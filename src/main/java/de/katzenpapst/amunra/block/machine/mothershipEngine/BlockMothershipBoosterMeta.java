package de.katzenpapst.amunra.block.machine.mothershipEngine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import de.katzenpapst.amunra.block.BlockMachineMetaDummyRender;
import de.katzenpapst.amunra.block.SubBlock;

public class BlockMothershipBoosterMeta extends BlockMachineMetaDummyRender {

    public BlockMothershipBoosterMeta(final String name, final Material material) {
        super(name, material);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        final int metadata = worldIn.getBlockMetadata(x, y, z);
        final SubBlock sb = this.getSubBlock(metadata);
        if (sb != null) {
            sb.onBlockPlacedBy(worldIn, x, y, z, placer, itemIn);
        }
    }

    @Override
    public boolean onUseWrench(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX,
            float hitY, float hitZ) {
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
    public void dropEntireInventory(final World world, final int x, final int y, final int z, final Block block,
            final int par6) {}

}
