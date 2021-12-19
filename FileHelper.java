import javax.swing.JFileChooser;
import java.io.File;
import java.awt.*;

//All of this assumes that the user selectsa .jpg file
public class FileHelper {
 
    Container container;
    //private final String FILENAME;
    public FileHelper(Container container){
        this.container = container;
    }

    public File openDialogue(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dirw"))); 
        //Desired path: C:\Users\Nich\Documents\Projects\CS-255RayTrace\raytrace.jpg
        int result = fileChooser.showOpenDialog(container);
        if (result == JFileChooser.APPROVE_OPTION) {
            // user selects a file
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
        File selectedFile = fileChooser.getSelectedFile();

        return selectedFile;
    }
}
