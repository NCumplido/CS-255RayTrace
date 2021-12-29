import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.lang.Math.*;

// OK this is not best practice - maybe you'd like to extend
// the BufferedImage class for the image processing operations?
// I won't give extra marks for that though.
public class Example extends JFrame {

        final JButton                 
        BTN_INVERT = new JButton("Invert"),
        BT_SLOW_GAMMA = new JButton("Slow Gamma"),
        BTN_FAST_GAMMA = new JButton("Fast Gamma"),
        BTN_CORRELATE = new JButton("Correlate"),
        BTN_EQUAL = new JButton("Equalize"),
        BTN_OPEN_FILE = new JButton("Open file"),
        BTN_RESET_IMAGE = new JButton("Reset Image"),
        BTN_THRESHOLD = new JButton("Threshold");

        JLabel image_icon;
        BufferedImage image;
        JSlider val_slider;
        Container container = getContentPane();
        File selectedFile;

        GUIEventHandler handler = new GUIEventHandler();

        final String DEFAULT_IMAGE_NAME = "raytrace.jpg";

        /*
         * This function sets up the GUI and reads an image
         */
        public void Example() {

                initialiseImage();
                initialiseGUI();
                initialiseHandler();

                // ... and display everything
                pack();
                setLocationRelativeTo(null);
                setVisible(true);
                this.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.setUndecorated(true);
                this.setVisible(true);
        }

        private void initialiseHandler() {
                BTN_INVERT.addActionListener(handler);
                BT_SLOW_GAMMA.addActionListener(handler);
                BTN_FAST_GAMMA.addActionListener(handler);
                BTN_CORRELATE.addActionListener(handler);
                BTN_EQUAL.addActionListener(handler);
                BTN_OPEN_FILE.addActionListener(handler);
                BTN_RESET_IMAGE.addActionListener(handler);
                val_slider.addChangeListener(handler);
                BTN_THRESHOLD.addChangeListener(handler);
        }

        private void initialiseImage() {
                File image_file = new File(DEFAULT_IMAGE_NAME);
                try {
                        image = ImageIO.read(image_file);
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        private void initialiseGUI() {
                container.setLayout(new FlowLayout());
                image_icon = new JLabel(new ImageIcon(image));
                container.add(image_icon);
                container.add(BTN_INVERT);
                container.add(BTN_EQUAL);
                container.add(BT_SLOW_GAMMA);
                container.add(BTN_FAST_GAMMA);
                container.add(BTN_CORRELATE);
                container.add(BTN_RESET_IMAGE);
                container.add(BTN_OPEN_FILE);
                container.add(BTN_THRESHOLD);

                val_slider = new JSlider(0, 100);
                container.add(val_slider);
                val_slider.setMajorTickSpacing(50);
                val_slider.setMinorTickSpacing(10);
                val_slider.setPaintTicks(true);
                val_slider.setPaintLabels(true);
                // see
                // http://docs.oracle.com/javase/7/docs/api/javax/swing/JSlider.html
                // for documentation (e.g. how to get the value, how to display vertically if
                // you want)
        }

        /*
         * This is the event handler for the application
         */
        private class GUIEventHandler implements ActionListener, ChangeListener {
                // Change handler (e.g. for sliders)
                public void stateChanged(ChangeEvent e) {
                        System.out.println(val_slider.getValue());
                        // you could pass the value to another function to change something
                        // then update the image
                }

                public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == BTN_INVERT) {
                                // Call image processing function
                                image = Invert(image);

                                // Update image
                                image_icon.setIcon(new ImageIcon(image));
                        } else if (event.getSource() == BT_SLOW_GAMMA) {
                                // Call image processing function
                                image = SlowGamma(image);

                                // Update image
                                image_icon.setIcon(new ImageIcon(image));
                        } else if (event.getSource() == BTN_FAST_GAMMA) {
                                // Call image processing function
                                image = FastGamma(image);

                                // Update image
                                image_icon.setIcon(new ImageIcon(image));
                        } else if (event.getSource() == BTN_CORRELATE) {
                                // Call image processing function
                                image = BlueFade(image);

                                // Update image
                                image_icon.setIcon(new ImageIcon(image));
                        } else if (event.getSource() == BTN_EQUAL) {
                                // Call function
                                image = Equalise(image);

                                // Update image
                                image_icon.setIcon(new ImageIcon(image));
                        } else if (event.getSource() == BTN_OPEN_FILE) {
                                FileHelper fileChooser = new FileHelper(container);
                                selectedFile = fileChooser.openDialogue();
                                ChangeImage(selectedFile);
                        } else if (event.getSource() == BTN_RESET_IMAGE) {
                                ResetImage();
                        } else if (event.getSource() == BTN_THRESHOLD) {
                                Threshold(image);
                        }
                }
        }

        /*
         * This function will return a pointer to an array
         * of bytes which represent the image data in memory.
         * Using such a pointer allows fast access to the image
         * data for processing (rather than getting/setting
         * individual pixels)
         */
        public static byte[] GetImageData(BufferedImage image) {
                WritableRaster WR = image.getRaster();
                DataBuffer DB = WR.getDataBuffer();
                if (DB.getDataType() != DataBuffer.TYPE_BYTE)
                        throw new IllegalStateException("That's not of type byte");

                return ((DataBufferByte) DB).getData();
        }

        public void ResetImage() {
                File image_file = new File(DEFAULT_IMAGE_NAME);
                try {
                        image = ImageIO.read(image_file);
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                // image_icon=new JLabel(new ImageIcon(image));
                image_icon.setIcon(new ImageIcon(image));
        }

        public BufferedImage Equalise(BufferedImage image) {
                // Get image dimensions, and declare loop variables
                int w = image.getWidth(), h = image.getHeight(), i, j, c;
                // Obtain pointer to data for fast processing
                byte[] data = GetImageData(image);
                int[] histogram;

                return image;
        }

        /*
         * This function shows how to carry out an operation on an image.
         * It obtains the dimensions of the image, and then loops through
         * the image carrying out the invert.
         */
        public BufferedImage Invert(BufferedImage image) {
                // Get image dimensions, and declare loop variables
                int w = image.getWidth(), h = image.getHeight(), i, j, c;
                // Obtain pointer to data for fast processing
                byte[] data = GetImageData(image);

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

        public BufferedImage SlowGamma(BufferedImage image) {
                // Get image dimensions, and declare loop variables
                int w = image.getWidth(), h = image.getHeight(), i, j, c;
                byte[] data = GetImageData(image);

                return image;
        }

        public BufferedImage FastGamma(BufferedImage image) {
                // Get image dimensions, and declare loop variables
                int w = image.getWidth(), h = image.getHeight(), i, j, c;
                byte[] data = GetImageData(image);
                // Obtain pointer to data for fast processing

                return image;
        }

        public BufferedImage BlueFade(BufferedImage image) {
                // Get image dimensions, and declare loop variables
                int w = image.getWidth(), h = image.getHeight(), i, j, c;
                // Obtain pointer to data for fast processing
                byte[] data = GetImageData(image);
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

        public void ChangeImage(File image_file) {
                try {
                        image = ImageIO.read(image_file);
                        image_icon.setIcon(new ImageIcon(image));
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

        }

        public BufferedImage Threshold(BufferedImage image) {
                // Get image dimensions, and declare loop variables
                int w = image.getWidth(), h = image.getHeight(), i, j, c;
                // Obtain pointer to data for fast processing
                byte[] data = GetImageData(image);

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

        public static void main(String[] args) throws IOException {

                Example e = new Example();
                e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                e.Example();

                SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                                new Example().setVisible(true);
                        }
                });
        }
}