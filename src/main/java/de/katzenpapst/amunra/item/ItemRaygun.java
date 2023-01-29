package de.katzenpapst.amunra.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import de.katzenpapst.amunra.entity.EntityBaseLaserArrow;
import de.katzenpapst.amunra.entity.EntityLaserArrow;

public class ItemRaygun extends ItemAbstractRaygun {

    protected IIcon itemEmptyIcon;

    public ItemRaygun(String assetName) {
        super(assetName);
    }

    @Override
    protected EntityBaseLaserArrow createProjectile(ItemStack itemStack, EntityPlayer entityPlayer, World world) {
        return new EntityLaserArrow(world, entityPlayer);
    }

}
