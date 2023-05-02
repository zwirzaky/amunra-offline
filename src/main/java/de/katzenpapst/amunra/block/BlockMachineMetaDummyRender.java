package de.katzenpapst.amunra.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;

public class BlockMachineMetaDummyRender extends BlockMachineMeta implements IPartialSealableBlock {

    public BlockMachineMetaDummyRender(final String name, final Material material) {
        super(name, material);
        // TODO Auto-generated constructor stub
    }

    public BlockMachineMetaDummyRender(final String name, final Material material, final int numSubBlocks) {
        super(name, material, numSubBlocks);
        // TODO Auto-generated constructor stub
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
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return AmunRa.dummyRendererId;
    }

    @Override
    public boolean isSealed(final World world, final int x, final int y, final int z, final ForgeDirection direction) {
        return true;
    }

}
