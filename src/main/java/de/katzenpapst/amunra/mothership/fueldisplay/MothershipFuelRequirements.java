package de.katzenpapst.amunra.mothership.fueldisplay;

import java.util.HashMap;
import java.util.Map;

public class MothershipFuelRequirements {

    protected Map<MothershipFuelDisplay, Integer> data;

    public MothershipFuelRequirements() {
        this.data = new HashMap<>();
    }

    public void add(final MothershipFuelDisplay fuel, final int amount) {
        if (!this.data.containsKey(fuel)) {
            this.data.put(fuel, amount);
        } else {
            this.data.put(fuel, this.data.get(fuel) + amount);
        }
    }

    public void merge(final MothershipFuelRequirements other) {
        for (final MothershipFuelDisplay fuel : other.data.keySet()) {
            this.add(fuel, other.data.get(fuel));
        }
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    public void clear() {
        this.data.clear();
    }

    public int get(final MothershipFuelDisplay key) {
        if (this.data.containsKey(key)) {
            return this.data.get(key);
        }
        return 0;
    }

    public Map<MothershipFuelDisplay, Integer> getData() {
        return this.data;
    }
}
