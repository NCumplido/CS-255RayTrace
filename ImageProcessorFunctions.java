import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageProcessorFunctions {

    File selectedFile;
    BufferedImage image;
    final String DEFAULT_IMAGE_NAME = "raytrace.jpg";
    File image_file = new File(DEFAULT_IMAGE_NAME);

    public ImageProcessorFunctions() {
        initialiseImage();
    }

    private BufferedImage initialiseImage() {
        try {
            image = ImageIO.read(image_file);
            return image;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public JLabel ChangeImage(File image_file, JLabel image_icon) {
        try {
            this.image_file = image_file;
            image = ImageIO.read(image_file);
            image_icon.setIcon(new ImageIcon(image));
            return image_icon;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /*
     * This function will return a pointer to an array
     * of bytes which represent the image data in memory.
     * Using such a pointer allows fast access to the image
     * data for processing (rather than getting/setting
     * individual pixels)
     */
    public byte[] GetImageData() {
        WritableRaster WR = image.getRaster();
        DataBuffer DB = WR.getDataBuffer();
        if (DB.getDataType() != DataBuffer.TYPE_BYTE)
            throw new IllegalStateException("That's not of type byte");

        return ((DataBufferByte) DB).getData();
    }

    public JLabel ResetImage(JLabel image_icon) {
        try {
            image = ImageIO.read(image_file);
            image_icon.setIcon(new ImageIcon(image));
            return image_icon;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage Equalise() {
        // Get image dimensions, and declare loop variables
        int w = image.getWidth(), h = image.getHeight(), i, j, c;
        // Obtain pointer to data for fast processing
        byte[] data = GetImageData();
        int[] histogram;

        return image;
    }

    /*
     * This function shows how to carry out an operation on an image.
     * It obtains the dimensions of the image, and then loops through
     * the image carrying out the invert.
     */
    // public BufferedImage Invert(BufferedImage image) {
    public BufferedImage Invert() {
        // Get image dimensions, and declare loop variables
        int w = image.getWidth(), h = image.getHeight(), i, j, c;
        // Obtain pointer to data for fast processing
        byte[] data = GetImageData();

        // Shows how to loop through each pixel and colour
        // Try to always use j for loops in y, and i for loops in x
        // as this makes the code more readable
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                for (c = 0; c < 3; c++) {
                    data[c + 3 * i + 3 * j * w] = (byte) (255
                            - (data[c + 3 * i + 3 * j * w] & 255));
                } // colour loop
            } // column loop
        } // row loop
        return image;
    }

    public BufferedImage SlowGamma() {
        // Get image dimensions, and declare loop variables
        int w = image.getWidth(), h = image.getHeight(), i, j, c;
        byte[] data = GetImageData();

        return image;
    }

    public BufferedImage FastGamma() {
        // Get image dimensions, and declare loop variables
        int w = image.getWidth(), h = image.getHeight(), i, j, c;
        byte[] data = GetImageData();
        // Obtain pointer to data for fast processing

        return image;
    }

    public BufferedImage BlueFade() {
        // Get image dimensions, and declare loop variables
        int w = image.getWidth(), h = image.getHeight(), i, j, c;
        // Obtain pointer to data for fast processing
        byte[] data = GetImageData();
        int int_image[][][];
        double t;

        int_image = new int[h][w][3];

        // Copy byte data to new image taking care to treat bytes as unsigned
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                for (c = 0; c < 3; c++) {
                    int_image[j][i][c] = data[c + 3 * i + 3 * j * w] & 255;
                } // colour loop
            } // column loop
        } // row loop

        // Now carry out processing on this different data typed image (e.g. correlation
        // or "bluefade"
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                int_image[j][i][0] = 255 * j / h; // BLUE
                int_image[j][i][1] = 0; // GREEN
                int_image[j][i][2] = 0; // RED
            } // column loop
        } // row loop

        // Now copy the processed image back to the original
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                for (c = 0; c < 3; c++) {
                    data[c + 3 * i + 3 * j * w] = (byte) int_image[j][i][c];
                } // colour loop
            } // column loop
        } // row loop

        return image;
    }

    public BufferedImage Threshold() {
        // Get image dimensions, and declare loop variables
        int w = image.getWidth(), h = image.getHeight(), i, j, c;
        // Obtain pointer to data for fast processing
        byte[] data = GetImageData();

        // Shows how to loop through each pixel and colour
        // Try to always use j for loops in y, and i for loops in x
        // as this makes the code more readable
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                for (c = 0; c < 3; c++) {
                    data[c + 3 * i + 3 * j * w] = (byte) (255
                            - (data[c + 3 * i + 3 * j * w] & 255));
                } // colour loop
            } // column loop
        } // row loop
        return image;
    }

}