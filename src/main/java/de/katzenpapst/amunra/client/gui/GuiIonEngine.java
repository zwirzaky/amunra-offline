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
        this.electricInfoRegion = new GuiElementInfoRegion(
                (this.width - this.xSize) / 2 + 113,
                (this.height - this.ySize) / 2 + 29,
                56,
                9,
                new ArrayList<>(),
                this.width,
                this.height,
                this);

        this.infoRegions.add(this.electricInfoRegion);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        final List<String> electricityDesc = new ArrayList<>();
        electricityDesc.add(GCCoreUtil.translate("gui.energyStorage.desc.0"));
        EnergyDisplayHelper.getEnergyDisplayTooltip(
                this.tileEngine.getEnergyStoredGC(),
                this.tileEngine.getMaxEnergyStoredGC(),
                electricityDesc);
        this.electricInfoRegion.tooltipStrings = electricityDesc;

        if (this.tileEngine.getEnergyStoredGC() > 0) {
            final int x = (this.width - this.xSize) / 2;
            final int y = (this.height - this.ySize) / 2;
            final int scale = this.tileEngine.getScaledElecticalLevel(54);
            this.drawTexturedModalRect(x + 114, y + 30, 176, 74, scale, 7);
            this.drawTexturedModalRect(x + 101, y + 29, 192, 64, 11, 10);
        }
    }

}
