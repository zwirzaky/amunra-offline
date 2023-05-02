package de.katzenpapst.amunra.block.machine.mothershipEngine;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.item.ARItems;
import de.katzenpapst.amunra.item.ItemDamagePair;
import de.katzenpapst.amunra.tile.TileEntityMothershipEngineJet;

public class MothershipEngineJetRocket extends MothershipEngineJetBase {

    protected ItemDamagePair item = null;

    public MothershipEngineJetRocket(final String name, final String texture, final String iconTexture) {
        super(name, texture, iconTexture);
    }

    @Override
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        super.registerBlockIcons(par1IconRegister);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        return this.blockIcon;
    }

    @Override
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new TileEntityMothershipEngineJet();
    }

    @Override
    protected ItemDamagePair getItem() {
        if (item == null) {
            item = ARItems.jetItem;
        }
        return item;
    }

}
