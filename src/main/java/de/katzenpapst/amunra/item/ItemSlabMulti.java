package de.katzenpapst.amunra.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.block.BlockDoubleslabMeta;
import de.katzenpapst.amunra.block.BlockSlabMeta;

public class ItemSlabMulti extends ItemBlockMulti {

    private final boolean isDoubleSlab;

    protected final Block singleSlab;
    protected final Block doubleSlab;

    /**
     *
     * @param name
     * @param singleSlab
     * @param doubleSlab
     */
    public ItemSlabMulti(final Block block, final BlockSlabMeta singleSlab, final BlockDoubleslabMeta doubleSlab) {
        super(block);

        this.singleSlab = singleSlab;
        this.doubleSlab = doubleSlab;
        this.isDoubleSlab = block == doubleSlab;
    }

    protected boolean placeDoubleSlab(final World world, final int x, final int y, final int z, final int meta) {
        return world.setBlock(x, y, z, doubleSlab, meta, 3);
    }

    protected void combine(final World world, final ItemStack stack, final int x, final int y, final int z, final int meta) {
        if (world.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(world, x, y, z))
                && placeDoubleSlab(world, x, y, z, meta)) {
            world.playSoundEffect(
                    (double) ((float) x + 0.5F),
                    (double) ((float) y + 0.5F),
                    (double) ((float) z + 0.5F),
                    this.doubleSlab.stepSound.func_150496_b(),
                    (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F,
                    this.doubleSlab.stepSound.getPitch() * 0.8F);
            --stack.stackSize;
        }
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side,
            final float hitX, final float hitY, final float hitZ) {
        if (this.isDoubleSlab) {
            return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        }
        if (stack.stackSize == 0) {
            return false;
        } else if (!player.canPlayerEdit(x, y, z, side, stack)) {
            return false;
        } else {
            final Block block = world.getBlock(x, y, z);
            final int worldMeta = world.getBlockMetadata(x, y, z);
            final int worldDistinctionMeta = worldMeta & 7;
            final boolean isHighestBitSet = (worldMeta & 8) != 0; // I think the meaning is: isSlabOnTop

            if ((side == 1 && !isHighestBitSet || side == 0 && isHighestBitSet) && block == this.singleSlab
                    && worldDistinctionMeta == stack.getItemDamage()) {
                // we are rightclicking on a slab with which we can merge
                this.combine(world, stack, x, y, z, worldDistinctionMeta);

                return true;
            } else {
                return this.tryCombiningWithSide(stack, player, world, x, y, z, side) ? true
                        : super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
            }
        }
    }

    /**
     * No idea what this actually is, but it helps slab placement
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean func_150936_a(final World world, int x, int y, int z, final int side, final EntityPlayer player, final ItemStack stack) {
        final int xNew = x;
        final int yNew = y;
        final int zNew = z;
        final Block block = world.getBlock(x, y, z);
        final int meta = world.getBlockMetadata(x, y, z);
        int distinctionMeta = meta & 7;
        final boolean isUpperSlab = (meta & 8) != 0;

        if ((side == 1 && !isUpperSlab || side == 0 && isUpperSlab) && block == this.singleSlab
                && distinctionMeta == stack.getItemDamage()) {
            return true;
        }
        switch (side) {
            case 0:
                --y;
                break;
            case 1:
                ++y;
                break;
            case 2:
                --z;
                break;
            case 3:
                ++z;
                break;
            case 4:
                --x;
                break;
            case 5:
                ++x;
                break;
        }

        final Block newBlock = world.getBlock(x, y, z);
        final int newMeta = world.getBlockMetadata(x, y, z);
        distinctionMeta = newMeta & 7;
        return newBlock == this.singleSlab && distinctionMeta == stack.getItemDamage() ? true
                : super.func_150936_a(world, xNew, yNew, zNew, side, player, stack);
    }

    private boolean tryCombiningWithSide(final ItemStack stack, final EntityPlayer player, final World world, int x, int y, int z,
            final int side) {
        switch (side) {
            case 0:
                --y;
                break;
            case 1:
                ++y;
                break;
            case 2:
                --z;
                break;
            case 3:
                ++z;
                break;
            case 4:
                --x;
                break;
            case 5:
                ++x;
                break;
        }

        final Block block = world.getBlock(x, y, z);
        final int worldMeta = world.getBlockMetadata(x, y, z);
        final int worldDistinctionMeta = worldMeta & 7;

        if (block == this.singleSlab && worldDistinctionMeta == stack.getItemDamage()) {
            this.combine(world, stack, x, y, z, worldDistinctionMeta);

            return true;
        }
        return false;
    }

}
