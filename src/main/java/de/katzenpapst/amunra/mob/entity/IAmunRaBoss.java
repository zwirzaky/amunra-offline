package de.katzenpapst.amunra.mob.entity;

import net.minecraft.util.AxisAlignedBB;

import de.katzenpapst.amunra.tile.ITileDungeonSpawner;

public interface IAmunRaBoss {

    public void setSpawner(ITileDungeonSpawner spawner);

    public ITileDungeonSpawner getSpawner();

    public void setRoomArea(AxisAlignedBB aabb);

    public AxisAlignedBB getRoomArea();

    public void despawnBoss();
}
