package de.katzenpapst.amunra.client;

import net.minecraft.util.ResourceLocation;

import de.katzenpapst.amunra.vec.Vector2int;

public class RingsRenderInfo {

    public ResourceLocation textureLocation;

    public int gapStart;
    public int gapEnd;

    public Vector2int textureSize = null;

    public RingsRenderInfo(final ResourceLocation textureLocation, final int gapStart, final int gapEnd) {
        this.textureLocation = textureLocation;
        this.gapStart = gapStart;
        this.gapEnd = gapEnd;
    }

    public void setTextureSize(final int x, final int y) {
        textureSize = new Vector2int(x, y);
    }

}
