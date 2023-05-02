package de.katzenpapst.amunra.mob;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class RobotVillagerProfession {

    protected ResourceLocation icon;
    protected String name;
    protected MerchantRecipeList merchantList;

    protected static ArrayList<RobotVillagerProfession> professionRegistry = new ArrayList<>();

    public static int addProfession(final RobotVillagerProfession prof) {
        professionRegistry.add(prof);
        return professionRegistry.size() - 1;
    }

    public static RobotVillagerProfession getProfession(final int profession) {
        return professionRegistry.get(profession);
    }

    public static int getRandomProfession(final Random rand) {
        return rand.nextInt(professionRegistry.size());
    }

    public RobotVillagerProfession(final ResourceLocation icon, final String name, final MerchantRecipeList list) {
        this.icon = icon;
        this.name = name;
        this.merchantList = list;
    }

    public RobotVillagerProfession(final ResourceLocation icon, final String name) {
        this.icon = icon;
        this.name = name;
        this.merchantList = new MerchantRecipeList();
    }

    public ResourceLocation getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.name;
    }

    public MerchantRecipeList getRecipeList() {
        return this.merchantList;
    }

    @SuppressWarnings("unchecked")
    public RobotVillagerProfession addRecipe(final MerchantRecipe recipe) {
        this.merchantList.add(recipe);
        return this;
    }

    @SuppressWarnings("unchecked")
    public RobotVillagerProfession addRecipe(final ItemStack input1, final ItemStack input2, final ItemStack output) {
        this.merchantList.add(new MerchantRecipe(input1, input2, output));
        return this;
    }

    @SuppressWarnings("unchecked")
    public RobotVillagerProfession addRecipe(final ItemStack input, final ItemStack output) {
        this.merchantList.add(new MerchantRecipe(input, output));
        return this;
    }

    @SuppressWarnings("unchecked")
    public RobotVillagerProfession addRecipe(final Item singleInputItem, final int numEmeralds, final Item singleOutputItem) {
        this.merchantList.add(
                new MerchantRecipe(
                        new ItemStack(singleInputItem, 1),
                        new ItemStack(Items.emerald, numEmeralds),
                        new ItemStack(singleOutputItem, 1)));
        return this;
    }
}
