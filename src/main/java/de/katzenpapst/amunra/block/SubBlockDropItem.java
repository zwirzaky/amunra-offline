package de.katzenpapst.amunra.block;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

import de.katzenpapst.amunra.item.ItemDamagePair;

public class SubBlockDropItem extends SubBlock {

    protected Random rand = new Random();

    protected ItemDamagePair droppedItems = null;
    /**
     * Minimum amount to drop. Probably shouldn't be != 1...
     */
    protected int baseDropRateMin = 1;
    /**
     * Usually fortune 3 can give up to 4 items. This will be multiplied on that value
     */
    protected float bonusDropMultiplier = 1;

    protected int xpDropMin = 0;
    protected int xpDropMax = 0;

    protected boolean isValuable = false;

    public SubBlockDropItem(final String name, final String texture) {
        super(name, texture);
    }

    public SubBlockDropItem(final String name, final String texture, final String tool, final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
    }

    public SubBlockDropItem(final String name, final String texture, final String tool, final int harvestLevel,
            final float hardness, final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
    }

    @Override
    public boolean dropsSelf() {
        return this.droppedItems == null;
    }

    public SubBlockDropItem setDroppedItem(final ItemDamagePair item) {
        this.droppedItems = item;
        return this;
    }

    @Override
    public int quantityDropped(final int meta, final int fortune, final Random random) {
        int j = random.nextInt(fortune + 2) - 1;

        if (j < 0) {
            j = 0;
        }

        int result = (int) (this.quantityDropped(random) * (j + 1) * this.bonusDropMultiplier);
        if (result < this.baseDropRateMin) {
            result = this.baseDropRateMin;
        }
        return result;

        // return Math.min(random.nextInt(3)+random.nextInt(10)*fortune, 9);
    }

    /**
     * Returns the quantity of items to drop on block destruction. There is no metadata here, so if this stuff is called
     * from the outside, I can't do shit
     */
    @Override
    public int quantityDropped(final Random rand) {
        return this.baseDropRateMin;
    }

    @Override
    public int damageDropped(final int meta) {
        return this.droppedItems != null ? this.droppedItems.getDamage() : super.damageDropped(meta);
    }

    @Override
    public Item getItemDropped(final int meta, final Random random, final int fortune) {
        return this.droppedItems != null ? this.droppedItems.getItem() : super.getItemDropped(meta, random, fortune);
    }

    public SubBlockDropItem setDroppedItem(final Item item) {
        this.droppedItems = new ItemDamagePair(item, 0);
        return this;
    }

    public SubBlockDropItem setMinDropRate(final int val) {
        this.baseDropRateMin = val;
        return this;
    }

    public SubBlockDropItem setBonusMultiplier(final float val) {
        this.bonusDropMultiplier = val;
        return this;
    }

    public SubBlockDropItem setXpDrop(final int dropMin, final int dropMax) {
        this.xpDropMin = dropMin;
        this.xpDropMax = dropMax;
        return this;
    }

    @Override
    public int getExpDrop(final IBlockAccess world, final int metadata, final int fortune) {
        if (!this.dropsSelf()) {
            if (this.xpDropMin <= this.xpDropMax) {
                return this.xpDropMin;
            }
            MathHelper.getRandomIntegerInRange(this.rand, this.xpDropMin, this.xpDropMax);
        }
        return 0;
    }

    @Override
    public boolean isValueable(final int metadata) {
        return this.isValuable;
    }

    public SubBlockDropItem setIsValueable(final boolean set) {
        this.isValuable = set;
        return this;
    }
}
