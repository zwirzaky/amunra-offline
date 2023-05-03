package de.katzenpapst.amunra.block.machine;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.GuiIds;
import de.katzenpapst.amunra.block.SubBlockMachine;
import de.katzenpapst.amunra.tile.TileEntityHydroponics;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public class BlockHydroponics extends SubBlockMachine {

    public BlockHydroponics(final String name, final String sideTexture) {
        super(name, sideTexture);
    }

    @Override
    public boolean onMachineActivated(final World world, final int x, final int y, final int z,
            final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ) {
        entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_HYDROPONICS, world, x, y, z);
        return true;
        // return false;
    }

    @Override
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new TileEntityHydroponics();
    }

    @Override
    public boolean hasTileEntity(final int metadata) {
        return true;
    }

    @Override
    public String getShiftDescription(final int meta) {
        return GCCoreUtil.translate("tile.hydroponics.description");
    }

    @Override
    public void breakBlock(final World world, final int x, final int y, final int z, final Block var5, final int var6) {
        // drop harvest items
        final TileEntity te = world.getTileEntity(x, y, z);
        if (te == null || !(te instanceof TileEntityHydroponics)) {
            return;
        }
        final ItemStack[] harvest = ((TileEntityHydroponics) te).getHarvest();
        for (final ItemStack stack : harvest) {
            if (stack != null) {
                final Random random = new Random();
                final float randX = random.nextFloat() * 0.8F + 0.1F;
                final float randY = random.nextFloat() * 0.8F + 0.1F;
                final float randZ = random.nextFloat() * 0.8F + 0.1F;

                while (stack.stackSize > 0) {
                    int randStackSize = random.nextInt(21) + 10;

                    if (randStackSize > stack.stackSize) {
                        randStackSize = stack.stackSize;
                    }

                    stack.stackSize -= randStackSize;
                    final EntityItem itemEntity = new EntityItem(
                            world,
                            x + randX,
                            y + randY,
                            z + randZ,
                            new ItemStack(stack.getItem(), randStackSize, stack.getItemDamage()));

                    if (stack.hasTagCompound()) {
                        itemEntity.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
                    }

                    final float someFactor = 0.05F;
                    itemEntity.motionX = (float) random.nextGaussian() * someFactor;
                    itemEntity.motionY = (float) random.nextGaussian() * someFactor + 0.2F;
                    itemEntity.motionZ = (float) random.nextGaussian() * someFactor;
                    world.spawnEntityInWorld(itemEntity);
                }
            }
        }
    }

}
