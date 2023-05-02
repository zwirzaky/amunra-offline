package de.katzenpapst.amunra.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;

import de.katzenpapst.amunra.client.gui.elements.TabButton;
import de.katzenpapst.amunra.client.gui.tabs.AbstractTab;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;

abstract public class GuiContainerTabbed extends GuiContainerGC {

    protected List<AbstractTab> tabList;

    protected List<TabButton> tabButtons;

    protected int activeTab = -1;

    public static final int TAB_BTN_OFFSET = 10000;

    public GuiContainerTabbed(final Container container) {
        super(container);

        this.tabList = new ArrayList<>();
        this.tabButtons = new ArrayList<>();
    }

    @Override
    public void initGui() {
        this.tabList.clear();
        this.tabButtons.clear();
        super.initGui();
    }

    public int addTab(final AbstractTab tab) {
        if (this.tabList.add(tab)) {
            tab.initGui();

            final int newIndex = this.tabList.size() - 1;

            final int guiX = (this.width - this.xSize) / 2;
            final int guiY = (this.height - this.ySize) / 2;

            // add button
            final TabButton test = new TabButton(
                    TAB_BTN_OFFSET + newIndex,
                    guiX - 27,
                    guiY + 6 + newIndex * 28,
                    tab.getTooltip(),
                    tab.getTooltipDescription(),
                    tab.getIcon());
            this.buttonList.add(test);
            this.tabButtons.add(test);

            this.setActiveTab(0);

            return newIndex;
        }
        return -1;
    }

    public AbstractTab getTab(final int index) {
        return this.tabList.get(index);
    }

    public void setActiveTab(final int newIndex) {
        if (newIndex >= 0 && newIndex < this.tabList.size() && newIndex != this.activeTab) {
            this.activeTab = newIndex;
            final int btnIndex = TAB_BTN_OFFSET + newIndex;
            this.getActiveTab().onTabActivated();
            for (final TabButton btn : this.tabButtons) {
                if (btn.id == btnIndex) {
                    btn.isActive = true;
                } else {
                    btn.isActive = false;
                }
            }
        }
    }

    public int getActiveTabIndex() {
        return this.activeTab;
    }

    public AbstractTab getActiveTab() {
        return this.tabList.get(this.activeTab);
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float ticks) {
        super.drawScreen(mouseX, mouseY, ticks);
        // getActiveTab().
        this.getActiveTab().drawScreen(mouseX, mouseY, ticks);

        for (final TabButton tb : this.tabButtons) {
            tb.drawTooltip(mouseX, mouseY);
        }
    }

    protected void drawTabs() {

    }

    @Override
    protected void actionPerformed(final GuiButton btn) {
        if (btn.id >= TAB_BTN_OFFSET) {
            final int index = btn.id - TAB_BTN_OFFSET;
            this.setActiveTab(index);
        }
        // TODO handle my own stuff first
        this.getActiveTab().actionPerformed(btn);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    @Override
    protected void keyTyped(final char keyChar, final int keyId) {
        if (!this.getActiveTab().keyTyped(keyChar, keyId)) {
            super.keyTyped(keyChar, keyId);
        }
    }

    @Override
    public void handleMouseInput() {
        this.getActiveTab().handleMouseInput();
        super.handleMouseInput();
    }

    /**
     * Causes the screen to lay out its subcomponents again. This is the equivalent of the Java call
     * Container.validate()
     */
    @Override
    public void setWorldAndResolution(final Minecraft mc, final int x, final int y) {
        super.setWorldAndResolution(mc, x, y);
        this.getActiveTab().setWorldAndResolution(mc, x, y, this.xSize, this.ySize);
    }
}
