package de.katzenpapst.amunra.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBucket;
import net.minecraftforge.fluids.ItemFluidContainer;

import de.katzenpapst.amunra.tile.TileEntityMothershipEngineAbstract;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;

public class ContainerRocketEngine extends ContainerWithPlayerInventory {

    public ContainerRocketEngine(final InventoryPlayer par1InventoryPlayer, final TileEntityMothershipEngineAbstract tile) {

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
        this.addSlotToContainer(new SlotSpecific(tile, 0, 8, 7, ItemFluidContainer.class, ItemBucket.class));
    }

    @Override
    public boolean canInteractWith(final EntityPlayer var1) {
        return ((TileEntityMothershipEngineAbstract) this.tileEntity).isUseableByPlayer(var1);
    }

}
