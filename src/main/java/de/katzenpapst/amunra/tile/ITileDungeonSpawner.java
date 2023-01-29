package de.katzenpapst.amunra.tile;

import net.minecraft.util.AxisAlignedBB;

import de.katzenpapst.amunra.mob.entity.IAmunRaBoss;
import de.katzenpapst.amunra.vec.Vector3int;

public interface ITileDungeonSpawner {

    public void setSpawnedBoss(IAmunRaBoss boss);

    public IAmunRaBoss getSpawnedBoss();

    public Vector3int getBlockPosition();

    public AxisAlignedBB getRoomArea();

    public void setRoomArea(AxisAlignedBB aabb);

    public void setBossClass(Class<? extends IAmunRaBoss> theClass);

    public void onBossDefeated();
}
