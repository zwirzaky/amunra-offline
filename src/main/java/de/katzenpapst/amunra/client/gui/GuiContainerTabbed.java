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

        tabList = new ArrayList<AbstractTab>();
        tabButtons = new ArrayList<TabButton>();
    }

    @Override
    public void initGui() {
        tabList.clear();
        tabButtons.clear();
        super.initGui();
    }

    public int addTab(final AbstractTab tab) {
        if (tabList.add(tab)) {
            tab.initGui();

            final int newIndex = tabList.size() - 1;

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

            setActiveTab(0);

            return newIndex;
        }
        return -1;
    }

    public AbstractTab getTab(final int index) {
        return tabList.get(index);
    }

    public void setActiveTab(final int newIndex) {
        if (newIndex >= 0 && newIndex < tabList.size() && newIndex != activeTab) {
            activeTab = newIndex;
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
        return activeTab;
    }

    public AbstractTab getActiveTab() {
        return tabList.get(activeTab);
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float ticks) {
        super.drawScreen(mouseX, mouseY, ticks);
        // getActiveTab().
        getActiveTab().drawScreen(mouseX, mouseY, ticks);

        for (final TabButton tb : tabButtons) {
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
        getActiveTab().actionPerformed(btn);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    @Override
    protected void keyTyped(final char keyChar, final int keyId) {
        if (!getActiveTab().keyTyped(keyChar, keyId)) {
            super.keyTyped(keyChar, keyId);
        }
    }

    @Override
    public void handleMouseInput() {
        getActiveTab().handleMouseInput();
        super.handleMouseInput();
    }

    /**
     * Causes the screen to lay out its subcomponents again. This is the equivalent of the Java call
     * Container.validate()
     */
    @Override
    public void setWorldAndResolution(final Minecraft mc, final int x, final int y) {
        super.setWorldAndResolution(mc, x, y);
        getActiveTab().setWorldAndResolution(mc, x, y, this.xSize, this.ySize);
    }
}
