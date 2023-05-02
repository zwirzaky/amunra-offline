package de.katzenpapst.amunra.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.block.SubBlock;
import de.katzenpapst.amunra.block.ore.BlockOreMulti;

public class BlockRendererMultiOre implements ISimpleBlockRenderingHandler {

    public BlockRendererMultiOre() {
        AmunRa.multiOreRendererId = RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId,
            final RenderBlocks renderer) {
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        // draw the background
        drawBlock(block, metadata, renderer);

        // and then the overlay
        final SubBlock sb = ((BlockOreMulti) block).getSubBlock(metadata);
        drawBlock(sb, metadata, renderer);

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

    }

    @Override
    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block,
            final int modelId, final RenderBlocks renderer) {
        final int meta = world.getBlockMetadata(x, y, z);
        final SubBlock sb = ((BlockOreMulti) block).getSubBlock(meta);

        // block with the background texture
        renderer.renderStandardBlock(block, x, y, z);

        // block with the overlay
        renderer.renderStandardBlock(sb, x, y, z);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(final int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return AmunRa.multiOreRendererId;
    }

    private static void drawBlock(final Block block, final int meta, final RenderBlocks renderer) {
        final Tessellator tessellator = Tessellator.instance;

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
        tessellator.draw();
    }

}
