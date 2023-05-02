package de.katzenpapst.amunra.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import de.katzenpapst.amunra.helper.GuiHelper;
import de.katzenpapst.amunra.tile.TileEntityBlockScale;

public class RenderBlockScale extends TileEntitySpecialRenderer {

    public RenderBlockScale() {}

    @Override
    public void renderTileEntityAt(final TileEntity entity, final double x, final double y, final double z, final float partialTickTime) {
        if (!(entity instanceof TileEntityBlockScale scaleEntity)) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        final int rotation = scaleEntity.getRotationMeta();
        switch (rotation) {
            case 0:
                GL11.glRotatef(180, 0, 1, 0);
                GL11.glTranslatef(0.0F, 1.0F, -1.01F);
                GL11.glRotatef(180, 0, 0, 1);
                break;
            case 1:
                GL11.glTranslatef(1.0F, 1.0F, -0.01F);
                GL11.glRotatef(180, 0, 0, 1);
                break;
            case 2:
                GL11.glRotatef(-90, 0, 1, 0);
                GL11.glTranslatef(1.0F, 1.0F, -1.01F);
                GL11.glRotatef(180, 0, 0, 1);
                break;
            case 3:
                GL11.glRotatef(90, 0, 1, 0);
                GL11.glTranslatef(-0.0F, 1.0F, -0.01F);
                GL11.glRotatef(180, 0, 0, 1);
                break;
        }

        // now try to draw some text onto the block
        final FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

        final float yOffset = (50 - fr.FONT_HEIGHT) / 2.0F + 7.0F;

        final float mass = scaleEntity.getCurrentMass();
        final String toDisplay = GuiHelper.formatKilogram(mass);
        final int width = fr.getStringWidth(toDisplay); // 29 pixels
        GL11.glScalef(0.020F, 0.020F, 0.020F);
        // I think now, translating by 1 means translating by 1 pixel
        // I also think the total width is 1/0,02 = 50
        GL11.glTranslatef((50 - width) / 2.0F, yOffset, 0);
        fr.drawString(toDisplay, 0, 0, 0);

        GL11.glPopMatrix();
    }

}
