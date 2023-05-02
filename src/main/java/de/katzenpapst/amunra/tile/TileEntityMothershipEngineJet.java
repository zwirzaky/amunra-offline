package de.katzenpapst.amunra.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.block.ARBlocks;
import de.katzenpapst.amunra.mothership.fueldisplay.MothershipFuelDisplay;
import de.katzenpapst.amunra.mothership.fueldisplay.MothershipFuelDisplayFluid;
import de.katzenpapst.amunra.mothership.fueldisplay.MothershipFuelRequirements;
import de.katzenpapst.amunra.proxy.ARSidedProxy.ParticleType;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

/**
 * This is supposed to be used for any jet blocks
 * 
 * @author katzenpapst
 *
 */
public class TileEntityMothershipEngineJet extends TileEntityMothershipEngineAbstract {

    // public static final int MAX_LENGTH = 10;
    // protected PositionedSoundRecord leSound;

    // protected final MothershipFuel fuelType;
    protected MothershipFuelDisplay fuelType = null;

    public TileEntityMothershipEngineJet() {
        this.boosterBlock = ARBlocks.blockMsEngineRocketBooster;
        this.containingItems = new ItemStack[1];

        this.fuel = GalacticraftCore.fluidFuel;
        this.fuelType = new MothershipFuelDisplayFluid(this.fuel);
    }

    @Override
    public boolean shouldUseEnergy() {
        return false;
    }

    @Override
    public void beginTransit(final long duration) {

        final MothershipFuelRequirements reqs = this.getFuelRequirements(duration);

        final int fuelReq = reqs.get(this.fuelType);

        this.fuelTank.drain(fuelReq, true);

        super.beginTransit(duration);

    }

    @Override
    protected boolean isItemFuel(final ItemStack itemstack) {

        FluidStack containedFluid = null;
        if (itemstack.getItem() instanceof IFluidContainerItem) {
            containedFluid = ((IFluidContainerItem) itemstack.getItem()).getFluid(itemstack);
        }
        if (containedFluid == null) {
            containedFluid = FluidContainerRegistry.getFluidForFilledItem(itemstack);
        }
        if (containedFluid != null) {
            if (containedFluid.getFluid() == this.fuel) {
                return true;
            }
            return FluidUtil.testFuel(FluidRegistry.getFluidName(containedFluid));
        }

        return false;
    }

    /**
     * Calculates tank capacity based on the boosters
     * 
     * @return
     */
    @Override
    protected int getTankCapacity() {
        return 10000 * this.numBoosters;
    }

    @Override
    protected void startSound() {
        super.startSound();
        AmunRa.proxy.playTileEntitySound(this, new ResourceLocation(AmunRa.TEXTUREPREFIX + "mothership.engine.rocket"));
    }

    @Override
    protected void spawnParticles() {

        final Vector3 particleStart = this.getExhaustPosition(1);
        final Vector3 particleDirection = this.getExhaustDirection().scale(5);

        AmunRa.proxy
                .spawnParticles(ParticleType.PT_MOTHERSHIP_JET_FLAME, this.worldObj, particleStart, particleDirection);
        AmunRa.proxy
                .spawnParticles(ParticleType.PT_MOTHERSHIP_JET_FLAME, this.worldObj, particleStart, particleDirection);
        AmunRa.proxy
                .spawnParticles(ParticleType.PT_MOTHERSHIP_JET_FLAME, this.worldObj, particleStart, particleDirection);
        AmunRa.proxy
                .spawnParticles(ParticleType.PT_MOTHERSHIP_JET_FLAME, this.worldObj, particleStart, particleDirection);

    }

    @Override
    public boolean canFill(final ForgeDirection from, final Fluid fluid) {

        // here, fluid is fuel
        if (!FluidUtil.testFuel(FluidRegistry.getFluidName(fluid))) {
            return false;
        }

        return super.canFill(from, fluid);
    }

    @Override
    public String getInventoryName() {
        return GCCoreUtil.translate("tile.mothershipEngineRocket.name");
    }

    @Override
    public boolean isItemValidForSlot(final int slotID, final ItemStack itemstack) {
        if (slotID == 0 && itemstack != null) {
            return this.isItemFuel(itemstack);
        }
        /*
         * FluidStack containedFluid = FluidContainerRegistry.getFluidForFilledItem(itemstack);
         * if(containedFluid.getFluid() == fuel) { return true; }
         */
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(final int side) {
        return new int[] { 0 };
    }

    @Override
    public boolean canInsertItem(final int slotID, final ItemStack itemstack, final int side) {
        return this.isItemValidForSlot(slotID, itemstack);
    }

    @Override
    public boolean canExtractItem(final int slotID, final ItemStack itemstack, final int side) {
        return slotID == 0;
    }

    @Override
    public double getThrust() {
        return this.getNumBoosters() * 2000000.0D;
    }

    /**
     * This should return how much fuel units are consumed per AU travelled, in millibuckets
     * 
     * @return
     */
    public float getFuelUsagePerTick() {
        return 2.0F;
    }

    @Override
    public MothershipFuelRequirements getFuelRequirements(final long duration) {
        final int totalFuelNeed = (int) Math
                .ceil(this.getFuelUsagePerTick() * duration * AmunRa.config.mothershipFuelFactor);

        final MothershipFuelRequirements result = new MothershipFuelRequirements();

        result.add(this.fuelType, totalFuelNeed);

        return result;
    }

    @Override
    public boolean canRunForDuration(final long duration) {
        final MothershipFuelRequirements reqs = this.getFuelRequirements(duration);

        return reqs.get(this.fuelType) <= this.fuelTank.getFluidAmount();
    }

}
