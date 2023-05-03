package de.katzenpapst.amunra.mob.entity;

import net.minecraft.util.AxisAlignedBB;

import de.katzenpapst.amunra.tile.ITileDungeonSpawner;

public interface IAmunRaBoss {

    void setSpawner(ITileDungeonSpawner spawner);

    ITileDungeonSpawner getSpawner();

    void setRoomArea(AxisAlignedBB aabb);

    AxisAlignedBB getRoomArea();

    void despawnBoss();
}
