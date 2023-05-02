package de.katzenpapst.amunra.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.tile.TileEntityARChest;
import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public class BlockARChest extends BlockContainer
        implements ITileEntityProvider, ItemBlockDesc.IBlockShiftDesc, IMassiveBlock {

    protected final Random random = new Random();

    protected float mass = 1.0F;

    protected final ResourceLocation smallChestTexture;
    protected final ResourceLocation bigChestTexture;
    protected final String fallbackTexture;

    protected boolean canDoublechest = true;

    protected String shiftDescription = null;

    public BlockARChest(final Material material, final String blockName, final ResourceLocation smallChestTexture,
            final ResourceLocation bigChestTexture, final String fallbackTexture) {
        super(material);

        this.setHardness(2.5F);
        this.setResistance(100.0F);
        this.setStepSound(Block.soundTypeStone);
        this.setBlockName(blockName);

        this.smallChestTexture = smallChestTexture;
        this.bigChestTexture = bigChestTexture;
        this.fallbackTexture = fallbackTexture;
    }

    public BlockARChest(final Material material, final String blockName, final ResourceLocation smallChestTexture,
            final String fallbackTexture) {
        super(material);

        this.setHardness(2.5F);
        this.setResistance(100.0F);
        this.setStepSound(Block.soundTypeStone);
        this.setBlockName(blockName);

        this.smallChestTexture = smallChestTexture;
        this.bigChestTexture = null;
        this.fallbackTexture = fallbackTexture;
    }

    public ResourceLocation getSmallTexture() {
        return this.smallChestTexture;
    }

    public ResourceLocation getLargeTexture() {
        return this.bigChestTexture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(this.fallbackTexture);
    }

    public void setShiftDescription(final String str) {
        this.shiftDescription = str;
    }

    @Override
    public String getShiftDescription(final int meta) {
        if (this.shiftDescription != null) {
            return GCCoreUtil.translate(this.shiftDescription);
        }
        return null;
    }

    @Override
    public boolean showDescription(final int meta) {
        return this.shiftDescription != null;
    }

    @Override
    public TileEntity createNewTileEntity(final World world, final int meta) {
        return new TileEntityARChest();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs getCreativeTabToDisplayOn() {
        return AmunRa.arTab;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return AmunRa.chestRenderId;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
        if (!this.canDoublechest) {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
            return;
        }
        if (this.isSameBlock(world, x, y, z - 1)) {
            this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
        } else if (this.isSameBlock(world, x, y, z + 1)) {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
        } else if (this.isSameBlock(world, x - 1, y, z)) {
            this.setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        } else if (this.isSameBlock(world, x + 1, y, z)) {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
        } else {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        }
    }

    @Override
    public void onBlockAdded(final World world, final int x, final int y, final int z) {
        super.onBlockAdded(world, x, y, z);
        if (!this.canDoublechest) {
            return;
        }

        this.unifyAdjacentChests(world, x, y, z);

        if (this.isSameBlock(world, x, y, z - 1)) {
            this.unifyAdjacentChests(world, x, y, z - 1);
        } else if (this.isSameBlock(world, x, y, z + 1)) {
            this.unifyAdjacentChests(world, x, y, z + 1);
        } else if (this.isSameBlock(world, x - 1, y, z)) {
            this.unifyAdjacentChests(world, x - 1, y, z);
        } else if (this.isSameBlock(world, x + 1, y, z)) {
            this.unifyAdjacentChests(world, x + 1, y, z);
        }
    }

    @Override
    public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase user, final ItemStack stack) {
        byte meta = 0;
        final int userRotation = MathHelper.floor_double(user.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        switch (userRotation) {
            case 0:
                meta = 2;
                break;
            case 1:
                meta = 5;
                break;
            case 2:
                meta = 3;
                break;
            case 3:
                meta = 4;
                break;
        }

        if (!this.canDoublechest) {
            world.setBlockMetadataWithNotify(x, y, z, meta, 3);
            return;
        }

        final boolean zNegSame = this.isSameBlock(world, x, y, z - 1);
        final boolean zPosSame = this.isSameBlock(world, x, y, z + 1);
        final boolean xNegSame = this.isSameBlock(world, x - 1, y, z);
        final boolean xPosSame = this.isSameBlock(world, x + 1, y, z);

        if (!zNegSame && !zPosSame && !xNegSame && !xPosSame) {
            world.setBlockMetadataWithNotify(x, y, z, meta, 3);
        } else {
            if ((zNegSame || zPosSame) && (meta == 4 || meta == 5)) {
                if (zNegSame) {
                    world.setBlockMetadataWithNotify(x, y, z - 1, meta, 3);
                } else {
                    world.setBlockMetadataWithNotify(x, y, z + 1, meta, 3);
                }

                world.setBlockMetadataWithNotify(x, y, z, meta, 3);
            }

            if ((xNegSame || xPosSame) && (meta == 2 || meta == 3)) {
                if (xNegSame) {
                    world.setBlockMetadataWithNotify(x - 1, y, z, meta, 3);
                } else {
                    world.setBlockMetadataWithNotify(x + 1, y, z, meta, 3);
                }

                world.setBlockMetadataWithNotify(x, y, z, meta, 3);
            }
        }
    }

    public void unifyAdjacentChests(final World world, final int x, final int y, final int z) {
        if (!this.canDoublechest) {
            return;
        }
        if (!world.isRemote) {
            final boolean zNegSame = this.isSameBlock(world, x, y, z - 1);
            final boolean zPosSame = this.isSameBlock(world, x, y, z + 1);
            final boolean xNegSame = this.isSameBlock(world, x - 1, y, z);
            final boolean xPosSame = this.isSameBlock(world, x + 1, y, z);

            final Block nZNeg = world.getBlock(x, y, z - 1);
            final Block nZPos = world.getBlock(x, y, z + 1);
            final Block nXNeg = world.getBlock(x - 1, y, z);
            final Block nXPos = world.getBlock(x + 1, y, z);
            Block otherNeighbour1;
            Block otherNeighbour2;
            byte meta;
            int otherMeta;

            if (!zNegSame && !zPosSame) {
                if (!xNegSame && !xPosSame) {
                    meta = 3;

                    if (nZNeg.func_149730_j() && !nZPos.func_149730_j()) {
                        meta = 3;
                    }

                    if (nZPos.func_149730_j() && !nZNeg.func_149730_j()) {
                        meta = 2;
                    }

                    if (nXNeg.func_149730_j() && !nXPos.func_149730_j()) {
                        meta = 5;
                    }

                    if (nXPos.func_149730_j() && !nXNeg.func_149730_j()) {
                        meta = 4;
                    }
                } else {
                    otherNeighbour1 = world.getBlock(xNegSame ? x - 1 : x + 1, y, z - 1);
                    otherNeighbour2 = world.getBlock(xNegSame ? x - 1 : x + 1, y, z + 1);
                    meta = 3;
                    if (xNegSame) {
                        otherMeta = world.getBlockMetadata(x - 1, y, z);
                    } else {
                        otherMeta = world.getBlockMetadata(x + 1, y, z);
                    }

                    if (otherMeta == 2) {
                        meta = 2;
                    }

                    if ((nZNeg.func_149730_j() || otherNeighbour1.func_149730_j()) && !nZPos.func_149730_j()
                            && !otherNeighbour2.func_149730_j()) {
                        meta = 3;
                    }

                    if ((nZPos.func_149730_j() || otherNeighbour2.func_149730_j()) && !nZNeg.func_149730_j()
                            && !otherNeighbour1.func_149730_j()) {
                        meta = 2;
                    }
                }
            } else {
                otherNeighbour1 = world.getBlock(x - 1, y, nZNeg == this ? z - 1 : z + 1);
                otherNeighbour2 = world.getBlock(x + 1, y, nZNeg == this ? z - 1 : z + 1);
                meta = 5;
                if (nZNeg == this) {
                    otherMeta = world.getBlockMetadata(x, y, z - 1);
                } else {
                    otherMeta = world.getBlockMetadata(x, y, z + 1);
                }

                if (otherMeta == 4) {
                    meta = 4;
                }

                if ((nXNeg.func_149730_j() || otherNeighbour1.func_149730_j()) && !nXPos.func_149730_j()
                        && !otherNeighbour2.func_149730_j()) {
                    meta = 5;
                }

                if ((nXPos.func_149730_j() || otherNeighbour2.func_149730_j()) && !nXNeg.func_149730_j()
                        && !otherNeighbour1.func_149730_j()) {
                    meta = 4;
                }
            }

            world.setBlockMetadataWithNotify(x, y, z, meta, 3);
        }
    }

    @Override
    public boolean canPlaceBlockAt(final World world, final int x, final int y, final int z) {
        if (!this.canDoublechest) {
            return super.canPlaceBlockAt(world, x, y, z);
        }
        int numSameNeighbours = 0;

        if (this.isSameBlock(world, x - 1, y, z)) {
            ++numSameNeighbours;
        }

        if (this.isSameBlock(world, x + 1, y, z)) {
            ++numSameNeighbours;
        }

        if (this.isSameBlock(world, x, y, z - 1)) {
            ++numSameNeighbours;
        }

        if (this.isSameBlock(world, x, y, z + 1)) {
            ++numSameNeighbours;
        }

        return numSameNeighbours <= 1 && (this.isThereANeighborChest(world, x - 1, y, z) ? false
                : !this.isThereANeighborChest(world, x + 1, y, z) && !this.isThereANeighborChest(world, x, y, z - 1)
                        && !this.isThereANeighborChest(world, x, y, z + 1));
    }

    /**
     * Checks the neighbor blocks to see if there is a chest there. Args: world, x, y, z
     */
    private boolean isThereANeighborChest(final World world, final int x, final int y, final int z) {
        if (!this.canDoublechest || !this.isSameBlock(world, x, y, z)) {
            return false;
        }

        if (this.isSameBlock(world, x - 1, y, z) || this.isSameBlock(world, x + 1, y, z) || this.isSameBlock(world, x, y, z - 1) || this.isSameBlock(world, x, y, z + 1)) {
            return true;
        }
        return false;
    }

    @Override
    public void onNeighborBlockChange(final World world, final int x, final int y, final int z, final Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);

        final TileEntityARChest tileEntity = (TileEntityARChest) world.getTileEntity(x, y, z);

        if (tileEntity != null) {
            tileEntity.updateContainingBlockInfo();
        }
    }

    protected boolean isSameBlock(final IBlockAccess world, final int x, final int y, final int z) {
        final Block b = world.getBlock(x, y, z);
        return b == this;
    }

    @Override
    public void breakBlock(final World world, final int x, final int y, final int z, final Block block, final int meta) {
        final TileEntityARChest tileEntity = (TileEntityARChest) world.getTileEntity(x, y, z);

        if (tileEntity != null) {
            for (int i = 0; i < tileEntity.getSizeInventory(); ++i) {
                final ItemStack stack = tileEntity.getStackInSlot(i);

                if (stack != null) {
                    final float rand1 = this.random.nextFloat() * 0.8F + 0.1F;
                    final float rand2 = this.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem itemEntity;

                    for (final float randOffset = this.random.nextFloat() * 0.8F + 0.1F; stack.stackSize > 0; world
                            .spawnEntityInWorld(itemEntity)) {
                        int droppedStackSize = this.random.nextInt(21) + 10;

                        if (droppedStackSize > stack.stackSize) {
                            droppedStackSize = stack.stackSize;
                        }

                        stack.stackSize -= droppedStackSize;
                        itemEntity = new EntityItem(
                                world,
                                x + rand1,
                                y + rand2,
                                z + randOffset,
                                new ItemStack(stack.getItem(), droppedStackSize, stack.getItemDamage()));
                        final float yOffset = 0.05F;
                        itemEntity.motionX = (float) this.random.nextGaussian() * yOffset;
                        itemEntity.motionY = (float) this.random.nextGaussian() * yOffset + 0.2F;
                        itemEntity.motionZ = (float) this.random.nextGaussian() * yOffset;

                        if (stack.hasTagCompound()) {
                            itemEntity.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
                        }
                    }
                }
            }
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    /**
     * Called upon block activation (right click on the block.) World world, int x, int y, int z, EntityPlayer player,
     * int side, float xOffset, float yOffset, float zOffset
     */
    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float xOffset,
            final float yOffset, final float zOffset) {
        Object tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity == null || world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN) || TileEntityARChest.isOcelotBlockingChest(world, x, y, z)) {
            return true;
        }
        if (this.canDoublechest) {
            if (world.getBlock(x - 1, y, z) == this && (world.isSideSolid(x - 1, y + 1, z, ForgeDirection.DOWN)
                    || TileEntityARChest.isOcelotBlockingChest(world, x - 1, y, z))) {
                return true;
            }
            if (world.getBlock(x + 1, y, z) == this && (world.isSideSolid(x + 1, y + 1, z, ForgeDirection.DOWN)
                    || TileEntityARChest.isOcelotBlockingChest(world, x + 1, y, z))) {
                return true;
            }
            if (world.getBlock(x, y, z - 1) == this && (world.isSideSolid(x, y + 1, z - 1, ForgeDirection.DOWN)
                    || TileEntityARChest.isOcelotBlockingChest(world, x, y, z - 1))) {
                return true;
            }
            if (world.getBlock(x, y, z + 1) == this && (world.isSideSolid(x, y + 1, z + 1, ForgeDirection.DOWN)
                    || TileEntityARChest.isOcelotBlockingChest(world, x, y, z + 1))) {
                return true;
            }

            if (this.isSameBlock(world, x - 1, y, z)) {
                tileEntity = new InventoryLargeChest(
                        "container.chestDouble",
                        (TileEntityARChest) world.getTileEntity(x - 1, y, z),
                        (IInventory) tileEntity);
            }

            if (this.isSameBlock(world, x + 1, y, z)) {
                tileEntity = new InventoryLargeChest(
                        "container.chestDouble",
                        (IInventory) tileEntity,
                        (TileEntityARChest) world.getTileEntity(x + 1, y, z));
            }

            if (this.isSameBlock(world, x, y, z - 1)) {
                tileEntity = new InventoryLargeChest(
                        "container.chestDouble",
                        (TileEntityARChest) world.getTileEntity(x, y, z - 1),
                        (IInventory) tileEntity);
            }

            if (this.isSameBlock(world, x, y, z + 1)) {
                tileEntity = new InventoryLargeChest(
                        "container.chestDouble",
                        (IInventory) tileEntity,
                        (TileEntityARChest) world.getTileEntity(x, y, z + 1));
            }
        }

        if (world.isRemote) {
            return true;
        }
        player.displayGUIChest((IInventory) tileEntity);
        return true;

    }

    public void setMass(final float mass) {
        this.mass = mass;
    }

    @Override
    public float getMass(final World w, final int x, final int y, final int z, final int meta) {
        return this.mass;
    }
}
