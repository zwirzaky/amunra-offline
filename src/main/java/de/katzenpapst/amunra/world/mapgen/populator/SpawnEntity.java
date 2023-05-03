package de.katzenpapst.amunra.world.mapgen.populator;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class SpawnEntity extends AbstractPopulator {

    private Entity entity = null;

    public SpawnEntity(final int x, final int y, final int z, final Entity ent) {
        super(x, y, z);
        this.entity = ent;
    }

    @Override
    public boolean populate(final World world) {
        if (this.entity == null) return false;

        // otherwise try to spawn it now
        this.entity.setLocationAndAngles(this.x + 0.5D, this.y, this.z + 0.5D, 0.0F, 0.0F);
        return world.spawnEntityInWorld(this.entity);
    }

}
