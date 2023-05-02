package de.katzenpapst.amunra.inventory.schematic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventorySchematicShuttle implements IInventory {

    protected final ItemStack[] stackList;
    protected final int inventoryWidth;
    protected final Container eventHandler;

    public InventorySchematicShuttle(final int numSlots, final Container par1Container) {
        this.stackList = new ItemStack[numSlots];
        this.eventHandler = par1Container;
        this.inventoryWidth = 5; // what for?
    }

    @Override
    public int getSizeInventory() {
        return this.stackList.length;
    }

    @Override
    public ItemStack getStackInSlot(final int slot) {
        return slot >= this.getSizeInventory() ? null : this.stackList[slot];
    }

    public ItemStack getStackInRowAndColumn(final int x, final int y) {
        if ((x < 0) || (x >= this.inventoryWidth)) {
            return null;
        }
        final int stackNr = x + y * this.inventoryWidth;
        if (stackNr >= 22) {
            return null;
        }
        return this.getStackInSlot(stackNr);
    }

    @Override
    public String getInventoryName() {
        return "container.crafting";
    }

    @Override
    public ItemStack getStackInSlotOnClosing(final int slot) {
        if (this.stackList[slot] != null) {
            final ItemStack curStack = this.stackList[slot];
            this.stackList[slot] = null;
            return curStack;
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(final int slot, final int amount) {
        if (this.stackList[slot] == null) {
            return null;
        }
        ItemStack var3;

        if (this.stackList[slot].stackSize <= amount) {
            var3 = this.stackList[slot];
            this.stackList[slot] = null;
        } else {
            var3 = this.stackList[slot].splitStack(amount);

            if (this.stackList[slot].stackSize == 0) {
                this.stackList[slot] = null;
            }
        }
        this.eventHandler.onCraftMatrixChanged(this);
        return var3;
    }

    @Override
    public void setInventorySlotContents(final int slot, final ItemStack par2ItemStack) {
        this.stackList[slot] = par2ItemStack;
        this.eventHandler.onCraftMatrixChanged(this);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return true;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return false; // but why?
    }
}
