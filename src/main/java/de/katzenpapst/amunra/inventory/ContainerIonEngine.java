package de.katzenpapst.amunra.inventory;

import net.minecraft.entity.player.InventoryPlayer;

import de.katzenpapst.amunra.tile.TileEntityMothershipEngineAbstract;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;

public class ContainerIonEngine extends ContainerRocketEngine {

    public ContainerIonEngine(final InventoryPlayer par1InventoryPlayer,
            final TileEntityMothershipEngineAbstract solarGen) {
        super(par1InventoryPlayer, solarGen);
    }

    @Override
    protected void initSlots(final TileEntityMothershipEngineAbstract tile) {
        super.initSlots(tile);
        this.addSlotToContainer(new SlotSpecific(tile, 1, 152, 86, ItemElectricBase.class));
    }

}
