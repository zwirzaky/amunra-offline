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
import de.katzenpapst.amunra.block.SubBlockMachine;
import de.katzenpapst.amunra.helper.CoordHelper;
import de.katzenpapst.amunra.tile.TileEntityIsotopeGenerator;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public class BlockIsotopeGenerator extends SubBlockMachine {

    // private IIcon iconFront = null;
    private IIcon iconOutput = null;
    private IIcon iconBlank = null;

    protected final String outputTexture;
    protected final String sideTexture;
    public final float energyGeneration;

    public BlockIsotopeGenerator(final String name, final String frontTexture, final String outputTexture, final String sideTexture,
            final float energyGeneration) {
        super(name, frontTexture);

        this.outputTexture = outputTexture;
        this.sideTexture = sideTexture;
        this.energyGeneration = energyGeneration;
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
        // this.blockIcon = reg.registerIcon(this.getTextureName());
        // this.iconFront = par1IconRegister.registerIcon(AmunRa.TEXTUREPREFIX + "machine_nuclear");
        this.iconBlank = par1IconRegister.registerIcon(this.sideTexture);
        this.iconOutput = par1IconRegister.registerIcon(this.outputTexture);
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
        // ForgeDirection output = CoordHelper.rotateForgeDirection(ForgeDirection.EAST, realMeta);// also north and
        // west

        if (side == ForgeDirection.UP.ordinal() || side == ForgeDirection.DOWN.ordinal()) {
            return this.iconBlank;
        }

        if (side == front.ordinal()) {
            return this.blockIcon;
        }
        // if(side == output.ordinal()) {
        return this.iconOutput;
        // }
        // return this.iconBlank;
    }

    @Override
    public boolean onMachineActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX,
            final float hitY, final float hitZ) {
        entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_ATOMBATTERY, world, x, y, z);
        return true;
        // return false;
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
        return new TileEntityIsotopeGenerator();
    }

    @Override
    public boolean hasTileEntity(final int metadata) {
        return true;
    }

    @Override
    public String getShiftDescription(final int meta) {
        return GCCoreUtil.translate("tile.isotopeGenerator.description");
    }

}
