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
    protected IIcon blockIconBottom, blockIconSide;

    public CraftingBlock(final String name) {
        super(name, "amunra:crafter", "pickaxe", 1, 5.0F, 5.0F);
        this.setStepSound(Block.soundTypeMetal);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(this.getTextureName());
        this.blockIconSide = reg.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_side");
        this.blockIconBottom = reg.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine");

    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return switch (side) {
            case 0 -> this.blockIconBottom;
            case 1 -> this.blockIcon;
            default -> this.blockIconSide;
        };
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
            float subY, float subZ) {
        if (!worldIn.isRemote) {
            player.openGui(AmunRa.instance, GuiIds.GUI_CRAFTING, worldIn, x, y, z);
        }
        return true;
    }

}
