package de.katzenpapst.amunra.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class HydroponicsRecipe {

    public int growthDuration;

    public ItemStack[] possibleOutputs;

    public ResourceLocation cropTexture;

    public HydroponicsRecipe(final int growDuration, final ResourceLocation cropTexture, final ItemStack... outputs) {
        this.growthDuration = growDuration;
        this.cropTexture = cropTexture;
        this.possibleOutputs = outputs;
    }

}
