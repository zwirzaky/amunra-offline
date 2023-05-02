package de.katzenpapst.amunra.world.mapgen.populator;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

public class SetSpawnerEntity extends AbstractPopulator {

    String entityName;

    public SetSpawnerEntity(final int x, final int y, final int z, final String entityName) {
        super(x, y, z);
        this.entityName = entityName;
    }

    @Override
    public boolean populate(final World world) {
        if (world.getBlock(this.x, this.y, this.z) == Blocks.mob_spawner) {
            final TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(this.x, this.y, this.z);
            if (spawner != null) {
                spawner.func_145881_a().setEntityName(this.entityName);
                return true;
            }
        }
        return false;
    }

}
