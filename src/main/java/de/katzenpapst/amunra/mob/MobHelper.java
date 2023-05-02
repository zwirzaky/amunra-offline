package de.katzenpapst.amunra.mob;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class MobHelper {

    public static boolean canAnimalSpawnHere(final World world, final EntityCreature ent, final BlockMetaPair blockToSpawnOn) {

        final int i = MathHelper.floor_double(ent.posX);
        final int j = MathHelper.floor_double(ent.boundingBox.minY);
        final int k = MathHelper.floor_double(ent.posZ);

        boolean canSpawnOnBlock = true;
        if (blockToSpawnOn != null) {
            canSpawnOnBlock = blockToSpawnOn.getBlock() == world.getBlock(i, j - 1, k)
                    && blockToSpawnOn.getMetadata() == world.getBlockMetadata(i, j - 1, k);
        }

        return canSpawnOnBlock && world.getFullBlockLightValue(i, j, k) > 8
                && ent.getBlockPathWeight(i, j, k) >= 0.0F
                && world.checkNoEntityCollision(ent.boundingBox)
                && world.getCollidingBoundingBoxes(ent, ent.boundingBox).isEmpty()
                && !world.isAnyLiquid(ent.boundingBox);

    }

}
