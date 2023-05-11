package de.katzenpapst.amunra.block;

import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.item.ItemDamagePair;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class SubBlockLeaf extends SubBlock {

    protected IIcon blockIconOpaque = null;
    protected ItemDamagePair itemDropped = null;

    public SubBlockLeaf(final String name, final String texture) {
        super(name, texture);
    }

    public SubBlockLeaf(final String name, final String texture, final String tool, final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
    }

    public SubBlockLeaf(final String name, final String texture, final String tool, final int harvestLevel,
            final float hardness, final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
    }

    public SubBlockLeaf setSaplingDropped(final BlockMetaPair sapling) {
        this.itemDropped = new ItemDamagePair(Item.getItemFromBlock(sapling.getBlock()), sapling.getMetadata());
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(this.getTextureName());
        this.blockIconOpaque = reg.registerIcon(this.getTextureName() + "_opaque");
    }

    public IIcon getOpaqueIcon(final int side) {
        return this.blockIconOpaque;
    }

    @Override
    public boolean dropsSelf() {
        return false;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return this.itemDropped.getItem();
    }

    @Override
    public int damageDropped(int meta) {
        return this.itemDropped.getDamage();
    }

}
