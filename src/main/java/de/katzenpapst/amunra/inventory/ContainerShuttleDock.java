package de.katzenpapst.amunra.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import de.katzenpapst.amunra.item.ItemShuttle;
import de.katzenpapst.amunra.tile.TileEntityShuttleDock;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;

public class ContainerShuttleDock extends ContainerWithPlayerInventory {

    public ContainerShuttleDock(InventoryPlayer player, TileEntityShuttleDock tile) {
        super((IInventory) tile);

        this.addSlotToContainer(new SlotSpecific(tile, 0, 137, 59, ItemShuttle.class));

        initPlayerInventorySlots(player, 9);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return ((TileEntityShuttleDock) this.tileEntity).isUseableByPlayer(player);
    }

}
