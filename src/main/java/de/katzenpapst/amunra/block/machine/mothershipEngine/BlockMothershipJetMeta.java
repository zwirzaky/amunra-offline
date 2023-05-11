package de.katzenpapst.amunra.block.machine.mothershipEngine;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import de.katzenpapst.amunra.block.BlockMachineMetaDummyRender;
import de.katzenpapst.amunra.block.SubBlock;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class BlockMothershipJetMeta extends BlockMachineMetaDummyRender {

    public BlockMothershipJetMeta(final String name, final Material material) {
        super(name, material);
    }

    @Override
    public BlockMetaPair addSubBlock(int meta, SubBlock sb) {
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
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        final SubBlock sb = this.getSubBlock(meta);
        if (sb instanceof MothershipEngineJetBase engineJetBase) {
            return engineJetBase.getItem().getItemStack(1);
        }
        return super.getPickBlock(target, world, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        final int metadata = worldIn.getBlockMetadata(x, y, z);
        final SubBlock sb = this.getSubBlock(metadata);
        if (sb != null) {
            sb.onBlockPlacedBy(worldIn, x, y, z, placer, itemIn);
        }
    }
}
