import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

// OK this is not best practice - maybe you'd like to extend
// the BufferedImage class for the image processing operations?
// I won't give extra marks for that though.
public class Example extends JFrame {

        final JButton BTN_INVERT = new JButton("Invert"),
                        BTN_SLOW_GAMMA = new JButton("Slow Gamma"),
                        BTN_FAST_GAMMA = new JButton("Fast Gamma"),
                        BTN_EQUAL = new JButton("Equalize"),
                        BTN_OPEN_FILE = new JButton("Open file"),
                        BTN_RESET_IMAGE = new JButton("Reset Image"),
                        BTN_THRESHOLD = new JButton("Threshold");

        String[] fadeOptions = {"Red", "Green", "Blue", "Grey"};
        final JComboBox<String> CMBOBX_FADE = new JComboBox<>(fadeOptions);

        ImageProcessorFunctions imgProcFunct;
        BufferedImage image;
        JLabel image_icon;
        JSlider val_slider;
        Container container = getContentPane();
        File selectedFile;

        GUIEventHandler handler = new GUIEventHandler();

        final String DEFAULT_IMAGE_NAME = "raytrace.jpg";

        /*
         * This function sets up the GUI and reads an image
         */
        public void Example() {
                imgProcFunct = new ImageProcessorFunctions();
                image = imgProcFunct.image;
                initialiseGUI();
                initialiseListeners();

                // ... and display everything
                pack();
                setLocationRelativeTo(null);
                setVisible(true);
                this.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.setUndecorated(true);
                this.setVisible(true);
        }

        private void initialiseListeners() {
                BTN_INVERT.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                                image_icon = imgProcFunct.Invert(image_icon);
                        }
                });
                BTN_SLOW_GAMMA.addActionListener(handler);
                BTN_FAST_GAMMA.addActionListener(handler);
                CMBOBX_FADE.addActionListener(handler);
                BTN_EQUAL.addActionListener(handler);
                BTN_OPEN_FILE.addActionListener(handler);
                BTN_RESET_IMAGE.addActionListener(handler);
                val_slider.addChangeListener(handler);
                BTN_THRESHOLD.addActionListener(handler);
        }

        private void initialiseGUI() {
                container.setLayout(new FlowLayout());
                image_icon = new JLabel(new ImageIcon(image));
                container.add(image_icon);
                container.add(BTN_INVERT);
                container.add(BTN_EQUAL);
                container.add(BTN_SLOW_GAMMA);
                container.add(BTN_FAST_GAMMA);
                container.add(CMBOBX_FADE);
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

                        if (event.getSource() == BTN_SLOW_GAMMA) {
                                image_icon = imgProcFunct.SlowGamma(image_icon, val_slider.getValue());

                        } else if (event.getSource() == BTN_FAST_GAMMA) {
                                image_icon = imgProcFunct.FastGamma(image_icon);

                        // } else if (event.getSource() == BTN_CORRELATE) {
                        //         image_icon = imgProcFunct.BlueFade(image_icon);

                        } else if (event.getSource() == BTN_EQUAL) {
                                image_icon = imgProcFunct.Equalise(image_icon);

                        } else if (event.getSource() == BTN_OPEN_FILE) {
                                FileHelper fileChooser = new FileHelper(container);
                                selectedFile = fileChooser.openDialogue();
                                image_icon = imgProcFunct.ChangeImage(selectedFile, image_icon);

                        } else if (event.getSource() == BTN_RESET_IMAGE) {
                                image_icon = imgProcFunct.ResetImage(image_icon);

                        } else if (event.getSource() == BTN_THRESHOLD) {
                                image_icon = imgProcFunct.Threshold(image_icon);
                        } else if (event.getSource() == CMBOBX_FADE){
                                int selectedFade = CMBOBX_FADE.getSelectedIndex();
                                image_icon = imgProcFunct.BlueFade(image_icon, selectedFade);
                        }
                }
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