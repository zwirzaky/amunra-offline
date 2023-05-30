package de.katzenpapst.amunra.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.IFluidContainerItem;

import de.katzenpapst.amunra.tile.TileEntityMothershipEngineAbstract;

public class ContainerRocketEngine extends ContainerWithPlayerInventory {

    public ContainerRocketEngine(final InventoryPlayer par1InventoryPlayer,
            final TileEntityMothershipEngineAbstract tile) {

        super(tile);

        /*
         * this.inventory = p_i1824_1_; this.slotIndex = p_i1824_2_; this.xDisplayPosition = p_i1824_3_;
         * this.yDisplayPosition = p_i1824_4_;
         */
        // inv, slotIndex, x, y
        this.initSlots(tile);

        this.initPlayerInventorySlots(par1InventoryPlayer);
    }

    protected void initSlots(final TileEntityMothershipEngineAbstract tile) {
        this.addSlotToContainer(new Slot(tile, 0, 8, 7) {

            @Override
            public boolean isItemValid(ItemStack stack) {
                final Item item = stack.getItem();
                return item instanceof IFluidContainerItem || item instanceof ItemBucket
                        || FluidContainerRegistry.isContainer(stack);
            }
        });
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return ((TileEntityMothershipEngineAbstract) this.tileEntity).isUseableByPlayer(player);
    }

}
