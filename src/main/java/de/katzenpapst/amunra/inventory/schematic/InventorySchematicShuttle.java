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
    public ItemStack getStackInSlot(int slotIn) {
        return slotIn >= this.getSizeInventory() ? null : this.stackList[slotIn];
    }

    public ItemStack getStackInRowAndColumn(final int x, final int y) {
        if (x < 0 || x >= this.inventoryWidth) {
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
    public ItemStack getStackInSlotOnClosing(int index) {
        if (this.stackList[index] != null) {
            final ItemStack curStack = this.stackList[index];
            this.stackList[index] = null;
            return curStack;
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.stackList[index] == null) {
            return null;
        }
        ItemStack var3;

        if (this.stackList[index].stackSize <= count) {
            var3 = this.stackList[index];
            this.stackList[index] = null;
        } else {
            var3 = this.stackList[index].splitStack(count);

            if (this.stackList[index].stackSize == 0) {
                this.stackList[index] = null;
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
    public boolean isUseableByPlayer(EntityPlayer player) {
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
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false; // but why?
    }
}
