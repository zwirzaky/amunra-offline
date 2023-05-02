package de.katzenpapst.amunra.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public class ItemBasicMulti extends Item implements ItemBlockDesc.IBlockShiftDesc {
    // public static final String[] names = { "solar_module_0", "solar_module_1", "rawSilicon", "ingotCopper",
    // "ingotTin", "ingotAluminum", "compressedCopper", "compressedTin", "compressedAluminum", "compressedSteel",
    // "compressedBronze", "compressedIron", "waferSolar", "waferBasic", "waferAdvanced", "dehydratedApple",
    // "dehydratedCarrot", "dehydratedMelon", "dehydratedPotato", "frequencyModule" };

    // protected IIcon[] icons = new IIcon[ItemBasic.names.length];
    protected ArrayList<SubItem> subItems = null;

    protected HashMap<String, Integer> nameDamageMapping = null;

    public ItemBasicMulti(final String name) {
        super();
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setUnlocalizedName(name);
        subItems = new ArrayList<>();
        nameDamageMapping = new HashMap<>();
    }

    public ItemStack getItemStack(final String name, final int count) {

        return getItemStack(getDamageByName(name), count);
    }

    public ItemStack getItemStack(final int damage, final int count) {
        // ensure it exists
        if (subItems.get(damage) == null) {
            throw new IllegalArgumentException(
                    "SubItem with damage " + damage + " does not exist in " + this.getUnlocalizedName());
        }

        return new ItemStack(this, count, damage);
    }

    public ItemDamagePair addSubItem(final int damage, final SubItem item) {
        if (damage >= subItems.size()) {
            subItems.ensureCapacity(damage);
            while (damage >= subItems.size()) {
                subItems.add(null);
            }
        }
        if (subItems.get(damage) != null) {
            throw new IllegalArgumentException(
                    "SubItem with damage " + damage + " already exists in " + this.getUnlocalizedName());
        }
        final String itemName = item.getUnlocalizedName();
        if (nameDamageMapping.get(itemName) != null) {
            throw new IllegalArgumentException(
                    "SubItem with name " + itemName + " already exists in " + this.getUnlocalizedName());
        }
        nameDamageMapping.put(itemName, damage);
        subItems.add(damage, item);
        return new ItemDamagePair(this, damage);
    }

    public int getDamageByName(final String name) {
        if (!nameDamageMapping.containsKey(name)) {
            return -1;
        }
        return nameDamageMapping.get(name);
    }

    public void register() {
        GameRegistry.registerItem(this, this.getUnlocalizedName(), AmunRa.MODID);
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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        for (final SubItem item : subItems) {
            if (item == null) continue;

            item.registerIcons(iconRegister);
            // item.icon = iconRegister.registerIcon(item.getIconString());
        }
    }

    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return this.getUnlocalizedName() + "." + getSubItem(itemStack.getItemDamage()).getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int damage) {
        return subItems.get(damage).getIconFromDamage(0);
    }

    @Override
    public IIcon getIcon(final ItemStack stack, final int pass) {
        return subItems.get(stack.getItemDamage()).getIcon(stack, pass);
    }

    /**
     * Returns the icon index of the stack given as argument.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(final ItemStack stack) {
        return subItems.get(stack.getItemDamage()).getIconIndex(stack);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int i = 0; i < subItems.size(); i++) {
            if (subItems.get(i) == null) continue;
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public int getMetadata(final int par1) {
        return par1;
    }

    public SubItem getSubItem(final int damage) {
        if (damage >= subItems.size() || subItems.get(damage) == null) {
            throw new IllegalArgumentException(
                    "Requested invalid SubItem " + damage + " from " + this.getUnlocalizedName());
        }
        return subItems.get(damage);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        final SubItem item = getSubItem(par1ItemStack.getItemDamage());

        item.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);

        String info = item.getItemInfo();
        if (info != null) {
            info = GCCoreUtil.translate(info);
            par3List.addAll(FMLClientHandler.instance().getClient().fontRenderer.listFormattedStringToWidth(info, 150));
        }
    }

    @Override
    public ItemStack onEaten(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        return getSubItem(par1ItemStack.getItemDamage()).onEaten(par1ItemStack, par2World, par3EntityPlayer);
    }

    @Override
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return getSubItem(par1ItemStack.getItemDamage()).getMaxItemUseDuration(par1ItemStack);
    }

    @Override
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return getSubItem(par1ItemStack.getItemDamage()).getItemUseAction(par1ItemStack);
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        return getSubItem(par1ItemStack.getItemDamage()).onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
    }

    @Override
    public boolean onLeftClickEntity(final ItemStack itemStack, final EntityPlayer player, final Entity entity) {
        return getSubItem(itemStack.getItemDamage()).onLeftClickEntity(itemStack, player, entity);
    }

    public int getFuelDuration(final int meta) {
        return getSubItem(meta).getFuelDuration();
    }

    @Override
    public String getShiftDescription(final int meta) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean showDescription(final int meta) {
        // TODO Auto-generated method stub
        return false;
    }
}
