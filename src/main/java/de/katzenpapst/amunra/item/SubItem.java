package de.katzenpapst.amunra.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;

public class SubItem extends Item {

    protected String itemInfo = null;
    protected String fuckYouName = null;// fuck you, private

    public SubItem(final String name, final String assetName) {
        this.fuckYouName = name;
        this.setUnlocalizedName(name);
        this.setTextureName(AmunRa.TEXTUREPREFIX + assetName);
    }

    public SubItem(final String name, final String assetName, final String info) {
        this(name, assetName);
        this.itemInfo = info;
    }

    @Override
    public String getUnlocalizedName() {
        return this.fuckYouName;
    }

    public String getItemInfo() {
        return this.itemInfo;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return AmunRa.arTab;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(final ItemStack par1ItemStack) {
        return ClientProxyCore.galacticraftItem;
    }

    public int getFuelDuration() {
        return 0;
    }
}
