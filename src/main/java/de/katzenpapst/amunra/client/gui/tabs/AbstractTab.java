package de.katzenpapst.amunra.client.gui.tabs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementTextBox;

abstract public class AbstractTab {

    protected List<GuiButton> buttonList = new ArrayList<>();

    protected List<GuiLabel> labelList = new ArrayList<>();

    protected List<GuiElementTextBox> textBoxList = new ArrayList<>();

    /** Reference to the Minecraft object. */
    protected Minecraft mc;
    /** The width of the screen object. */
    protected int width;
    /** The height of the screen object. */
    protected int height;

    /** The width of the window itself */
    protected int xSize;
    /** The height of the window itself */
    protected int ySize;

    private int field_146298_h;

    private GuiButton selectedButton;
    private int eventButton;
    private long lastMouseEvent;

    private Slot theSlot;

    protected FontRenderer fontRendererObj;

    protected GuiContainerGC parent;

    public AbstractTab(GuiContainerGC parent, Minecraft mc, int width, int height, int xSize, int ySize) {
        setWorldAndResolution(mc, width, height, xSize, ySize);
        this.parent = parent;
    }

    abstract public void initGui();

    public void setWorldAndResolution(Minecraft mc, int width, int height, int xSize, int ySize) {
        this.mc = mc;
        this.fontRendererObj = mc.fontRenderer;
        this.width = width;
        this.height = height;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public void drawScreen(int mouseX, int mouseY, float ticks) {
        // GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        // GL11.glDisable(GL11.GL_LIGHTING);
        // GL11.glDisable(GL11.GL_DEPTH_TEST);
        // super.drawScreen(mouseX, mouseY, partialTicks);

        // GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        // GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        for (GuiButton box : buttonList) {
            box.drawButton(mc, mouseX, mouseY);
        }

        for (GuiLabel box : labelList) {
            box.func_146159_a(mc, mouseX, mouseY);
        }

        drawExtraScreenElements(mouseX, mouseY, ticks);
        /*
         * for(GuiElementTextBox box: textBoxList) { box.drawButton(mc, mouseX, mouseY); }
         */
        RenderHelper.enableGUIStandardItemLighting();
    }

    protected void drawExtraScreenElements(int mouseX, int mouseY, float ticks) {

    }

    public void addButton(GuiButton btn) {
        buttonList.add(btn);
    }

    public void addLabel(GuiLabel label) {
        labelList.add(label);
    }

    public void addTextBox(GuiElementTextBox box) {
        textBoxList.add(box);
        buttonList.add(box);
    }

    public boolean actionPerformed(GuiButton btn) {
        return false;
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() {
        int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        int k = Mouse.getEventButton();

        if (Mouse.getEventButtonState()) {
            if (this.mc.gameSettings.touchscreen && this.field_146298_h++ > 0) {
                return;
            }

            this.eventButton = k;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(i, j, this.eventButton);
        } else if (k != -1) {
            if (this.mc.gameSettings.touchscreen && --this.field_146298_h > 0) {
                return;
            }

            this.eventButton = -1;
            this.mouseMovedOrUp(i, j, k);
        } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
            long l = Minecraft.getSystemTime() - this.lastMouseEvent;
            this.mouseClickMove(i, j, this.eventButton, l);
        }
    }

    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {}

    protected void mouseMovedOrUp(int mouseX, int mouseY, int state) {
        if (this.selectedButton != null && state == 0) {
            this.selectedButton.mouseReleased(mouseX, mouseY);
            this.selectedButton = null;
        }
    }

    /**
     * Handles keyboard input. / public void handleKeyboardInput() { if (Keyboard.getEventKeyState()) {
     * this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey()); } }
     */

    /**
     * This function is what controls the hotbar shortcut check when you press a number key when hovering a stack.
     */
    protected boolean checkHotbarKeys(int keyCode) {
        if (this.mc.thePlayer.inventory.getItemStack() == null && this.theSlot != null) {
            for (int j = 0; j < 9; ++j) {
                if (keyCode == this.mc.gameSettings.keyBindsHotbar[j].getKeyCode()) {
                    this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, j, 2);
                    return true;
                }
            }
        }

        return false;
    }

    protected void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType) {
        /*
         * if (slotIn != null) { slotId = slotIn.slotNumber; }
         * this.mc.playerController.windowClick(this.inventorySlots.windowId, slotId, clickedButton, clickType,
         * this.mc.thePlayer);
         */
    }

    public boolean keyTyped(char keyChar, int keyID) {
        if (keyID != Keyboard.KEY_ESCAPE /* && keyID != this.mc.gameSettings.keyBindInventory.getKeyCode() */) {
            // do the fields
            for (GuiElementTextBox box : textBoxList) {
                if (box.keyTyped(keyChar, keyID)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int l = 0; l < this.buttonList.size(); ++l) {
                GuiButton guibutton = (GuiButton) this.buttonList.get(l);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
                    ActionPerformedEvent.Pre event = new ActionPerformedEvent.Pre(
                            (GuiScreen) this.parent,
                            guibutton,
                            this.buttonList);
                    if (MinecraftForge.EVENT_BUS.post(event)) break;
                    this.selectedButton = event.button;
                    event.button.func_146113_a(this.mc.getSoundHandler());
                    this.actionPerformed(event.button);
                    if (this.equals(this.mc.currentScreen)) MinecraftForge.EVENT_BUS.post(
                            new ActionPerformedEvent.Post((GuiScreen) this.parent, event.button, this.buttonList));
                }
            }
        }
    }

    public void onTabActivated() {}

    abstract public ResourceLocation getIcon();

    abstract public String getTooltip();

    public String getTooltipDescription() {
        return null;
    }
}
