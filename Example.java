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

    JButton invert_button, 
    slow_gamma_button, 
    fast_gamma_button, 
    correlate_button, 
    equal_button,
    open_file_Button;
    JLabel image_icon;
    BufferedImage image;
    JSlider val_slider;
    Container container = getContentPane();
    File selectedFile;

    /*
        This function sets up the GUI and reads an image
    */
    public void Example() throws IOException {
        
        File image_file = new File("raytrace.jpg");
        
        image = ImageIO.read(image_file);

        container.setLayout(new FlowLayout());
        
        image_icon=new JLabel(new ImageIcon(image));
        container.add(image_icon);
 
        invert_button = new JButton("Invert");
        container.add(invert_button);

        equal_button = new JButton("Equalize");
        container.add(equal_button);
        
        slow_gamma_button = new JButton("Slow Gamma");
        container.add(slow_gamma_button);
        fast_gamma_button = new JButton("Fast Gamma");
        container.add(fast_gamma_button);
        
        correlate_button = new JButton("Correlate");
        container.add(correlate_button);

        open_file_Button = new JButton("Open file");
        container.add(open_file_Button);
		
		val_slider = new JSlider(0,100);
		container.add(val_slider);
		val_slider.setMajorTickSpacing(50);
		val_slider.setMinorTickSpacing(10);
		val_slider.setPaintTicks(true);
		val_slider.setPaintLabels(true);
		//see
		//http://docs.oracle.com/javase/7/docs/api/javax/swing/JSlider.html
		//for documentation (e.g. how to get the value, how to display vertically if you want)
        		

        GUIEventHandler handler = new GUIEventHandler();

        invert_button.addActionListener(handler);
        slow_gamma_button.addActionListener(handler);
        fast_gamma_button.addActionListener(handler);
        correlate_button.addActionListener(handler);
        equal_button.addActionListener(handler);
        open_file_Button.addActionListener(handler);
	val_slider.addChangeListener(handler);
        // ... and display everything
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
    }
    
    /*
        This is the event handler for the application
    */
    private class GUIEventHandler implements ActionListener, ChangeListener {
   		//Change handler (e.g. for sliders)
        public void stateChanged(ChangeEvent e) {
			System.out.println(val_slider.getValue());
			//you could pass the value to another function to change something
                        //then update the image
		}

         public void actionPerformed(ActionEvent event) {
                 if (event.getSource()==invert_button) {
                        // Call image processing function
                        image=Invert(image);
        
                        // Update image
                        image_icon.setIcon(new ImageIcon(image));
                 } else if (event.getSource()==slow_gamma_button) {
                        // Call image processing function
                        image=SlowGamma(image);
        
                        // Update image
                        image_icon.setIcon(new ImageIcon(image));
                 } else if (event.getSource()==fast_gamma_button) {
                        // Call image processing function
                        image=FastGamma(image);
        
                        // Update image
                        image_icon.setIcon(new ImageIcon(image));
                 } else if (event.getSource()==correlate_button) {
                        // Call image processing function
                        image=BlueFade(image);
        
                        // Update image
                        image_icon.setIcon(new ImageIcon(image));
                 } else if (event.getSource()==equal_button) {
						//Call function
						image=Equalise(image);
						
                        // Update image
                        image_icon.setIcon(new ImageIcon(image));
                }else if (event.getSource()==open_file_Button) {
                        FileHelper fileChooser = new FileHelper(container);
                        selectedFile = fileChooser.openDialogue();
                        ChangeImage(selectedFile);
                        }
         }
    }

    /*
        This function will return a pointer to an array
        of bytes which represent the image data in memory.
        Using such a pointer allows fast access to the image
        data for processing (rather than getting/setting
        individual pixels)
    */
    public static byte[] GetImageData(BufferedImage image) {
            WritableRaster WR=image.getRaster();
            DataBuffer DB=WR.getDataBuffer();
            if (DB.getDataType() != DataBuffer.TYPE_BYTE)
                throw new IllegalStateException("That's not of type byte");
          
            return ((DataBufferByte) DB).getData();
    }


    public BufferedImage Equalise(BufferedImage image) {
            //Get image dimensions, and declare loop variables
            int w=image.getWidth(), h=image.getHeight(), i, j, c;
            //Obtain pointer to data for fast processing
            byte[] data = GetImageData(image);
			int[] histogram;

            return image;
    }

    /*
        This function shows how to carry out an operation on an image.
        It obtains the dimensions of the image, and then loops through
        the image carrying out the invert.
    */
    public BufferedImage Invert(BufferedImage image) {
            //Get image dimensions, and declare loop variables
            int w=image.getWidth(), h=image.getHeight(), i, j, c;
            //Obtain pointer to data for fast processing
            byte[] data = GetImageData(image);
            
            //Shows how to loop through each pixel and colour
            //Try to always use j for loops in y, and i for loops in x
            //as this makes the code more readable
            for (j=0; j<h; j++) {
                    for (i=0; i<w; i++) {
                            for (c=0; c<3; c++) {
                                    data[c+3*i+3*j*w]=(byte) (255-(data[c+3*i+3*j*w]&255));
                            } // colour loop
                    } // column loop
            } // row loop
            return image;
    }

    public BufferedImage SlowGamma(BufferedImage image) {
            //Get image dimensions, and declare loop variables
            int w=image.getWidth(), h=image.getHeight(), i, j, c;
			byte[] data = GetImageData(image);

            return image;
    }
	
    public BufferedImage FastGamma(BufferedImage image) {
            //Get image dimensions, and declare loop variables
            int w=image.getWidth(), h=image.getHeight(), i, j, c;
			byte[] data = GetImageData(image);
            //Obtain pointer to data for fast processing

            return image;
    }
    
    public BufferedImage BlueFade(BufferedImage image) {
            //Get image dimensions, and declare loop variables
            int w=image.getWidth(), h=image.getHeight(), i, j, c;
            //Obtain pointer to data for fast processing
            byte[] data = GetImageData(image);
            int int_image[][][];
            double t;
            
            int_image = new int[h][w][3];
            
            // Copy byte data to new image taking care to treat bytes as unsigned
            for (j=0; j<h; j++) {
                    for (i=0; i<w; i++) {
                            for (c=0; c<3; c++) {
                                    int_image[j][i][c]=data[c+3*i+3*j*w]&255;
                            } // colour loop
                    } // column loop
            } // row loop
            
            // Now carry out processing on this different data typed image (e.g. correlation or "bluefade"
            for (j=0; j<h; j++) {
                    for (i=0; i<w; i++) {
                                int_image[j][i][0]=255*j/h; //BLUE
                                int_image[j][i][1]=0; //GREEN
                                int_image[j][i][2]=0; //RED
                    } // column loop
            } // row loop
            
            // Now copy the processed image back to the original
            for (j=0; j<h; j++) {
                    for (i=0; i<w; i++) {
                            for (c=0; c<3; c++) {
                                    data[c+3*i+3*j*w]=(byte) int_image[j][i][c];
                            } // colour loop
                    } // column loop
            } // row loop
            

            return image;
    }

    public void ChangeImage(File image_file){
        try {
                image = ImageIO.read(image_file);
                image_icon.setIcon(new ImageIcon(image));
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
 
       Example e = new Example();
       e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       e.Example();
      
       SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				new Example().setVisible(true);
			}
		});
    }
}