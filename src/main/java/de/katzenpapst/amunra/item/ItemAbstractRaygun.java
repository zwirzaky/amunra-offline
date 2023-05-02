package de.katzenpapst.amunra.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.entity.EntityBaseLaserArrow;

public abstract class ItemAbstractRaygun extends ItemAbstractBatteryUser {

    // set to true for chargeMode, instead of single-shot mode, which would fire each time
    // the player rightclicks
    protected boolean chargeMode = false;

    public ItemAbstractRaygun(final String assetName) {
        this.setUnlocalizedName(assetName);
        this.setTextureName(AmunRa.TEXTUREPREFIX + assetName);
        this.maxStackSize = 1;

        // batteryInUse = new ItemStack(GCItems.battery, 1);
        // batteryInUse.getTagCompound()
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return AmunRa.arTab;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */

    @Override
    public void onPlayerStoppedUsing(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer, final int itemInUseCount) {
        if (!this.chargeMode) {
            return;
        }
        // int j = this.getMaxItemUseDuration(itemStack) - itemInUseCount;

        this.fire(itemStack, entityPlayer, world);
    }

    /**
     * How long it takes to use or consume an item
     */

    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction(final ItemStack stack) {
        return EnumAction.bow;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        /*
         * ArrowNockEvent event = new ArrowNockEvent(entityPlayer, itemStack); MinecraftForge.EVENT_BUS.post(event); if
         * (event.isCanceled()) { return event.result; }
         */
        if (entityPlayer.capabilities.isCreativeMode || this.getElectricityStored(itemStack) >= this.getEnergyPerShot()) {

            entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
            if (!this.chargeMode) {
                this.fire(itemStack, entityPlayer, world);
            }
        } else if (!world.isRemote) {
            world.playSoundAtEntity(
                    entityPlayer,
                    this.getEmptySound(),
                    1.0F,
                    1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);

        }

        return itemStack;
    }

    public float getEnergyPerShot() {
        return 300.0F;
    }

    protected String getFiringSound() {
        return AmunRa.TEXTUREPREFIX + "weapon.lasergun.shot";
    }

    protected String getEmptySound() {
        return AmunRa.TEXTUREPREFIX + "weapon.lasergun.empty";
    }

    protected boolean fire(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            this.setElectricity(
                    itemStack,
                    this.getElectricityStored(itemStack) - this.getModifiedEnergyPerShot(itemStack));
        }
        if (!world.isRemote) {
            world.playSoundAtEntity(
                    entityPlayer,
                    this.getFiringSound(),
                    1.0F,
                    1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
            // LaserArrow entityarrow = new LaserArrow(world, entityPlayer);
            this.spawnProjectile(itemStack, entityPlayer, world);
        }
        return true;
    }

    protected float getModifiedEnergyPerShot(final ItemStack stack) {
        float base = this.getEnergyPerShot();

        final int efficiency = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
        // max level seems to be 5
        final float relativeEff = efficiency / 10.0F;

        return base * (1.0F - relativeEff);
    }

    protected void spawnProjectile(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world) {
        final EntityBaseLaserArrow ent = this.createProjectile(itemStack, entityPlayer, world);

        // enchantment stuff

        world.spawnEntityInWorld(ent);
    }

    abstract protected EntityBaseLaserArrow createProjectile(ItemStack itemStack, EntityPlayer entityPlayer,
            World world);

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(this.getIconString());
        // this.itemEmptyIcon = iconRegister.registerIcon(this.getIconString() + "_empty");
    }

    @Override
    public IIcon getIcon(final ItemStack stack, final int renderPass, final EntityPlayer player, final ItemStack usingItem, final int useRemaining) {
        return super.getIcon(stack, renderPass, player, usingItem, useRemaining);

        /*
         * final int count2 = useRemaining / 2; switch (count2 % 5) { case 0: if (useRemaining == 0) { return
         * this.icons[0]; } return this.icons[4]; case 1: return this.icons[3]; case 2: return this.icons[2]; case 3:
         * return this.icons[1]; case 4: return this.icons[0]; } return this.icons[0];
         */
    }

}
