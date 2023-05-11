package de.katzenpapst.amunra.block.machine.mothershipEngine;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import de.katzenpapst.amunra.item.ARItems;
import de.katzenpapst.amunra.item.ItemDamagePair;
import de.katzenpapst.amunra.tile.TileEntityMothershipEngineJet;

public class MothershipEngineJetRocket extends MothershipEngineJetBase {

    @Deprecated
    protected ItemDamagePair item;

    public MothershipEngineJetRocket(final String name, final String texture, final String iconTexture) {
        super(name, texture, iconTexture);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityMothershipEngineJet();
    }

    @Override
    protected ItemDamagePair getItem() {
        return ARItems.jetItem;
    }

}
