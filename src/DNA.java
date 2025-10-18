/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: Amay Srinivasan
 *</p>
 */

public class DNA {

    /**
     * TODO: Complete this function, STRCount(), to return longest consecutive run of STR in sequence.
     */
    public static int STRCount(String sequence, String STR) {
        // Store the lengths sow we don't have to keep calling .length
        int sequenceLength = sequence.length();
        int strLength = STR.length();
        // Variable to track the highest number of repeats
        int maxCount = 0;
        // Loop through the sequence from the start to the final index where an STR can fit
        for (int i = 0; i < sequenceLength-strLength; i++) {
            // Counter for how many times an STR was repeated starting at index i
            int currentCount = 0;
            // While the substring of the sequence starting from the current position matches the STR
            while (i + currentCount * strLength + strLength <= sequenceLength && sequence.substring(i + currentCount * strLength, i + (currentCount + 1) * strLength).equals(STR)) {
                // Increase the counter (which also moves which one we are checking within the while loop_
                currentCount++;
            }
            // Update maxcount if this is the longest repeat we've seen so far
            if (currentCount > maxCount) {
                maxCount = currentCount;
            }
            // Added this optimization such that we aren't double counting the parts we know are valid.
            if (currentCount > 0) {
                i += (currentCount - 1) * strLength;
            }
        }
        /// Return the max number of repeats we found
        return maxCount;
    }
}
