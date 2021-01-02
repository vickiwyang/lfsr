/* *****************************************************************************
 * Name: Vicki Yang
 * NetID: vyang
 * Precept: 04
 *
 * Description: This is an implementation of the data type LFSR, which simulates
 *              the operation of a linear-feedback shift register.
 **************************************************************************** */

public class LFSR {

    private final int n; // number of bits in LFSR
    private int[] reg; // register as an array where reg[i] is cell position
    private final int tapPos; // tap position

    // creates an LFSR with the specified seed and tap
    public LFSR(String seed, int tap) {
        n = seed.length();

        reg = new int[n + 1];
        for (int i = 1; i < n + 1; i++) { // reg[0] is a dummy cell
            if (seed.charAt(n - i) == '0') reg[i] = 0;
            else reg[i] = 1;
        }

        tapPos = tap;
    }

    // returns the number of bits in this LFSR
    public int length() {
        return n;
    }

    // returns the ith bit of this LFSR (as 0 or 1)
    public int bitAt(int i) {
        return reg[i];
    }

    // returns a string representation of this LFSR
    public String toString() {
        // Convert register to char array and then to string
        int ASCII_NUM_CONVERT = 48; // ASCII code for '0'
        char[] regChar = new char[n];
        for (int i = 0; i < n; i++) {
            regChar[i] = (char) (reg[n - i] + ASCII_NUM_CONVERT);
        }
        String regStr = new String(regChar);
        return regStr;
    }

    // simulates one step of this LFSR and returns the new bit (as 0 or 1)
    public int step() {
        // Compute XOR of leftmost cell (rightmost array index) and tap cell
        int xor = reg[n] ^ reg[tapPos];

        // Shift-left the register (shift-right array elements);
        for (int i = n; i > 1; i--) {
            reg[i] = reg[i - 1];
        }

        // Update cell 1
        reg[1] = xor;

        return reg[1];
    }

    // simulates k steps of this LFSR and returns the k bits as a k-bit integer
    public int generate(int k) {
        // Simulate k-steps and store binary k-bit integery in array
        int[] rBinary = new int[k + 1];
        for (int i = 0; i < k; i++) {
            rBinary[i + 1] = step();
        }

        // Convert binary integer (stored from rBinary[1] to rBinary[k]) to
        // to decimal
        int rDecimal = 0; // Create/initialize decimal integer
        for (int i = 1; i < k + 1; i++) {
            rDecimal += rBinary[i] * (int) Math.pow(2.0, k - i);
        }
        return rDecimal;
    }

    // tests this class by directly calling all instance methods
    public static void main(String[] args) {
        // Create and initialize two LFSR objects with varying values for
        // seed and tap position
        LFSR lfsr0 = new LFSR("1101", 2);
        LFSR lfsr1 = new LFSR("01101000010", 9);

        // Test toString(): print string representation of LFSR objects
        StdOut.println(lfsr0);
        StdOut.println(lfsr1);

        // Test length(): print lengths of LFSR objects
        StdOut.println("lengths: " + lfsr0.length() + " " + lfsr1.length());

        // Test bitAt(): print the value of first bit and value at tap position
        int firstBit0 = lfsr0.bitAt(1);
        int firstBit1 = lfsr1.bitAt(1);
        int tapBit0 = lfsr0.bitAt(lfsr0.tapPos);
        int tapBit1 = lfsr1.bitAt(lfsr1.tapPos);

        StdOut.println("1st bits: " + firstBit0 + " " + firstBit1);
        StdOut.println("bit at tap: " + tapBit0 + " " + tapBit1);

        // Test step(): simulate 10 steps of LFSR and return rightmost bit as an
        // integer (0 or 1)
        StdOut.println(lfsr0);
        for (int i = 0; i < 10; i++) {
            int bit = lfsr0.step();
            StdOut.println(lfsr0 + " " + bit);
        }

        StdOut.println(lfsr1);
        for (int i = 0; i < 10; i++) {
            int bit = lfsr1.step();
            StdOut.println(lfsr1 + " " + bit);
        }

        // Test generate(): return a k-bit integer in decimal obtained by
        // simulating k steps of the LFSR, for k = 5; repeat 10 times
        LFSR lfsr2 = new LFSR("01101000010", 9);
        StdOut.println(lfsr2);
        for (int i = 0; i < 10; i++) {
            int r = lfsr2.generate(5);
            StdOut.println(lfsr2 + " " + r);
        }
    }

}
