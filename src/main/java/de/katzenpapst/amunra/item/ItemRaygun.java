package de.katzenpapst.amunra.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import de.katzenpapst.amunra.entity.EntityBaseLaserArrow;
import de.katzenpapst.amunra.entity.EntityLaserArrow;

public class ItemRaygun extends ItemAbstractRaygun {

    protected IIcon itemEmptyIcon;

    public ItemRaygun(final String assetName) {
        super(assetName);
    }

    @Override
    protected EntityBaseLaserArrow createProjectile(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world) {
        return new EntityLaserArrow(world, entityPlayer);
    }

}
