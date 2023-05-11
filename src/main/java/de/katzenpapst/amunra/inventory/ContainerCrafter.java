package de.katzenpapst.amunra.inventory;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import de.katzenpapst.amunra.block.ARBlocks;
import de.katzenpapst.amunra.item.ARItems;
import de.katzenpapst.amunra.item.ItemNanotool;

public class ContainerCrafter extends ContainerWorkbench {

    protected World worldFU;
    protected int posXFU;
    protected int posYFU;
    protected int posZFU;

    public ContainerCrafter(final InventoryPlayer playerInv, final World world, final int x, final int y, final int z) {
        super(playerInv, world, x, y, z);
        this.worldFU = world;
        this.posXFU = x;
        this.posYFU = y;
        this.posZFU = z;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        // either using a crafting block, or a crafting tool
        final Block b = player.worldObj.getBlock(this.posXFU, this.posYFU, this.posZFU);
        final int meta = player.worldObj.getBlockMetadata(this.posXFU, this.posYFU, this.posZFU);

        if (ARBlocks.blockWorkbench.getBlock() == b && ARBlocks.blockWorkbench.getMetadata() == meta) {
            return player.getDistanceSq(this.posXFU + 0.5D, this.posYFU + 0.5D, this.posZFU + 0.5D) <= 64.0D;
        }

        // not the block, check for item
        final ItemStack stack = player.inventory.getCurrentItem();

        if (stack != null && stack.getItem() == ARItems.nanotool) {
            return ARItems.nanotool.getMode(stack) == ItemNanotool.Mode.WORKBENCH;
        }
        return false;
    }
}
