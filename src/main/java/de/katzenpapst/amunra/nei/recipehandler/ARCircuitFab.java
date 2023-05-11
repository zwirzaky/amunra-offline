package de.katzenpapst.amunra.nei.recipehandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import codechicken.nei.PositionedStack;
import de.katzenpapst.amunra.nei.NEIAmunRaConfig;
import micdoodle8.mods.galacticraft.core.nei.CircuitFabricatorRecipeHandler;

public class ARCircuitFab extends CircuitFabricatorRecipeHandler {

    @Override
    public Set<Entry<ArrayList<PositionedStack>, PositionedStack>> getRecipes() {
        final Map<ArrayList<PositionedStack>, PositionedStack> recipes = new HashMap<>();

        for (final Entry<Map<Integer, PositionedStack>, PositionedStack> stack : NEIAmunRaConfig
                .getCircuitFabricatorRecipes()) {
            final ArrayList<PositionedStack> inputStacks = new ArrayList<>();

            for (final Map.Entry<Integer, PositionedStack> input : stack.getKey().entrySet()) {
                inputStacks.add(input.getValue());
            }

            recipes.put(inputStacks, stack.getValue());
        }

        return recipes.entrySet();
    }

}
