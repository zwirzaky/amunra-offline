package de.katzenpapst.amunra.block.ore;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import de.katzenpapst.amunra.item.ItemDamagePair;

public class SubBlockOreMultidrop extends SubBlockOre {

    public class DroppedItem {

        // the item
        public Item item;
        // the item's metadata
        public int metadata;
        // min amount to drop
        public int minDrop;
        // max amount to drop. relevant for fortune
        public int maxDrop;
        // the probability to be evaluated at all. fortune will be multiplied onto this
        public float probability = 1;

        public DroppedItem(final Item item, final int meta, final int minDrop, final int maxDrop, final float probability) {
            this.item = item;
            this.metadata = meta;
            this.minDrop = minDrop;
            this.maxDrop = maxDrop;
            this.probability = probability;
        }
    }

    protected ArrayList<DroppedItem> dropList = new ArrayList<DroppedItem>();

    public SubBlockOreMultidrop(final String name, final String texture) {
        super(name, texture);
    }

    public SubBlockOreMultidrop addDroppedItem(final Item item, final int metadata, final int minDrop, final int maxDrop, final float probability) {
        dropList.add(new DroppedItem(item, metadata, minDrop, maxDrop, probability));
        return this;
    }

    public SubBlockOreMultidrop addDroppedItem(final Item item, final int metadata, final int minDrop, final int maxDrop) {
        dropList.add(new DroppedItem(item, metadata, minDrop, maxDrop, 1));
        return this;
    }

    public SubBlockOreMultidrop addDroppedItem(final ItemDamagePair idp, final int minDrop, final int maxDrop) {
        addDroppedItem(idp.getItem(), idp.getDamage(), minDrop, maxDrop);
        return this;
    }

    public SubBlockOreMultidrop addDroppedItem(final ItemDamagePair idp, final int minDrop, final int maxDrop, final float probability) {
        addDroppedItem(idp.getItem(), idp.getDamage(), minDrop, maxDrop, probability);
        return this;
    }

    @Override
    public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int metadata, final int fortune) {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        for (final DroppedItem di : dropList) {
            if (di.probability < 1) {
                final float effectiveProb = di.probability * fortune;
                if (effectiveProb < 1) {
                    if (this.rand.nextFloat() >= effectiveProb) {
                        continue; // skip this
                    }
                }
            }
            final float bonusDrop = Math.round(fortune * this.rand.nextInt(di.maxDrop - di.minDrop + 1) / 3.0F);
            final int numDrops = (int) (bonusDrop + di.minDrop);
            if (numDrops > 0) {
                ret.add(new ItemStack(di.item, numDrops, di.metadata));
            }
        }
        return ret;
    }

    @Override
    public boolean dropsSelf() {
        return dropList.size() == 0;
    }

}
