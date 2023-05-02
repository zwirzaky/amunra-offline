package de.katzenpapst.amunra.client.gui.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.client.gui.GuiMothershipSettings;
import de.katzenpapst.amunra.helper.PlayerID;
import de.katzenpapst.amunra.mothership.Mothership;
import de.katzenpapst.amunra.mothership.Mothership.PermissionMode;
import de.katzenpapst.amunra.network.packet.PacketSimpleAR;
import de.katzenpapst.amunra.network.packet.PacketSimpleAR.EnumSimplePacket;
import de.katzenpapst.amunra.tile.TileEntityMothershipSettings;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementDropdown;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public class TabMothershipUsage extends AbstractPermissionTab {

    protected static final ResourceLocation icon = new ResourceLocation(
            AmunRa.ASSETPREFIX,
            "textures/gui/usage-permission.png");

    public TabMothershipUsage(final TileEntityMothershipSettings tile, final GuiMothershipSettings parent,
            final Minecraft mc, final int width, final int height, final int xSize, final int ySize) {
        super(tile, parent, mc, width, height, xSize, ySize);
    }

    @Override
    public void resetData() {
        final Mothership.PermissionMode pm = this.tile.getMothership().getUsagePermissionMode();
        this.modeDropdown.selectedOption = pm.ordinal();
        this.playerIdList.clear();

        final Mothership m = this.tile.getMothership();

        this.playerIdList.addAll(m.getPlayerListUsage());
        this.selectBox.clear();
        for (final PlayerID pid : this.playerIdList) {
            this.selectBox.addString(pid.getName());
        }
    }

    @Override
    public ResourceLocation getIcon() {
        return icon;
    }

    @Override
    public String getTooltip() {
        return GCCoreUtil.translate("tile.mothershipSettings.permissionUse");
    }

    @Override
    public void onSelectionChanged(final GuiElementDropdown dropdown, final int selection) {
        if (dropdown == this.modeDropdown) {
            final PermissionMode mode = PermissionMode.values()[selection];
            this.tile.getMothership().setUsagePermissionMode(mode);
            this.applyData();
        }
    }

    @Override
    public int getInitialSelection(final GuiElementDropdown dropdown) {
        return this.tile.getMothership().getUsagePermissionMode().ordinal();
    }

    @Override
    protected void addUsername(final Mothership mothership, final String userName) {
        AmunRa.packetPipeline.sendToServer(
                new PacketSimpleAR(
                        EnumSimplePacket.S_ADD_MOTHERSHIP_PLAYER,
                        this.tile.getMothership().getID(),
                        this.textBoxUsername.text,
                        1));
    }

    @Override
    protected void removeUsernameFromList(final int position) {
        this.playerIdList.remove(position);
        this.tile.getMothership().setPlayerListUsage(this.playerIdList);
    }

    @Override
    public String getTooltipDescription() {
        return GCCoreUtil.translate("tile.mothershipSettings.permissionUseDesc");
    }
}
