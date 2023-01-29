package de.katzenpapst.amunra.client;

import net.minecraft.util.ResourceLocation;

import de.katzenpapst.amunra.vec.Vector2int;

public class RingsRenderInfo {

    public ResourceLocation textureLocation;

    public int gapStart;
    public int gapEnd;

    public Vector2int textureSize = null;

    public RingsRenderInfo(ResourceLocation textureLocation, int gapStart, int gapEnd) {
        this.textureLocation = textureLocation;
        this.gapStart = gapStart;
        this.gapEnd = gapEnd;
    }

    public void setTextureSize(int x, int y) {
        textureSize = new Vector2int(x, y);
    }

}
