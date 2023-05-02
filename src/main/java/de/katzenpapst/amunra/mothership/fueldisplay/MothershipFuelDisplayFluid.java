package de.katzenpapst.amunra.mothership.fueldisplay;

import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class MothershipFuelDisplayFluid extends MothershipFuelDisplay {

    private final Fluid fluid;

    public MothershipFuelDisplayFluid(final Fluid fluid) {
        this.fluid = fluid;
    }

    @Override
    public IIcon getIcon() {
        return this.fluid.getIcon();
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getDisplayName() {
        return this.fluid.getLocalizedName();
    }

    @Override
    public int getSpriteNumber() {
        return this.fluid.getSpriteNumber();
    }

    @Override
    public String getUnit() {
        return "B";
    }

    @Override
    public float getFactor() {
        return 0.001F;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof MothershipFuelDisplayFluid)) {
            return false;
        }
        return this.fluid == ((MothershipFuelDisplayFluid) other).fluid;
    }

    @Override
    public int hashCode() {
        return this.fluid.hashCode() + 89465;
    }

}
