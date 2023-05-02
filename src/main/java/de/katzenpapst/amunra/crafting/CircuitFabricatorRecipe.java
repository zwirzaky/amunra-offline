package de.katzenpapst.amunra.crafting;

import net.minecraft.item.ItemStack;

/**
 * Helper for NEI and stuff
 * 
 * @author katzenpapst
 *
 */
public class CircuitFabricatorRecipe {

    public ItemStack output;
    public ItemStack[] crystal;
    public ItemStack[] silicon1;
    public ItemStack[] silicon2;
    public ItemStack[] redstone;
    public ItemStack[] optional;

    public CircuitFabricatorRecipe(final ItemStack output, final ItemStack[] crystal, final ItemStack[] silicon1,
            final ItemStack[] silicon2, final ItemStack[] redstone) {
        this(output, crystal, silicon1, silicon2, redstone, null);
    }

    public CircuitFabricatorRecipe(final ItemStack output, final ItemStack[] crystal, final ItemStack[] silicon1,
            final ItemStack[] silicon2, final ItemStack[] redstone, final ItemStack[] optional) {
        this.crystal = crystal;
        this.silicon1 = silicon1;
        this.silicon2 = silicon2;
        this.redstone = redstone;
        this.optional = optional;
        this.output = output;
    }

    public Object getCrystal() {
        if (this.crystal.length == 1) {
            return this.crystal[0];
        }
        return this.crystal;
    }

    public Object getSilicon1() {
        if (this.silicon1.length == 1) {
            return this.silicon1[0];
        }
        return this.silicon1;
    }

    public Object getSilicon2() {
        if (this.silicon2.length == 1) {
            return this.silicon2[0];
        }
        return this.silicon2;
    }

    public Object getRedstone() {
        if (this.redstone.length == 1) {
            return this.redstone[0];
        }
        return this.redstone;
    }

    public Object getOptional() {
        if (this.optional == null || this.optional.length == 0) {
            return null;
        }
        if (this.optional.length == 1) {
            return this.optional[0];
        }
        return this.optional;
    }
}
