package de.katzenpapst.amunra.world.mapgen.populator;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import de.katzenpapst.amunra.mob.entity.IAmunRaBoss;
import de.katzenpapst.amunra.tile.ITileDungeonSpawner;

public class InitBossSpawner extends AbstractPopulator {

    protected AxisAlignedBB aabb;

    protected Class<? extends IAmunRaBoss> entityClass;

    public InitBossSpawner(int x, int y, int z, AxisAlignedBB aabb, Class<? extends IAmunRaBoss> entityClass) {
        super(x, y, z);
        this.aabb = aabb;
        this.entityClass = entityClass;
    }

    @Override
    public boolean populate(World world) {
        if (world.getTileEntity(x, y, z) instanceof ITileDungeonSpawner tileDungeonSpawner) {
            tileDungeonSpawner.setRoomArea(aabb);
            tileDungeonSpawner.setBossClass(entityClass);
            return true;
        }
        return false;
    }

}
