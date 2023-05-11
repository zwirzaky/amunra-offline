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

    public ItemNuclearBattery(final String assetName, final float capacity, final float maxTransfer,
            final float rechargeRate) {
        super(assetName, capacity, maxTransfer);
        this.rechargeRate = rechargeRate;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int p_77663_4_, boolean p_77663_5_) {
        if (this.getElectricityStored(stack) < this.getMaxElectricityStored(stack)) {
            // recharge
            this.setElectricity(stack, this.getElectricityStored(stack) + this.rechargeRate);
        }
    }

}
