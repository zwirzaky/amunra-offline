package de.katzenpapst.amunra.block.machine.mothershipEngine;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.block.BlockMachineMetaDummyRender;
import de.katzenpapst.amunra.block.SubBlock;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class BlockMothershipJetMeta extends BlockMachineMetaDummyRender {

    public BlockMothershipJetMeta(final String name, final Material material) {
        super(name, material);
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
    public BlockMetaPair addSubBlock(final int meta, final SubBlock sb) {
        if (!(sb instanceof MothershipEngineJetBase)) {
            throw new IllegalArgumentException("BlockMothershipJetMeta can only accept MothershipEngineJetBase");
        }
        return super.addSubBlock(meta, sb);
    }

    @Override
    public void register() {
        GameRegistry.registerBlock(this, null, this.getUnlocalizedName());

        for (int i = 0; i < this.subBlocksArray.length; i++) {
            final SubBlock sb = this.subBlocksArray[i];
            if (sb != null) {
                this.setHarvestLevel(sb.getHarvestTool(0), sb.getHarvestLevel(0), i);
            }
        }
    }

    @Override
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y,
            final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        final SubBlock sb = this.getSubBlock(meta);
        if (sb instanceof MothershipEngineJetBase) {
            return ((MothershipEngineJetBase) sb).getItem().getItemStack(1);
        }

        return super.getPickBlock(target, world, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(final World world, final int x, final int y, final int z,
            final EntityLivingBase entityLiving, final ItemStack itemStack) {
        final int metadata = world.getBlockMetadata(x, y, z);

        final SubBlock sb = this.getSubBlock(metadata);
        if (sb != null) {
            sb.onBlockPlacedBy(world, x, y, z, entityLiving, itemStack);
        }
    }
}
