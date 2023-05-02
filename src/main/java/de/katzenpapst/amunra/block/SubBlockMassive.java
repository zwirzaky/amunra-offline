package de.katzenpapst.amunra.block;

import net.minecraft.world.World;

public class SubBlockMassive extends SubBlock implements IMassiveBlock {

    protected float mass = 1.0F;

    public SubBlockMassive(final String name, final String texture) {
        super(name, texture);
    }

    public SubBlockMassive(final String name, final String texture, final String tool, final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
    }

    public SubBlockMassive(final String name, final String texture, final String tool, final int harvestLevel, final float hardness,
            final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
    }

    @Override
    public float getMass(final World w, final int x, final int y, final int z, final int meta) {
        return mass;
    }

    public SubBlockMassive setMass(final float mass) {
        this.mass = mass;
        return this;
    }

}
