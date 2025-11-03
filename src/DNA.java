/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 * </p>
 * <p>
 * Completed by: Amay Srinivasan
 * </p>
 */

public class DNA {

    /**
     * TODO: Complete this function, STRCount(), to return longest consecutive run of STR in sequence.
     */
    // Constants for rolling hash: base for horner's method and large prime modulus to avoid collisions
    private static final long R = 127;
    private static final long P = 54_321_102_419L;

    // Maps DNA characters to an integer for increased hashing efficiency
    private static int val(char c) {
        if (c == 'A') {
            return 1;
        } else if (c == 'C') {
            return 2;
        } else if (c == 'G') {
            return 3;
        } else if (c == 'T') {
            return 4;
        } else {
            return 0;
        }
    }

    // Computes a hash of a substring using horner's method, treats substring as a num in base R, modulos P
    private static long hash(String s, int start, int length) {
        long h = 0;
        for (int i = 0; i < length; i++) {
            h = (h * R + val(s.charAt(start + i))) % P;
        }
        return h;
    }

    // Returns the longest consecutive and non overlapping repeats of STR in a sequence
    public static int STRCount(String sequence, String STR) {
        int n = sequence.length();
        int m = STR.length();
        if (m == 0 || n < m) {
            return 0;
        }
        // Precompute R^(m-1) for efficiency in removing the leading character in the rolling hash
        long power = 1;
        for (int i = 1; i < m; i++) {
            power = (power * R) % P;
        }
        // Initial hashes
        long strHash = hash(STR, 0, m);
        long seqHash = hash(sequence, 0, m);

        int maxRun = 0;
        int i = 0;
        while (i <= n - m) {
            // If the current window matches the STR hash, check consecutive repeats
            if (seqHash == strHash) {
                int count = 0;
                int j = i;
                long nextHash = seqHash;
                // Count consecutive non-overlapping repeats by jimping m each time
                while (j <= n - m && nextHash == strHash) {
                    count++;
                    j += m;
                    if (j <= n - m) {
                        nextHash = hash(sequence, j, m);
                    }
                }
                // updates the maximum run
                if (count > maxRun) {
                    maxRun = count;
                }
                // Skip past this run
                i = j - 1;
                // Recomputes the hash at new position since we jumped
                if (i <= n - m) {
                    seqHash = hash(sequence, i, m);
                }
                // Skip the rolling hash slide for this iteration
                continue;
            }
            // Rolling hashes, to slide the window by one character, removes leading char, multiplies by R,
            // and adds new trailing char
            if (i < n - m) {
                long lead = (val(sequence.charAt(i)) * power) % P;
                seqHash = ((seqHash - lead + P) % P * R + val(sequence.charAt(i + m))) % P;
            }
            i++;
        }
        return maxRun;
    }
}
