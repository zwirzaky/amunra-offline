package de.katzenpapst.amunra.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemDamagePair {

    protected Item item;
    protected int damage;

    public ItemDamagePair(final Item item, final int damage) {
        this.item = item;
        this.damage = damage;
    }

    public ItemDamagePair(final ItemStack stack) {
        this.item = stack.getItem();
        this.damage = stack.getItemDamage();
    }

    public Item getItem() {
        return this.item;
    }

    public int getDamage() {
        return this.damage;
    }

    public Item getSubItem() {
        if (!(this.item instanceof ItemBasicMulti)) {
            return this.item;
        }
        return ((ItemBasicMulti) this.item).getSubItem(this.damage);
    }

    public ItemStack getItemStack(final int numItems) {
        return new ItemStack(this.item, numItems, this.damage);
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ItemDamagePair otherCast)) {
            return false;
        }
        return this.isSameItem(otherCast.getItem(), otherCast.getDamage());
    }

    @Override
    public int hashCode() {
        if (this.item instanceof ItemBlock) {
            return ((ItemBlock) this.item).field_150939_a.hashCode() ^ ~this.damage;
        }
        return this.item.hashCode() ^ ~this.damage;
    }

    public boolean isSameItem(final ItemStack stack) {
        if (stack == null) {
            return false;
        }
        return this.isSameItem(stack.getItem(), stack.getItemDamage());
    }

    public boolean isSameItem(final Item item, final int damage) {

        // this matters anyway, do it here before the other mess
        if (this.damage != damage) {
            return false;
        }
        if (item instanceof ItemBlock) {
            if (this.item instanceof ItemBlock) {
                // compare blocks... *sigh*
                return ((ItemBlock) this.item).field_150939_a == ((ItemBlock) item).field_150939_a;
            } else {
                return false;
            }
        }
        if (this.item instanceof ItemBlock) {
            return false;
        } else {
            return this.item == item;
        }
    }

}
