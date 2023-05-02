package de.katzenpapst.amunra.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class AIFollowClosest extends EntityAIBase {

    private final EntityLiving theWatcher;
    /** The closest entity which is being watched by this one. */
    protected Entity closestEntity;
    /** This is the Maximum distance that the AI will look for the Entity */
    private final float maxDistanceForPlayer;
    private int lookTime;
    private final float someProbability;
    private final Class<? extends Entity> watchedClass;
    protected float minDistance;

    public AIFollowClosest(final EntityLiving user, final Class<? extends Entity> classToFollow, final float maxDistance,
            final float minDistance) {
        this.theWatcher = user;
        this.watchedClass = classToFollow;
        this.maxDistanceForPlayer = maxDistance;
        this.someProbability = 0.02F;
        this.setMutexBits(2);
    }

    public AIFollowClosest(final EntityLiving user, final Class<? extends Entity> classToFollow, final float maxDistance,
            final float minDistance, final float probability) {
        this.theWatcher = user;
        this.watchedClass = classToFollow;
        this.maxDistanceForPlayer = maxDistance;
        this.someProbability = probability;
        this.setMutexBits(2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (this.theWatcher.getRNG().nextFloat() >= this.someProbability) {
            return false;
        }
        if (this.theWatcher.getAttackTarget() != null) {
            this.closestEntity = this.theWatcher.getAttackTarget();
        }

        if (this.watchedClass == EntityPlayer.class) {
            this.closestEntity = this.theWatcher.worldObj
                    .getClosestPlayerToEntity(this.theWatcher, (double) this.maxDistanceForPlayer);
        } else {
            this.closestEntity = this.theWatcher.worldObj.findNearestEntityWithinAABB(
                    this.watchedClass,
                    this.theWatcher.boundingBox
                            .expand((double) this.maxDistanceForPlayer, 3.0D, (double) this.maxDistanceForPlayer),
                    this.theWatcher);
        }

        return this.closestEntity != null;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean continueExecuting() {
        return !this.closestEntity
                .isEntityAlive()
                        ? false
                        : this.theWatcher.getDistanceSqToEntity(this.closestEntity)
                                > (double) (this.maxDistanceForPlayer * this.maxDistanceForPlayer) ? false
                                        : this.lookTime > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.lookTime = 40 + this.theWatcher.getRNG().nextInt(40);
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.closestEntity = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {
        this.theWatcher.getLookHelper().setLookPosition(
                this.closestEntity.posX,
                this.closestEntity.posY + (double) this.closestEntity.getEyeHeight(),
                this.closestEntity.posZ,
                10.0F,
                (float) this.theWatcher.getVerticalFaceSpeed());
        --this.lookTime;
    }

}
