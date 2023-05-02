package de.katzenpapst.amunra.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.block.bush.SubBlockBush;
import de.katzenpapst.amunra.helper.BlockMassHelper;
import de.katzenpapst.amunra.item.ItemBlockMulti;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import micdoodle8.mods.galacticraft.api.block.IPlantableBlock;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class BlockBasicMeta extends Block implements IMetaBlock, IDetectableResource, IPlantableBlock,
        ITerraformableBlock, IMassiveBlock, IPartialSealableBlock {

    // protected ArrayList<SubBlock> subBlocks = null;
    protected SubBlock[] subBlocksArray;
    protected HashMap<String, Integer> nameMetaMap = null;

    /**
     * If true, the baseblock's name will be prefixed to the subblock's name for localisation
     */
    protected boolean prefixOwnBlockName = false;

    protected String blockNameFU;

    public BlockBasicMeta(final String name, final Material mat, final int numSubBlocks) {
        super(mat); // todo replace this
        subBlocksArray = new SubBlock[numSubBlocks];
        blockNameFU = name;
        // subBlocks = new ArrayList<SubBlock>(initialCapacity);
        nameMetaMap = new HashMap<>();
        setBlockName(name);
    }

    public BlockBasicMeta setPrefixOwnBlockName(final boolean set) {
        prefixOwnBlockName = set;
        return this;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(final World world, final int x, final int y, final int z, final Random rand) {
        if (!world.isRemote) {
            final int metadata = world.getBlockMetadata(x, y, z);
            getSubBlock(metadata).updateTick(world, x, y, z, rand);
        }
    }

    public BlockBasicMeta(final String name, final Material mat) {
        this(name, mat, 16);
    }

    @Override
    public int getMetaByName(final String name) {
        final Integer i = nameMetaMap.get(name);
        if (i == null) {
            throw new IllegalArgumentException("Subblock " + name + " doesn't exist in " + blockNameFU);
        }
        return i;
    }

    @Override
    public BlockMetaPair addSubBlock(final int meta, final SubBlock sb) {
        if (meta >= subBlocksArray.length || meta < 0) {
            throw new IllegalArgumentException(
                    "Meta " + meta + " must be <= " + (subBlocksArray.length - 1) + " && >= 0");
        }
        if (subBlocksArray[meta] != null) {
            throw new IllegalArgumentException("Meta " + meta + " is already in use in " + blockNameFU);
        }
        if (nameMetaMap.get(sb.getUnlocalizedName()) != null) {
            throw new IllegalArgumentException(
                    "Name " + sb.getUnlocalizedName() + " is already in use in " + blockNameFU);
        }
        sb.setParent(this);
        nameMetaMap.put(sb.getUnlocalizedName(), meta);
        subBlocksArray[meta] = sb;
        return new BlockMetaPair(this, (byte) meta);
    }

    @Override
    public SubBlock getSubBlock(int meta) {
        meta = getDistinctionMeta(meta);
        return subBlocksArray[meta];
    }

    @Override
    public int getNumPossibleSubBlocks() {
        return subBlocksArray.length;
    }

    /**
     * Registers the block with the GameRegistry and sets the harvestlevels for all subblocks
     */
    @Override
    public void register() {
        GameRegistry.registerBlock(this, ItemBlockMulti.class, this.getUnlocalizedName());

        for (int i = 0; i < subBlocksArray.length; i++) {
            final SubBlock sb = subBlocksArray[i];
            if (sb != null) {

                this.setHarvestLevel(sb.getHarvestTool(0), sb.getHarvestLevel(0), i);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        for (final SubBlock sb : subBlocksArray) {
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
    public float getExplosionResistance(final Entity par1Entity, final World world, final int x, final int y, final int z, final double explosionX,
            final double explosionY, final double explosionZ) {
        final int metadata = world.getBlockMetadata(x, y, z);

        return getSubBlock(metadata)
                .getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    public float getBlockHardness(final World world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);

        return getSubBlock(meta).getBlockHardness(world, x, y, z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int side, final int meta) {
        /*
         * Face 0 (Bottom Face) Face 1 (Top Face) Face 2 (Northern Face) Face 3 (Southern Face) Face 4 (Western Face)
         * Face 5 (Eastern Face)
         */
        return getSubBlock(meta).getIcon(side, meta);
    }

    @Override
    public Item getItemDropped(final int meta, final Random random, final int fortune) {
        final SubBlock sb = getSubBlock(meta);

        if (sb.dropsSelf()) {
            return Item.getItemFromBlock(this);
        }
        return sb.getItemDropped(0, random, fortune);
    }

    @Override
    public int damageDropped(final int meta) {
        final SubBlock sb = getSubBlock(meta);
        if (sb.dropsSelf()) {
            return getDistinctionMeta(meta);
        }
        return sb.damageDropped(0);
    }

    @Override
    public int getDamageValue(final World world, final int x, final int y, final int z) {
        return getDistinctionMeta(world.getBlockMetadata(x, y, z));
    }

    @Override
    public int quantityDropped(final int meta, final int fortune, final Random random) {
        final SubBlock sb = getSubBlock(meta);
        if (sb.dropsSelf()) {
            return 1;
        }
        return sb.quantityDropped(meta, fortune, random);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int i = 0; i < this.subBlocksArray.length; i++) {
            if (subBlocksArray[i] != null) {
                par3List.add(new ItemStack(par1, 1, i));
            }
        }
    }

    @Override
    public boolean hasTileEntity(final int meta) {
        final SubBlock sb = getSubBlock(meta);
        if (sb == null) return false;
        return sb.hasTileEntity(meta);
    }

    @Override
    public TileEntity createTileEntity(final World world, final int meta) {
        final SubBlock sb = getSubBlock(meta);
        return sb.createTileEntity(world, meta);
    }

    @Override
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z, final EntityPlayer player) {
        final int meta = world.getBlockMetadata(x, y, z);
        if (getSubBlock(meta) != null) {
            return new ItemStack(Item.getItemFromBlock(this), 1, getDistinctionMeta(meta));
        }

        return super.getPickBlock(target, world, x, y, z, player);
    }

    @Override
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
        return this.getPickBlock(target, world, x, y, z, null);
    }

    @Override
    public boolean getBlocksMovement(final IBlockAccess par1World, final int x, final int y, final int z) {
        final int meta = par1World.getBlockMetadata(x, y, z);

        return this.getSubBlock(meta).getBlocksMovement(par1World, x, y, z);
    }

    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX,
            final float hitY, final float hitZ) {
        final int meta = world.getBlockMetadata(x, y, z);
        return this.getSubBlock(meta).onBlockActivated(world, x, y, z, entityPlayer, side, hitX, hitY, hitZ);
    }

    @Override
    public int getExpDrop(final IBlockAccess world, final int metadata, final int fortune) {
        return this.getSubBlock(metadata).getExpDrop(world, 0, fortune);
    }

    @Override
    public boolean isTerraformable(final World world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        return this.getSubBlock(meta).isTerraformable(world, x, y, z);
    }

    @Override
    public int requiredLiquidBlocksNearby() {
        return 4; // I can't actually return the value of the subblock
    }

    @Override
    public boolean isPlantable(final int metadata) {
        return getSubBlock(metadata).isPlantable(0);
    }

    @Override
    public boolean isValueable(final int metadata) {
        return getSubBlock(metadata).isValueable(0);
    }

    @Override
    public Material getMaterial() {
        return this.blockMaterial;
    }

    @Override
    public boolean canSustainPlant(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection direction,
            final IPlantable plantable) {
        final Block block = plantable.getPlant(world, x, y + 1, z);
        final int blockMeta = plantable.getPlantMetadata(world, x, y + 1, z);
        // EnumPlantType plantType = plantable.getPlantType(world, x, y + 1, z);

        if (plantable instanceof SubBlockBush) {
            return ((SubBlockBush) plantable).canPlaceOn(block, blockMeta, 0);
        }

        return super.canSustainPlant(world, x, y, z, direction, plantable);
    }

    @Override
    public int getLightValue(final IBlockAccess world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        return this.getSubBlock(meta).getLightValue();
    }

    /**
     * This returns a complete list of items dropped from this block.
     *
     * @param world    The current world
     * @param x        X Position
     * @param y        Y Position
     * @param z        Z Position
     * @param metadata Current metadata
     * @param fortune  Breakers fortune level
     * @return A ArrayList containing all items this block drops
     */
    @Override
    public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int metadata, final int fortune) {
        final SubBlock sb = this.getSubBlock(metadata);
        if (sb.dropsSelf()) {
            return super.getDrops(world, x, y, z, metadata, fortune);
        }
        return sb.getDrops(world, x, y, z, 0, fortune);

    }

    @Override
    public String getUnlocalizedSubBlockName(final int meta) {
        if (prefixOwnBlockName) {
            return this.blockNameFU + "." + this.getSubBlock(meta).getUnlocalizedName();
        }
        return this.getSubBlock(meta).getUnlocalizedName();
    }

    @Override
    public int onBlockPlaced(final World w, final int x, final int y, final int z, final int side, final float hitX, final float hitY, final float hitZ, final int meta) {
        return this.getSubBlock(meta).onBlockPlaced(w, x, y, z, side, hitX, hitY, hitZ, meta);
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
        if (!(sb instanceof IMassiveBlock)) {
            return BlockMassHelper.guessBlockMass(w, sb, meta, x, y, z);
        }
        return ((IMassiveBlock) sb).getMass(w, x, y, z, meta);
    }

    @Override
    public boolean isSealed(final World world, final int x, final int y, final int z, final ForgeDirection direction) {
        final int meta = world.getBlockMetadata(x, y, z);
        final SubBlock sb = this.getSubBlock(meta);
        if (sb instanceof IPartialSealableBlock) {
            return ((IPartialSealableBlock) sb).isSealed(world, x, y, z, direction);
        }

        return true;
    }

    @Override
    public void breakBlock(final World world, final int x, final int y, final int z, final Block b, final int meta) {
        this.getSubBlock(meta).breakBlock(world, x, y, z, b, meta);
    }

    @Override
    public boolean canBlockStay(final World world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        return this.getSubBlock(meta).canBlockStay(world, x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        return this.getSubBlock(meta).getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public boolean canHarvestBlock(final EntityPlayer player, final int meta) {
        return getSubBlock(meta).canHarvestBlock(player, meta);
    }

    @Override
    public boolean canSilkHarvest(final World world, final EntityPlayer player, final int x, final int y, final int z, final int metadata) {
        return getSubBlock(metadata).canSilkHarvest(world, player, x, y, z, metadata);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        return this.getSubBlock(meta).getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    /**
     * Returns whether this block is collideable based on the arguments passed in
     * 
     * @param par1 block metaData
     * @param par2 whether the player right-clicked while holding a boat
     */
    @Override
    public boolean canCollideCheck(final int meta, final boolean withBoat) {
        return this.getSubBlock(meta).canCollideCheck(meta, withBoat);
        // return super.canCollideCheck(meta, withBoat);
    }
}
