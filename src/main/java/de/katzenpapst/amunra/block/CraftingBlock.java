package de.katzenpapst.amunra.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.GuiIds;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;

public class CraftingBlock extends SubBlock {

    @SideOnly(Side.CLIENT)
    protected IIcon blockIconBottom;
    @SideOnly(Side.CLIENT)
    protected IIcon blockIconSide;

    public CraftingBlock(final String name) {
        // super
        super(name, "amunra:crafter", "pickaxe", 1, 5.0F, 5.0F);
        this.setStepSound(Block.soundTypeMetal);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(this.getTextureName());
        this.blockIconSide = par1IconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_side");
        this.blockIconBottom = par1IconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine");

    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int side, final int meta) {
        return switch (side) {
            case 0 -> this.blockIconBottom;
            case 1 -> this.blockIcon;
            default -> this.blockIconSide;
        };
    }

    /**
     *
     *
     * @param world The World Object.
     * @param x     , y, z The coordinate of the block.
     * @param side  The side the player clicked on.
     * @param hitX  , hitY, hitZ The position the player clicked on relative to the block.
     */
    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX,
            final float hitY, final float hitZ) {

        // onBlockActivated

        if (world.isRemote) {
            return true;
        }
        entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_CRAFTING, world, x, y, z);
        return true;
    }

}
// blockRegistry.addObject(58, "crafting_table", (new
// BlockWorkbench()).setHardness(2.5F).setStepSound(soundTypeWood).setBlockName("workbench").setBlockTextureName("crafting_table"));
