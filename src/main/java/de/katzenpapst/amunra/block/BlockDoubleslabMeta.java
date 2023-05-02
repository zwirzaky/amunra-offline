package de.katzenpapst.amunra.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.item.ItemSlabMulti;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class BlockDoubleslabMeta extends BlockBasicMeta {

    protected final BlockSlabMeta slabMetablock;

    /**
     * I think this has to match the Slabs, meta-wise
     * 
     * @param name
     * @param mat
     */
    public BlockDoubleslabMeta(final String name, final Material mat, final BlockSlabMeta slabMetablock) {
        super(name, mat, 8);
        this.slabMetablock = slabMetablock;
        slabMetablock.setDoubleslabMeta(this);
    }

    @Override
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
        // int meta = world.getBlockMetadata(x, y, z);
        return this.slabMetablock.getPickBlock(target, world, x, y, z);
    }

    @Override
    public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int metadata, final int fortune) {
        final ArrayList<ItemStack> ret = new ArrayList<>();

        final int count = this.quantityDropped(metadata, fortune, world.rand);
        for (int i = 0; i < count; i++) {
            final Item item = this.getItemDropped(metadata, world.rand, fortune);
            if (item != null) {
                ret.add(new ItemStack(item, 1, this.damageDropped(metadata)));
            }
        }
        return ret;

    }

    @Override
    public String getUnlocalizedSubBlockName(final int meta) {
        return this.getSubBlock(meta).getUnlocalizedName() + ".slab";
    }

    @Override
    public Item getItemDropped(final int meta, final Random random, final int fortune) {
        return this.slabMetablock.getItemDropped(meta, random, fortune);
        // return Item.getItemFromBlock(slabMetablock);
    }

    @Override
    public int damageDropped(final int meta) {
        return this.getDistinctionMeta(meta);
    }

    @Override
    public int getDamageValue(final World world, final int x, final int y, final int z) {
        return this.getDistinctionMeta(world.getBlockMetadata(x, y, z));
    }

    @Override
    public int quantityDropped(final int meta, final int fortune, final Random random) {
        return 2;
    }

    @Override
    public BlockMetaPair addSubBlock(final int meta, final SubBlock sb) {

        if (meta >= this.subBlocksArray.length || meta < 0) {
            throw new IllegalArgumentException(
                    "Meta " + meta + " must be <= " + (this.subBlocksArray.length - 1) + " && >= 0");
        }

        if (this.subBlocksArray[meta] != null) {
            throw new IllegalArgumentException("Meta " + meta + " is already in use");
        }

        if (this.nameMetaMap.get(sb.getUnlocalizedName()) != null) {
            throw new IllegalArgumentException("Name " + sb.getUnlocalizedName() + " is already in use");
        }
        // sb.setParent(this);
        this.nameMetaMap.put(sb.getUnlocalizedName(), meta);
        this.subBlocksArray[meta] = sb;
        return new BlockMetaPair(this, (byte) meta);
    }

    public BlockMetaPair addSubBlock(final int meta) {

        // find the basedOn block
        final SubBlock sb = this.slabMetablock.getSubBlock(meta);

        return this.addSubBlock(meta, sb);
    }

    @Override
    public int getMetaByName(final String name) {
        final Integer i = this.nameMetaMap.get(name);
        if (i == null) {
            throw new IllegalArgumentException("Subblock " + name + " doesn't exist");
        }
        return i;
    }

    @Override
    public SubBlock getSubBlock(final int meta) {

        return this.subBlocksArray[this.getDistinctionMeta(meta)];
    }

    @Override
    public IIcon getIcon(final int side, final int meta) {
        return this.getSubBlock(meta).getIcon(side, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        for (final SubBlock sb : this.subBlocksArray) {
            if (sb != null) {
                sb.registerBlockIcons(par1IconRegister);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs getCreativeTabToDisplayOn() {
        return null;
    }

    @Override
    public void register() {
        // try to checking what the slabMetablock has
        // slabMetablock.getAllSubBlocks()
        for (int i = 0; i < this.slabMetablock.getAllSubBlocks().length; i++) {
            final SubBlock sb = this.slabMetablock.getSubBlock(i);
            if (sb != null) {
                this.addSubBlock(i, sb);
            }
        }
        GameRegistry.registerBlock(
                this,
                ItemSlabMulti.class,
                this.getUnlocalizedName(),
                (Block) this.slabMetablock,
                (Block) this);

        for (int i = 0; i < this.subBlocksArray.length; i++) {
            final SubBlock sb = this.subBlocksArray[i];
            if (sb != null) {
                this.setHarvestLevel(sb.getHarvestTool(0), sb.getHarvestLevel(0), i);
            }
        }
    }
}
