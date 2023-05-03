package de.katzenpapst.amunra.tile;

import net.minecraft.util.AxisAlignedBB;

import de.katzenpapst.amunra.mob.entity.IAmunRaBoss;
import de.katzenpapst.amunra.vec.Vector3int;

public interface ITileDungeonSpawner {

    void setSpawnedBoss(IAmunRaBoss boss);

    IAmunRaBoss getSpawnedBoss();

    Vector3int getBlockPosition();

    AxisAlignedBB getRoomArea();

    void setRoomArea(AxisAlignedBB aabb);

    void setBossClass(Class<? extends IAmunRaBoss> theClass);

    void onBossDefeated();
}
