package de.katzenpapst.amunra.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNuclearBattery extends ItemBaseBattery {

    protected float rechargeRate;

    public ItemNuclearBattery(final String assetName, final float capacity, final float rechargeRate) {
        super(assetName, capacity);
        this.rechargeRate = rechargeRate;
    }

    public ItemNuclearBattery(final String assetName, final float capacity, final float maxTransfer, final float rechargeRate) {
        super(assetName, capacity, maxTransfer);
        this.rechargeRate = rechargeRate;
    }

    @Override
    public void onUpdate(final ItemStack stack, final World world, final Entity entity, final int stackNumber, final boolean isBeingHeld) {
        if (this.getElectricityStored(stack) < this.getMaxElectricityStored(stack)) {
            // recharge
            this.setElectricity(stack, this.getElectricityStored(stack) + rechargeRate);
        }
    }

}
