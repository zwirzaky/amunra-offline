package de.katzenpapst.amunra.block.machine;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.block.SubBlockMachine;
import de.katzenpapst.amunra.tile.TileEntityShuttleDock;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;

public class BlockShuttleDock extends SubBlockMachine {

    public BlockShuttleDock(final String name, final String texture) {
        super(name, texture);
        // TODO Auto-generated constructor stub
    }

    public BlockShuttleDock(final String name, final String texture, final String tool, final int harvestLevel) {
        super(name, texture, tool, harvestLevel);
        // TODO Auto-generated constructor stub
    }

    public BlockShuttleDock(final String name, final String texture, final String tool, final int harvestLevel,
            final float hardness, final float resistance) {
        super(name, texture, tool, harvestLevel, hardness, resistance);
        // TODO Auto-generated constructor stub
    }

    @Override
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new TileEntityShuttleDock();
    }

    @Override
    public boolean hasTileEntity(final int metadata) {
        return true;
    }

    @Override
    public void breakBlock(final World world, final int x0, final int y0, final int z0, final Block var5,
            final int var6) {
        final TileEntity te = world.getTileEntity(x0, y0, z0);
        if (te instanceof TileEntityShuttleDock) {
            ((TileEntityShuttleDock) te).onDestroy(te);
        }
    }

    @Override
    public void onBlockPlacedBy(final World world, final int x, final int y, final int z,
            final EntityLivingBase entityLiving, final ItemStack itemStack) {
        final TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityShuttleDock) {
            ((TileEntityShuttleDock) te).onCreate(new BlockVec3(x, y, z));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return AmunRa.dummyRendererId;
    }

    /*
     * @Override public boolean canReplace(World world, int x, int y, int z, int probablySide, ItemStack stack) { return
     * this.getSubBlock(stack.getItemDamage()).canReplace(world, x, y, z, probablySide, stack); }
     */
    @Override
    public boolean canPlaceBlockAt(final World world, final int x, final int y, final int z) {
        return world.getBlock(x, y, z).isReplaceable(world, x, y, z)
                && world.getBlock(x, y + 1, z).isReplaceable(world, x, y + 1, z);
    }

    @Override
    public boolean onMachineActivated(final World world, final int x, final int y, final int z,
            final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ) {
        final TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityShuttleDock) {
            ((TileEntityShuttleDock) te).onActivated(entityPlayer);
        }
        return true;
    }
}
