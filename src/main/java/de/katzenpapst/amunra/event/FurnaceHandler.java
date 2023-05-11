package de.katzenpapst.amunra.event;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.IFuelHandler;
import de.katzenpapst.amunra.item.ItemBasicMulti;

public class FurnaceHandler implements IFuelHandler {

    @Override
    public int getBurnTime(final ItemStack fuel) {
        if (fuel.getItem() instanceof ItemBasicMulti itemMulti) {
            return itemMulti.getFuelDuration(fuel.getItemDamage());
        }
        return 0;
    }

}
