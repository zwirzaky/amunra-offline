package de.katzenpapst.amunra.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.inventory.ContainerIonEngine;
import de.katzenpapst.amunra.tile.TileEntityMothershipEngineAbstract;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public class GuiIonEngine extends GuiRocketEngine {

    protected GuiElementInfoRegion electricInfoRegion;

    public GuiIonEngine(final InventoryPlayer player, final TileEntityMothershipEngineAbstract tileEngine) {
        super(
                new ContainerIonEngine(player, tileEngine),
                tileEngine,
                new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/ms_ion.png"));
    }

    @Override
    public void initGui() {
        super.initGui();
        electricInfoRegion = new GuiElementInfoRegion(
                (this.width - this.xSize) / 2 + 113,
                (this.height - this.ySize) / 2 + 29,
                56,
                9,
                new ArrayList<String>(),
                this.width,
                this.height,
                this);

        this.infoRegions.add(this.electricInfoRegion);
    };

    @Override
    protected void drawGuiContainerBackgroundLayer(final float var1, final int var2, final int var3) {
        super.drawGuiContainerBackgroundLayer(var1, var2, var3);

        final int containerWidth = (this.width - this.xSize) / 2;
        final int containerHeight = (this.height - this.ySize) / 2;
        // this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        int scale;

        final List<String> electricityDesc = new ArrayList<>();
        electricityDesc.add(GCCoreUtil.translate("gui.energyStorage.desc.0"));
        EnergyDisplayHelper.getEnergyDisplayTooltip(
                this.tileEngine.getEnergyStoredGC(),
                this.tileEngine.getMaxEnergyStoredGC(),
                electricityDesc);
        this.electricInfoRegion.tooltipStrings = electricityDesc;

        if (this.tileEngine.getEnergyStoredGC() > 0) {
            scale = this.tileEngine.getScaledElecticalLevel(54);
            this.drawTexturedModalRect(containerWidth + 114, containerHeight + 30, 176, 74, scale, 7);
            this.drawTexturedModalRect(containerWidth + 101, containerHeight + 29, 192, 64, 11, 10);
        }
    };

    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);

    }

}
