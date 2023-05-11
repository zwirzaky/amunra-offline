package de.katzenpapst.amunra.helper;

public class MathHelperAR {

    /**
     * Returns the smallest int from any number of arguments
     */
    public static int min(final int... numbers) {
        int smallest = Integer.MAX_VALUE;

        for (int number : numbers) {
            if (number < smallest) {
                smallest = number;
            }
        }
        return smallest;
    }

    /**
     * Returns the largest int from any number of arguments
     */
    public static int max(final int... numbers) {
        int largest = Integer.MIN_VALUE;

        for (int number : numbers) {
            if (number > largest) {
                largest = number;
            }
        }
        return largest;
    }
}
