import java.awt.Color;

public class PhotoMagic {

    // returns a new picture that has a transformed copy of given picture,
    // using the given lfsr
    public static Picture transform(Picture picture, LFSR lfsr) {
        // Get width and height of input picture
        int width = picture.width();
        int height = picture.height();

        // Create new picture for output with same width and height as input
        Picture target = new Picture(width, height);
        int RGB_BITS = 8; // each color channel uses 8 bits

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                // Get color value of current pixel and separate into RGB
                Color color = picture.get(col, row);
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                // Generate 8-bit int from LFSR and XOR with each color value
                // to obtain scrambled RGB channels; set pixel in target picture
                // to new color using scrambled RGB channels
                int rScrambled = r ^ lfsr.generate(RGB_BITS);
                int gScrambled = g ^ lfsr.generate(RGB_BITS);
                int bScrambled = b ^ lfsr.generate(RGB_BITS);
                target.set(col, row,
                           new Color(rScrambled, gScrambled, bScrambled));
            }
        }
        return target;
    }

    // takes name of image file and description of an lfsr as command-line
    // arguments; displays a copy of the image that is "encrypted" using LFSR
    public static void main(String[] args) {
        // Read image file and LFSR description from command line
        Picture pic = new Picture(args[0]);
        LFSR lfsr = new LFSR(args[1], Integer.parseInt(args[2]));

        // Transform and display encrypted/decrypted picture
        Picture transPic = transform(pic, lfsr);
        transPic.show();
    }

}
