package de.katzenpapst.amunra.world.mapgen.populator;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import de.katzenpapst.amunra.mob.entity.IAmunRaBoss;
import de.katzenpapst.amunra.tile.ITileDungeonSpawner;

public class InitBossSpawner extends AbstractPopulator {

    protected AxisAlignedBB aabb;

    protected Class<? extends IAmunRaBoss> entityClass;

    public InitBossSpawner(final int x, final int y, final int z, final AxisAlignedBB aabb, final Class<? extends IAmunRaBoss> entityClass) {
        super(x, y, z);
        this.aabb = aabb;
        this.entityClass = entityClass;
    }

    @Override
    public boolean populate(final World world) {
        if (world.getTileEntity(this.x, this.y, this.z) instanceof final ITileDungeonSpawner tileDungeonSpawner) {
            tileDungeonSpawner.setRoomArea(this.aabb);
            tileDungeonSpawner.setBossClass(this.entityClass);
            return true;
        }
        return false;
    }

}
