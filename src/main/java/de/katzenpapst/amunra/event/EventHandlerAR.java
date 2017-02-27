package de.katzenpapst.amunra.event;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler.ThermalArmorEvent;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.katzenpapst.amunra.item.ItemThermalSuit;
import de.katzenpapst.amunra.mob.DamageSourceAR;
import de.katzenpapst.amunra.mob.entity.IEntityNonOxygenBreather;

public class EventHandlerAR {

    @SubscribeEvent
    public void entityLivingEvent(LivingUpdateEvent event)
    {
        EntityLivingBase entityLiving = event.entityLiving;
        if(!(entityLiving instanceof IEntityNonOxygenBreather)) {
            return;
        }

        if (entityLiving.ticksExisted % 100 == 0)
        {
            CelestialBody body;
            boolean isInSealedArea = OxygenUtil.isAABBInBreathableAirBlock(entityLiving);

            if (entityLiving.worldObj.provider instanceof IGalacticraftWorldProvider) {
                body = ((IGalacticraftWorldProvider)entityLiving.worldObj.provider).getCelestialBody();
            } else {
                body = GalacticraftCore.planetOverworld;
            }
            if(!((IEntityNonOxygenBreather)entityLiving).canBreatheIn(body.atmosphere, isInSealedArea)) {
                // should I add these events about suffocation that GC does?
                entityLiving.attackEntityFrom(DamageSourceAR.dsSuffocate, 1);
            }
        }
    }

    @SubscribeEvent
    public void onThermalArmorEvent(ThermalArmorEvent event)
    {
        // I sure hope this works with other mods...

        if (event.armorStack.getItem() instanceof ItemThermalSuit)
        {
            event.setArmorAddResult(ThermalArmorEvent.ArmorAddResult.ADD);
            return;
        }

    }
}
