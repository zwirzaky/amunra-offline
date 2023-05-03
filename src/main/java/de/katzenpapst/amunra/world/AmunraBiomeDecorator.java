package de.katzenpapst.amunra.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;

public class AmunraBiomeDecorator extends BiomeDecoratorSpace {

    protected World mWorld;

    protected List<WorldGenOre> oreGenList;

    public AmunraBiomeDecorator() {}

    @Override
    protected void setCurrentWorld(final World world) {
        this.mWorld = world;
        this.oreGenList = this.getOreGenerators();
    }

    /**
     * Override and return a list of ore generators.
     */
    protected List<WorldGenOre> getOreGenerators() {
        return new ArrayList<>();
    }

    @Override
    protected World getCurrentWorld() {
        return this.mWorld;
    }

    @Override
    protected void decorate() {
        for (final WorldGenOre oreGen : this.oreGenList) {
            this.generateOre(oreGen.amountPerChunk, oreGen, oreGen.minY, oreGen.maxY);
        }
    }

}
