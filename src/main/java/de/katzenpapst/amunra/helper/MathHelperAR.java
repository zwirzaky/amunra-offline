package de.katzenpapst.amunra.helper;

public class MathHelperAR {

    /**
     * Returns the smallest int from any number of arguments
     * 
     * @param numbers
     * @return
     */
    public static int min(final int... numbers) {
        int smallest = Integer.MAX_VALUE;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < smallest) {
                smallest = numbers[i];
            }
        }

        return smallest;
    }

    /**
     * Returns the largest int from any number of arguments
     * 
     * @param numbers
     * @return
     */
    public static int max(final int... numbers) {
        int largest = Integer.MIN_VALUE;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > largest) {
                largest = numbers[i];
            }
        }

        return largest;
    }

}
