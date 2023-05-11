package de.katzenpapst.amunra.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockWood extends SubBlock {

    protected final String textureTop;
    protected IIcon topIcon;

    public SubBlockWood(final String name, final String textureSide, final String textureTop) {
        super(name, textureSide, "axe", 0);
        this.textureTop = textureTop;
    }

    public SubBlockWood(final String name, final String textureSide, final String textureTop, final String tool,
            final int harvestLevel) {
        super(name, textureSide, tool, harvestLevel);
        this.textureTop = textureTop;
    }

    public SubBlockWood(final String name, final String textureSide, final String textureTop, final String tool,
            final int harvestLevel, final float hardness, final float resistance) {
        super(name, textureSide, tool, harvestLevel, hardness, resistance);
        this.textureTop = textureTop;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister reg) {
        this.blockIcon = reg.registerIcon(this.getTextureName());
        this.topIcon = reg.registerIcon(this.textureTop);

    }

    /**
     * @param meta expects to be ONLY the relevant two bits which describe rotation
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return switch (meta) {
            // Facing up/down
            case 0 -> side == 0 || side == 1 ? this.topIcon : this.blockIcon;
            // Facing east/west
            case 1 -> side == 4 || side == 5 ? this.topIcon : this.blockIcon;
            // Facing north/south
            case 2 -> side == 2 || side == 3 ? this.topIcon : this.blockIcon;
            // Only bark / unexpected cases
            default -> this.blockIcon;
        };
    }

}
