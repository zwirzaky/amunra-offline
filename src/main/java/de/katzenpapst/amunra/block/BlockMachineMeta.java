package de.katzenpapst.amunra.block;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.item.ItemBlockMulti;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectrical;
import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;

public class BlockMachineMeta extends BlockTileGC implements ItemBlockDesc.IBlockShiftDesc, IMetaBlock {

    protected SubBlockMachine[] subBlocksArray;
    protected HashMap<String, Integer> nameMetaMap = new HashMap<String, Integer>();
    protected String blockNameFU;

    public BlockMachineMeta(final String name, final Material material) {
        super(material);
        blockNameFU = name;
        subBlocksArray = new SubBlockMachine[4]; // const
        this.setBlockName(blockNameFU);
    }

    protected BlockMachineMeta(final String name, final Material material, final int numSubBlocks) {
        super(material);
        blockNameFU = name;
        subBlocksArray = new SubBlockMachine[numSubBlocks]; // const
        this.setBlockName(blockNameFU);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        final SubBlock leBlock = this.getSubBlock(meta);
        if (leBlock == null) {
            return null;
        }
        return leBlock.getIcon(side, meta);
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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        for (final SubBlock sb : subBlocksArray) {
            if (sb != null) {
                sb.registerBlockIcons(par1IconRegister);
            }
        }
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

    @Override
    public String getShiftDescription(final int meta) {
        return ((SubBlockMachine) this.getSubBlock(meta)).getShiftDescription(0);
    }

    @Override
    public boolean showDescription(final int meta) {
        return ((SubBlockMachine) this.getSubBlock(meta)).showDescription(0);
    }

    @Override
    public boolean onMachineActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX,
            final float hitY, final float hitZ) {
        return ((SubBlockMachine) this.getSubBlock(world.getBlockMetadata(x, y, z)))
                .onMachineActivated(world, x, y, z, entityPlayer, side, hitX, hitY, hitZ);
    }

    @Override
    public BlockMetaPair addSubBlock(final int meta, final SubBlock sb) {
        if (!(sb instanceof SubBlockMachine)) {
            throw new IllegalArgumentException("BlockMachineMeta can only accept SubBlockMachine");
        }
        if (meta >= subBlocksArray.length || meta < 0) {
            throw new IllegalArgumentException("Meta " + meta + " must be <= 3 && >= 0");
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
        subBlocksArray[meta] = (SubBlockMachine) sb;
        return new BlockMetaPair(this, (byte) meta);
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
    public SubBlock getSubBlock(final int meta) {
        return subBlocksArray[getDistinctionMeta(meta)];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs getCreativeTabToDisplayOn() {
        return AmunRa.arTab;
    }

    @Override
    public String getUnlocalizedSubBlockName(final int meta) {
        return this.getSubBlock(meta).getUnlocalizedName();
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
    public void register() {
        GameRegistry.registerBlock(this, ItemBlockMulti.class, this.getUnlocalizedName());

        for (int i = 0; i < subBlocksArray.length; i++) {
            final SubBlock sb = subBlocksArray[i];
            if (sb != null) {
                this.setHarvestLevel(sb.getHarvestTool(0), sb.getHarvestLevel(0), i);
            }
        }
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
    public TileEntity createTileEntity(final World world, final int metadata) {
        return this.getSubBlock(metadata).createTileEntity(world, metadata);
    }

    @Override
    public boolean onUseWrench(final World par1World, final int x, final int y, final int z, final EntityPlayer par5EntityPlayer, final int side,
            final float hitX, final float hitY, final float hitZ) {
        final int metadata = par1World.getBlockMetadata(x, y, z);
        if (!this.getSubBlock(metadata).canBeMoved(par1World, x, y, z)) {
            return false;
        }

        final int original = this.getRotationMeta(metadata);
        int change = 0;

        switch (original) {
            case 0:
                change = 3;
                break;
            case 3:
                change = 1;
                break;
            case 1:
                change = 2;
                break;
            case 2:
                change = 0;
                break;
        }

        change = this.addRotationMeta(this.getDistinctionMeta(metadata), change);

        final TileEntity te = par1World.getTileEntity(x, y, z);
        if (te instanceof TileBaseUniversalElectrical) {
            ((TileBaseUniversalElectrical) te).updateFacing();
        }

        par1World.setBlockMetadataWithNotify(x, y, z, change, 3);
        return true;
    }

    @Override
    public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase entityLiving, final ItemStack itemStack) {
        final int metadata = world.getBlockMetadata(x, y, z);

        final int dist = this.getDistinctionMeta(metadata);

        final int angle = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int change = 0;

        switch (angle) {
            case 0:
                change = 1;
                break;
            case 1:
                change = 2;
                break;
            case 2:
                change = 0;
                break;
            case 3:
                change = 3;
                break;
        }

        change = this.addRotationMeta(dist, change);

        world.setBlockMetadataWithNotify(x, y, z, change, 3);

        final TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileBaseUniversalElectrical) {
            ((TileBaseUniversalElectrical) tile).updateFacing();
        }

        final SubBlock sb = this.getSubBlock(dist);
        if (sb != null) {
            sb.onBlockPlacedBy(world, x, y, z, entityLiving, itemStack);
        }
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

    @Override
    public boolean getBlocksMovement(final IBlockAccess par1World, final int x, final int y, final int z) {
        final int meta = par1World.getBlockMetadata(x, y, z);

        return this.getSubBlock(meta).getBlocksMovement(par1World, x, y, z);
    }

    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX,
            final float hitY, final float hitZ) {
        final int meta = world.getBlockMetadata(x, y, z);

        // do the wrench stuff, too
        // ORIG BEGIN
        /**
         * Check if the player is holding a wrench or an electric item. If so, call the wrench event.
         */
        // handle the wrench stuff in the metablock
        if (this.isUsableWrench(entityPlayer, entityPlayer.inventory.getCurrentItem(), x, y, z)) {
            this.damageWrench(entityPlayer, entityPlayer.inventory.getCurrentItem(), x, y, z);

            if (entityPlayer.isSneaking()) {
                if (this.onSneakUseWrench(world, x, y, z, entityPlayer, side, hitX, hitY, hitZ)) {
                    return true;
                }
            }

            if (this.onUseWrench(world, x, y, z, entityPlayer, side, hitX, hitY, hitZ)) {
                return true;
            }
        }
        // handle the other stuff in the subblock
        final SubBlockMachine sb = (SubBlockMachine) this.getSubBlock(meta);

        if (entityPlayer.isSneaking()) {
            if (sb.onSneakMachineActivated(world, x, y, z, entityPlayer, side, hitX, hitY, hitZ)) {
                return true;
            }
        }

        return sb.onMachineActivated(world, x, y, z, entityPlayer, side, hitX, hitY, hitZ);
        // ORIG END
    }

    @Override
    public boolean hasTileEntity(final int metadata) {
        return this.getSubBlock(metadata).hasTileEntity(metadata);
    }

    @Override
    public void breakBlock(final World world, final int x, final int y, final int z, final Block block, final int metadata) {
        this.getSubBlock(metadata).breakBlock(world, x, y, z, block, metadata);
        super.breakBlock(world, x, y, z, block, metadata);
    }

    //////////////////////////////////////////////////// EVENTS ////////////////////////////////////////////////////

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

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    @Override
    public int onBlockPlaced(final World w, final int x, final int y, final int z, final int side, final float hitX, final float hitY, final float hitZ, final int meta) {
        return this.getSubBlock(meta).onBlockPlaced(w, x, y, z, side, hitX, hitY, hitZ, meta);
    }

    @Override
    public int getNumPossibleSubBlocks() {
        return subBlocksArray.length;
    }

    @Override
    public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z, final boolean willHarvest) {
        final int meta = world.getBlockMetadata(x, y, z);
        return this.getSubBlock(meta).removedByPlayer(world, player, x, y, z, willHarvest);
    }

    @Override
    @Deprecated
    public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        this.getSubBlock(meta).removedByPlayer(world, player, x, y, z);
        return false;
    }

    @Override
    public boolean canDropFromExplosion(final Explosion kaboom) {
        return true;
    }

    @Override
    public boolean canReplace(final World world, final int x, final int y, final int z, final int probablySide, final ItemStack stack) {
        if (stack != null) {
            return this.getSubBlock(stack.getItemDamage()).canReplace(world, x, y, z, probablySide, stack);
        }
        return super.canReplace(world, x, y, z, probablySide, stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(final World world, final int x, final int y, final int z, final Random rand) {
        final int meta = world.getBlockMetadata(x, y, z);
        this.getSubBlock(meta).randomDisplayTick(world, x, y, z, rand);
    }
}
