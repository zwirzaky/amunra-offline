package de.katzenpapst.amunra.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetaNonOpaqueInternal extends BlockBasicMeta {

    public BlockMetaNonOpaqueInternal(final String name, final Material mat, final int numSubBlocks) {
        super(name, mat, numSubBlocks);
    }

    public BlockMetaNonOpaqueInternal(final String name, final Material mat) {
        super(name, mat);
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
    public CreativeTabs getCreativeTabToDisplayOn() {
        return null;
    }
}
