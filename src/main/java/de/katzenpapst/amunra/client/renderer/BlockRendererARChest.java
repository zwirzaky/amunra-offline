package de.katzenpapst.amunra.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.block.BlockARChest;
import de.katzenpapst.amunra.client.renderer.model.ModelARChest;

public class BlockRendererARChest implements ISimpleBlockRenderingHandler {

    // private final TileEntityARChest chest = new TileEntityARChest();
    private final ModelARChest chestModel = new ModelARChest();

    public BlockRendererARChest() {
        AmunRa.chestRenderId = RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
        // TODO Auto-generated method stub
        renderChest(block, metadata, modelId);

    }

    @Override
    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId,
            final RenderBlocks renderer) {
        // this happens in the tileentity

        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(final int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return AmunRa.chestRenderId;
    }

    public void renderChest(final Block par1Block, final int par2, final float par3) {
        if (par1Block instanceof BlockARChest) {
            chestModel.render((BlockARChest) par1Block, false, 0, -0.1F, 0);
        }
    }
}
