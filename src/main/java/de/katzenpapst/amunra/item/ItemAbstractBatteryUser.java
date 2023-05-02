package de.katzenpapst.amunra.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.items.GCItems;

public class ItemAbstractBatteryUser extends ItemElectricBase {

    @Override
    public float getMaxElectricityStored(final ItemStack theItem) {
        if (theItem.getTagCompound() == null) {
            theItem.setTagCompound(new NBTTagCompound());
        }
        if (theItem.getTagCompound().hasKey("maxEnergy")) {
            return theItem.getTagCompound().getFloat("maxEnergy");
        }

        final ItemStack bat = getUsedBattery(theItem, false);
        final float maxEnergy = ((ItemElectricBase) bat.getItem()).getMaxElectricityStored(bat);
        theItem.getTagCompound().setFloat("maxEnergy", maxEnergy);
        return maxEnergy;
        // return 15000; // fallback
    }

    @Override
    public void onCreated(final ItemStack itemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        // important NOT to call the parent for this, because there are crafting recipes
        // which create non-empty rayguns
        // this.setElectricity(itemStack, 0);
    }

    /**
     * Returns the battery currently in this device as ItemStack. The ItemStack will be newly constructed
     *
     * @param theItem   The ItemAbstractBatteryUser ItemStack
     * @param setEnergy if true, the result itemstack will also have the energy of the current battery user
     * @return
     */
    public ItemStack getUsedBattery(final ItemStack theItem, final boolean setEnergy) {
        if (theItem.getTagCompound() == null) {
            theItem.setTagCompound(new NBTTagCompound());
        }

        final NBTTagCompound stackNBT = theItem.getTagCompound().getCompoundTag("batteryStack");
        ItemStack batteryStack = null;
        if (stackNBT == null) {
            // default?
            batteryStack = new ItemStack(GCItems.battery, 1, 0);
        } else {
            batteryStack = ItemStack.loadItemStackFromNBT(stackNBT);
            if (batteryStack == null) {
                batteryStack = new ItemStack(GCItems.battery, 1, 0);
            }
        }

        // ItemStack bat = new ItemStack(getUsedBatteryID(theItem), 1, 0);
        if (setEnergy) {
            ((ItemElectricBase) batteryStack.getItem())
                    .setElectricity(batteryStack, this.getElectricityStored(theItem));
        }
        return batteryStack;
    }

    /**
     * Set the battery to use for this BU. also sets the BU's energy level to that of the battery
     *
     * @param theItem
     * @param battery
     */
    public void setUsedBattery(final ItemStack theItem, final ItemStack battery) {
        if (theItem.getTagCompound() == null) {
            theItem.setTagCompound(new NBTTagCompound());
        }

        final NBTTagCompound batteryStackCompound = new NBTTagCompound();
        battery.writeToNBT(batteryStackCompound);

        theItem.getTagCompound()
                .setFloat("maxEnergy", ((ItemElectricBase) battery.getItem()).getMaxElectricityStored(battery));
        theItem.getTagCompound().setTag("batteryStack", batteryStackCompound);

        this.setElectricity(theItem, ((ItemElectricBase) battery.getItem()).getElectricityStored(battery));
    }

    public Item getUsedBatteryID(final ItemStack theItem) {
        if (theItem.getTagCompound().hasKey("batteryStack")) {
            final NBTTagCompound stackNBT = theItem.getTagCompound().getCompoundTag("batteryStack");
            final ItemStack batteryStack = ItemStack.loadItemStackFromNBT(stackNBT);

            return batteryStack.getItem();
        }
        return GCItems.battery;
    }

    @Override
    public void onUpdate(final ItemStack stack, final World world, final Entity entity, final int stackNumber, final boolean isBeingHeld) {
        final ItemStack battery = this.getUsedBattery(stack, true);
        battery.getItem().onUpdate(battery, world, entity, stackNumber, isBeingHeld);
        // do I write the battery back in?
        // I'm somewhat afraid regarding the efficiency of this
        // this.setUsedBattery(stack, battery);
        // maybe this is better
        this.setElectricity(stack, ((ItemElectricBase) battery.getItem()).getElectricityStored(battery));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean par4) {
        super.addInformation(itemStack, entityPlayer, list, par4);

        final Item batItem = getUsedBatteryID(itemStack);
        //
        final String s = StatCollector.translateToLocal("item.battery-using-item.powerlevel") + ": "
                + StatCollector.translateToLocal(batItem.getUnlocalizedName() + ".name");

        list.add(s);
    }

}
