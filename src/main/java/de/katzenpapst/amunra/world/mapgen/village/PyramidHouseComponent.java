package de.katzenpapst.amunra.world.mapgen.village;

import net.minecraft.block.Block;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import de.katzenpapst.amunra.helper.CoordHelper;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class PyramidHouseComponent extends GridVillageComponent {

    protected int houseHeight = 5;

    @Override
    public boolean generateChunk(final int chunkX, final int chunkZ, final Block[] blocks, final byte[] metas) {

        // now, how to get the height?
        final StructureBoundingBox chunkBB = CoordHelper.getChunkBB(chunkX, chunkZ);// new StructureBoundingBox((chunkX << 4),
                                                                              // (chunkX<< 4), (chunkX+1 << 4)-1,
                                                                              // (chunkX+1 << 4)-1);
        final int fallbackGround = this.parent.getWorldGroundLevel();
        if (groundLevel == -1) {
            groundLevel = getAverageGroundLevel(blocks, metas, getStructureBoundingBox(), chunkBB, fallbackGround);
            if (groundLevel == -1) {
                groundLevel = fallbackGround; // but this shouldn't even happen...
            }
        }

        final StructureBoundingBox myBB = this.getStructureBoundingBox();
        final BlockMetaPair wall = ((GridVillageStart) this.parent).getWallMaterial();
        final BlockMetaPair floor = ((GridVillageStart) this.parent).getFloorMaterial();
        final BlockMetaPair padding = ((GridVillageStart) this.parent).getFillMaterial();
        // BlockMetaPair path = ((GridVillageStart)this.parent).getPathMaterial();
        // BlockMetaPair glassPane = new BlockMetaPair(Blocks.glass_pane, (byte) 0);
        // BlockMetaPair air = new BlockMetaPair(Blocks.air, (byte) 0);

        // draw floor first
        final int startX = 0;
        final int stopX = myBB.getXSize() - 1;
        final int startZ = 0;
        final int stopZ = myBB.getZSize() - 1;

        final int xCenter = (int) Math.ceil((stopX - startX) / 2 + startX);
        // int zCenter = (int)Math.ceil((stopZ-startZ)/2+startZ);

        final int radius = xCenter;

        for (int x = startX; x <= stopX; x++) {
            for (int z = startZ; z <= stopZ; z++) {

                final int highestGroundBlock = getHighestSolidBlockInBB(blocks, metas, chunkX, chunkZ, x, z);
                if (highestGroundBlock == -1) {
                    continue; // that should mean that we aren't in the right chunk
                }

                // now fill
                for (int y = highestGroundBlock - 1; y < groundLevel; y++) {
                    // padding
                    placeBlockRel2BB(blocks, metas, chunkX, chunkZ, x, y, z, padding);
                }
                // floor
                placeBlockRel2BB(blocks, metas, chunkX, chunkZ, x, groundLevel - 1, z, floor);

                if (startX == x || startZ == z || stopX == x || stopZ == z) {
                    placeBlockRel2BB(blocks, metas, chunkX, chunkZ, x, groundLevel, z, wall);
                }

                for (int y = 0; y <= radius; y++) {
                    if (x >= startX + y && x <= stopX - y && (z == startZ + y || z == stopZ - y)
                            || (x == startX + y || x == stopX - y) && z >= startZ + y && z <= stopZ - y) {
                        placeBlockRel2BB(blocks, metas, chunkX, chunkZ, x, groundLevel + y + 1, z, wall);
                    }
                    /*
                     * if((x >= startX+y && x <= stopX-y) && (z >= startZ+y && z <= stopZ-y)) { placeBlockRel2BB(blocks,
                     * metas, chunkX, chunkZ, x, groundLevel+y+1, z, wall); } if((x >= startX+y && x <= stopX-y-1) && (z
                     * >= startZ+y+1 && z <= stopZ-y-y)) { placeBlockRel2BB(blocks, metas, chunkX, chunkZ, x,
                     * groundLevel, z, air); }
                     */
                }

            }

        }

        return true;

    }
}
