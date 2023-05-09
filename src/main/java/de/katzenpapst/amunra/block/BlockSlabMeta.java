package de.katzenpapst.amunra.block;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.helper.BlockMassHelper;
import de.katzenpapst.amunra.item.ItemSlabMulti;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class BlockSlabMeta extends BlockSlab implements IMetaBlock, IMassiveBlock {

    protected HashMap<String, Integer> nameMetaMap = null;
    protected SubBlock[] subBlocksArray = new SubBlock[8];
    protected BlockDoubleslabMeta doubleslabMetablock;

    public BlockSlabMeta(final String name, final Material material) {
        // I think the first parameter is true for doubleslabs...
        super(false, material);
        this.setBlockName(name);
        this.nameMetaMap = new HashMap<>();
    }

    @Override
    public String getUnlocalizedSubBlockName(final int meta) {
        return this.getSubBlock(meta).getUnlocalizedName() + ".slab";
    }

    public void setDoubleslabMeta(final BlockDoubleslabMeta doubleslabMetablock) {
        this.doubleslabMetablock = doubleslabMetablock;
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

    public BlockMetaPair addSubBlock(final int meta, final BlockMetaPair basedOn) {

        return this.addSubBlock(meta, ((IMetaBlock) basedOn.getBlock()).getSubBlock(basedOn.getMetadata()));
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

    public SubBlock[] getAllSubBlocks() {
        return this.subBlocksArray;
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
        return AmunRa.arTab;
    }

    @Override
    public Item getItemDropped(final int meta, final Random random, final int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public int damageDropped(final int meta) {
        return this.getDistinctionMeta(meta);
    }

    @Override
    public int getDamageValue(final World world, final int x, final int y, final int z) {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    public int quantityDropped(final int meta, final int fortune, final Random random) {
        return 1;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int i = 0; i < this.subBlocksArray.length; i++) {
            if (this.subBlocksArray[i] != null) {
                par3List.add(new ItemStack(par1, 1, i));
            }
        }
    }

    @Override
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y,
            final int z, final EntityPlayer player) {
        final int meta = world.getBlockMetadata(x, y, z);
        if (this.getSubBlock(meta) != null) {
            return new ItemStack(Item.getItemFromBlock(this), 1, this.getDistinctionMeta(meta));
        }

        return super.getPickBlock(target, world, x, y, z, player);
    }

    @Override
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y,
            final int z) {
        return this.getPickBlock(target, world, x, y, z, null);
    }

    @Override
    public void register() {
        // doubleslabMetablock
        GameRegistry
                .registerBlock(this, ItemSlabMulti.class, this.getUnlocalizedName(), this, this.doubleslabMetablock);

        for (int i = 0; i < this.subBlocksArray.length; i++) {
            final SubBlock sb = this.subBlocksArray[i];
            if (sb != null) {

                this.setHarvestLevel(sb.getHarvestTool(0), sb.getHarvestLevel(0), i);
            }
        }
    }

    @Override
    public String func_150002_b(final int meta) {
        // something like getNameByMeta
        // net.minecraft.item.ItemSlab calls this
        return this.getUnlocalizedName() + "." + this.getSubBlock(meta).getUnlocalizedName();
    }

    @Override
    public float getExplosionResistance(final Entity par1Entity, final World world, final int x, final int y,
            final int z, final double explosionX, final double explosionY, final double explosionZ) {
        final int metadata = world.getBlockMetadata(x, y, z);

        return this.getSubBlock(metadata)
                .getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    public float getBlockHardness(final World world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);

        return this.getSubBlock(meta).getBlockHardness(world, x, y, z);
    }

    @Override
    public int getNumPossibleSubBlocks() {
        return this.subBlocksArray.length;
    }

    @Override
    public int getExpDrop(final IBlockAccess world, final int metadata, final int fortune) {
        return this.getSubBlock(metadata).getExpDrop(world, 0, fortune);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    @Override
    public void onNeighborBlockChange(final World w, final int x, final int y, final int z, final Block nb) {
        final int meta = w.getBlockMetadata(x, y, z);
        this.getSubBlock(meta).onNeighborBlockChange(w, x, y, z, nb);
        super.onNeighborBlockChange(w, x, y, z, nb);
    }

    @Override
    public float getMass(final World w, final int x, final int y, final int z, final int meta) {
        final SubBlock sb = this.getSubBlock(meta);
        final float parentMass = BlockMassHelper.getBlockMass(w, sb, meta, x, y, z);
        // return half the mass, because slab
        return parentMass / 2.0F;
    }

    /**
     * Queries the class of tool required to harvest this block, if null is returned we assume that anything can harvest
     * this block.
     *
     * @param metadata
     * @return
     */
    @Override
    public String getHarvestTool(int metadata) {
        final SubBlock sb = this.getSubBlock(metadata);
        return sb == null ? null : sb.getHarvestTool(metadata);
    }

    /**
     * Queries the harvest level of this item stack for the specifred tool class, Returns -1 if this tool is not of the
     * specified type
     *
     * @param stack This item stack instance
     * @return Harvest level, or -1 if not the specified tool type.
     */
    @Override
    public int getHarvestLevel(int metadata) {
        final SubBlock sb = this.getSubBlock(metadata);
        return sb == null ? -1 : sb.getHarvestLevel(metadata);
    }

    /**
     * Checks if the specified tool type is efficient on this block, meaning that it digs at full speed.
     *
     * @param type
     * @param metadata
     * @return
     */
    @Override
    public boolean isToolEffective(final String type, final int metadata) {
        return this.getHarvestTool(metadata).equals(type);
    }
}
