package de.katzenpapst.amunra.tile;

import java.util.HashSet;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.FluidStack;

import micdoodle8.mods.galacticraft.api.entity.ICargoEntity;
import micdoodle8.mods.galacticraft.api.entity.IDockable;
import micdoodle8.mods.galacticraft.api.entity.IFuelable;
import micdoodle8.mods.galacticraft.api.tile.IFuelDock;
import micdoodle8.mods.galacticraft.api.tile.ILandingPadAttachable;
import micdoodle8.mods.galacticraft.core.tile.TileEntityMulti;

public class TileEntityShuttleDockFake extends TileEntityMulti implements IFuelable, IFuelDock, ICargoEntity {

    public TileEntityShuttleDockFake() {}

    @Override
    public EnumCargoLoadingState addCargo(final ItemStack stack, final boolean doAdd) {

        final TileEntity main = this.getMainBlockTile();
        if (main instanceof ICargoEntity cargo) {
            return cargo.addCargo(stack, doAdd);
        }
        return EnumCargoLoadingState.NOTARGET;
    }

    @Override
    public RemovalResult removeCargo(final boolean doRemove) {
        final TileEntity main = this.getMainBlockTile();
        if (main instanceof ICargoEntity cargo) {
            return cargo.removeCargo(doRemove);
        }
        return new RemovalResult(EnumCargoLoadingState.NOTARGET, null);
    }

    @Override
    public HashSet<ILandingPadAttachable> getConnectedTiles() {
        final TileEntity main = this.getMainBlockTile();
        if (main instanceof IFuelDock dock) {
            return dock.getConnectedTiles();
        }
        return new HashSet<>();
    }

    @Override
    public boolean isBlockAttachable(final IBlockAccess world, final int x, final int y, final int z) {
        final TileEntity main = this.getMainBlockTile();
        if (main instanceof IFuelDock dock) {
            return dock.isBlockAttachable(world, x, y, z);
        }
        return false;
    }

    @Override
    public IDockable getDockedEntity() {
        final TileEntity main = this.getMainBlockTile();
        if (main instanceof IFuelDock dock) {
            return dock.getDockedEntity();
        }
        return null;
    }

    @Override
    public void dockEntity(final IDockable entity) {
        final TileEntity main = this.getMainBlockTile();
        if (main instanceof IFuelDock dock) {
            dock.dockEntity(entity);
        }
    }

    @Override
    public int addFuel(final FluidStack fluid, final boolean doDrain) {
        final TileEntity main = this.getMainBlockTile();
        if (main instanceof IFuelable fuelable) {
            return fuelable.addFuel(fluid, doDrain);
        }
        return 0;
    }

    @Override
    public FluidStack removeFuel(final int amount) {
        final TileEntity main = this.getMainBlockTile();
        if (main instanceof IFuelable fuelable) {
            return fuelable.removeFuel(amount);
        }
        return null;
    }

}
