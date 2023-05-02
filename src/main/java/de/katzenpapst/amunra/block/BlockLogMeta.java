package de.katzenpapst.amunra.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class BlockLogMeta extends BlockBasicMeta {

    public BlockLogMeta(final String name, final Material mat) {
        super(name, mat, 4); // only 4 subblocks of wood are possible
    }

    @Override
    public BlockMetaPair addSubBlock(final int meta, final SubBlock sb) {
        if (!(sb instanceof SubBlockWood)) {
            throw new IllegalArgumentException("BlockWoodMulti can only accept SubBlockWood");
        }
        return super.addSubBlock(meta, sb);
    }

    @Override
    public SubBlock getSubBlock(final int meta) {
        return subBlocksArray[meta & 3]; // use only the first 2 bits, the rest is rotation
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int side, final int meta) {
        // /*Face 0 (Bottom Face) Face 1 (Top Face) Face 2 (Northern Face) Face 3 (Southern Face) Face 4 (Western Face)
        // Face 5 (Eastern Face)*/
        final int rotationMeta = (meta & 12) >> 2;

        return getSubBlock(meta).getIcon(side, rotationMeta);
    }

    @Override
    public int damageDropped(final int meta) {
        return super.damageDropped(meta & 3);
    }

    @Override
    public boolean isWood(final IBlockAccess world, final int x, final int y, final int z) {
        return true;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return 31; // ..?
    }

    @Override
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        if (getSubBlock(meta) != null) {
            return new ItemStack(Item.getItemFromBlock(this), 1, getDistinctionMeta(meta));
        }

        return super.getPickBlock(target, world, x, y, z);
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    @Override
    public int onBlockPlaced(final World world, final int x, final int y, final int z, final int side, final float hitX, final float hitY, final float hitZ,
            final int metadata) {
        final int actualMeta = metadata & 3;
        byte rotationalMeta = 0;

        switch (side) {
            case 0:
            case 1:
                rotationalMeta = 0;
                break;
            case 2:
            case 3:
                rotationalMeta = 8;
                break;
            case 4:
            case 5:
                rotationalMeta = 4;
        }

        return actualMeta | rotationalMeta;
    }

    @Override
    public boolean canSustainLeaves(final IBlockAccess world, final int x, final int y, final int z) {
        return true;
    }

}
