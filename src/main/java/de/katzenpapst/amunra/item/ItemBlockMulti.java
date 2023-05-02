package de.katzenpapst.amunra.item;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.block.IMetaBlock;
import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;

/**
 * Item for multiblocks
 * 
 * @author katzenpapst
 *
 */
public class ItemBlockMulti extends ItemBlockDesc {

    public ItemBlockMulti(final Block block) {
        super(block); // it ends up in field_150939_a
        this.setMaxDamage(0); // ?
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(final ItemStack par1ItemStack) {
        // colors the name
        return ClientProxyCore.galacticraftItem;
    }

    @Override
    public String getUnlocalizedName(final ItemStack itemstack) {
        final String subBlockName = ((IMetaBlock) field_150939_a).getUnlocalizedSubBlockName(itemstack.getItemDamage());
        return "tile." + subBlockName;
    }

    @Override
    public String getUnlocalizedName() {
        return this.field_150939_a.getUnlocalizedName() + ".0";
    }

    @Override
    public int getMetadata(final int meta) {
        return meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int damage) {
        return ((IMetaBlock) field_150939_a).getSubBlock(damage).getIcon(1, 0);
    }
}
