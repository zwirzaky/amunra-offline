package de.katzenpapst.amunra.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import de.katzenpapst.amunra.AmunRa;

/**
 * A renderer to simply don't render blocks, for when the tile entity is supposed to do the rendering
 *
 */
public class BlockRendererDummy implements ISimpleBlockRenderingHandler {

    public BlockRendererDummy() {
        AmunRa.dummyRendererId = RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId,
            final RenderBlocks renderer) {
        // don't
    }

    @Override
    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block,
            final int modelId, final RenderBlocks renderer) {
        // don't do anything
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(final int modelId) {
        // ?
        return true;
    }

    @Override
    public int getRenderId() {
        return AmunRa.dummyRendererId;
    }

}
