package de.katzenpapst.amunra.vec;

public class Vector2int {

    public int x;
    public int y;

    public Vector2int(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        // Should work somewhat good if I don't go too far, I guess
        return this.x << 16 ^ this.y;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Vector2int vector)) {
            return false;
        }
        return this.x == vector.x && this.y == vector.y;
    }

}
