package de.katzenpapst.amunra.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import de.katzenpapst.amunra.AmunRa;
import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;

public class BlockMachineMetaDummyRender extends BlockMachineMeta implements IPartialSealableBlock {

    public BlockMachineMetaDummyRender(final String name, final Material material) {
        super(name, material);
    }

    public BlockMachineMetaDummyRender(final String name, final Material material, final int numSubBlocks) {
        super(name, material, numSubBlocks);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isBlockNormalCube() {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return AmunRa.dummyRendererId;
    }

    @Override
    public boolean isSealed(World world, int x, int y, int z, ForgeDirection direction) {
        return true;
    }

}
