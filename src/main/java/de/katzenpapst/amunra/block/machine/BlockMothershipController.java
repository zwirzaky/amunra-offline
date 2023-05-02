package de.katzenpapst.amunra.block.machine;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.GuiIds;
import de.katzenpapst.amunra.block.BlockMachineMeta;
import de.katzenpapst.amunra.helper.CoordHelper;
import de.katzenpapst.amunra.tile.TileEntityMothershipController;

public class BlockMothershipController extends AbstractBlockMothershipRestricted {

    protected final String frontTexture;
    private IIcon iconFront = null;

    public BlockMothershipController(final String name, final String frontTexture, final String sideTexture) {
        super(name, sideTexture);
        this.frontTexture = frontTexture;
    }

    /**
     *
     * @param side
     * @return
     */
    public static boolean isSideEnergyOutput(final int side) {
        // wait, wat?
        return false;
    }

    @Override
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        super.registerBlockIcons(par1IconRegister);
        this.iconFront = par1IconRegister.registerIcon(this.frontTexture);
        // this.blockIcon = iconFront;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        final int realMeta = ((BlockMachineMeta) this.parent).getRotationMeta(meta);
        // we have the front thingy at front.. but what is front?
        // east is the output
        // I think front is south
        final ForgeDirection front = CoordHelper.rotateForgeDirection(ForgeDirection.SOUTH, realMeta);
        // ForgeDirection output = CoordHelper.rotateForgeDirection(ForgeDirection.EAST, realMeta);

        if (side == front.ordinal()) {
            return this.iconFront;
        }

        return this.blockIcon;

    }

    /**
     * Called throughout the code as a replacement for ITileEntityProvider.createNewTileEntity Return the same thing you
     * would from that function. This will fall back to ITileEntityProvider.createNewTileEntity(World) if this block is
     * a ITileEntityProvider
     *
     * @param metadata The Metadata of the current block
     * @return A instance of a class extending TileEntity
     */
    @Override
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new TileEntityMothershipController();
    }

    @Override
    protected void openGui(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer) {
        entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_MOTHERSHIPCONTROLLER, world, x, y, z);
    }
}
