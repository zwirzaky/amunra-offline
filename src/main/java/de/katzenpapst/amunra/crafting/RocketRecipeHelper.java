package de.katzenpapst.amunra.crafting;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class RocketRecipeHelper {

    @SuppressWarnings("unchecked")
    ArrayList<ItemStack>[] stacks = new ArrayList[3];
    /*
     * ArrayList<ItemStack> stack2 = new ArrayList<ItemStack>(); ArrayList<ItemStack> stack3 = new
     * ArrayList<ItemStack>();
     */

    public RocketRecipeHelper() {
        for (int i = 0; i < this.stacks.length; i++) {
            this.stacks[i] = new ArrayList<>();
        }
    }

    public RocketRecipeHelper(final ItemStack one) {
        this();
        this.addSame(one);
    }

    public RocketRecipeHelper(final ItemStack one, final ItemStack otherTwo) {
        this();
        this.addPermutation1And2(one, otherTwo);
    }

    public RocketRecipeHelper(final ItemStack stack1, final ItemStack stack2, final ItemStack stack3) {
        this();
        this.addPermutation3different(stack1, stack2, stack3);
    }

    public ItemStack[] getStackArray(final int i) {
        final ItemStack[] result = new ItemStack[i];
        this.stacks[i].toArray(result);
        return result;
    }

    public ArrayList<ItemStack> getStacks(final int i) {
        return this.stacks[i];
    }

    /**
     * Adds permutations of 1 and 2 items, either can be null
     * 
     * @param one
     * @param otherTwo
     */
    public void addPermutation1And2(final ItemStack one, final ItemStack otherTwo) {
        this.stacks[0].add(one);
        this.stacks[1].add(otherTwo);
        this.stacks[2].add(otherTwo);

        this.stacks[0].add(otherTwo);
        this.stacks[1].add(one);
        this.stacks[2].add(otherTwo);

        this.stacks[0].add(otherTwo);
        this.stacks[1].add(otherTwo);
        this.stacks[2].add(one);
    }

    /**
     * Adds permutations for 3 different items
     * 
     * @param stack1
     * @param stack2
     * @param stack3
     */
    public void addPermutation3different(final ItemStack stack1, final ItemStack stack2, final ItemStack stack3) {
        this.stacks[0].add(stack1);
        this.stacks[1].add(stack2);
        this.stacks[2].add(stack3);

        this.stacks[0].add(stack1);
        this.stacks[1].add(stack3);
        this.stacks[2].add(stack2);

        this.stacks[0].add(stack2);
        this.stacks[1].add(stack1);
        this.stacks[2].add(stack3);

        this.stacks[0].add(stack2);
        this.stacks[1].add(stack3);
        this.stacks[2].add(stack1);

        this.stacks[0].add(stack3);
        this.stacks[1].add(stack1);
        this.stacks[2].add(stack2);

        this.stacks[0].add(stack3);
        this.stacks[1].add(stack2);
        this.stacks[2].add(stack1);
    }

    /**
     * Just adds the stack to everything
     * 
     * @param stack
     */
    public void addSame(final ItemStack stack) {
        this.stacks[0].add(stack);
        this.stacks[1].add(stack);
        this.stacks[2].add(stack);
    }

}
