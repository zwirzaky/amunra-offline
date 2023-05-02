package de.katzenpapst.amunra.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import de.katzenpapst.amunra.block.bush.BlockBushMulti;
import de.katzenpapst.amunra.block.bush.SubBlockBush;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class WorldGenTallgrassMeta extends WorldGenerator {

    private final Block tallGrassBlock;
    private final int tallGrassMetadata;
    private final SubBlockBush plant;

    public WorldGenTallgrassMeta(final BlockBushMulti block, final int meta) {
        this.tallGrassBlock = block;
        this.tallGrassMetadata = meta;

        plant = (SubBlockBush) block.getSubBlock(meta);

    }

    public WorldGenTallgrassMeta(final BlockMetaPair grass) {
        this((BlockBushMulti) grass.getBlock(), grass.getMetadata());
    }

    @Override
    public boolean generate(final World world, final Random rand, final int x, int y, final int z) {
        Block block;

        do {
            block = world.getBlock(x, y, z);
            if (!(block.isLeaves(world, x, y, z) || block.isAir(world, x, y, z))) {
                break;
            }
            --y;
        } while (y > 0);

        for (int l = 0; l < 128; ++l) {
            final int curX = x + rand.nextInt(8) - rand.nextInt(8);
            final int curY = y + rand.nextInt(4) - rand.nextInt(4);
            final int curZ = z + rand.nextInt(8) - rand.nextInt(8);

            if (world.isAirBlock(curX, curY, curZ) && plant.canPlaceOn(
                    world.getBlock(curX, curY - 1, curZ),
                    world.getBlockMetadata(curX, curY - 1, curZ),
                    0)) {

                world.setBlock(curX, curY, curZ, this.tallGrassBlock, this.tallGrassMetadata, 2);
            }
        }

        return true;
    }
}
