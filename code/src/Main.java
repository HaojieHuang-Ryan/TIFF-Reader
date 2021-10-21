import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Main
{
    public static byte[] data = null;
    public static boolean order;//True=little endianness False=big endianness
    public static boolean version;
    public static int imageWidth = 0;
    public static int imageLength = 0;
    public static List<Integer> stripOffsets = new ArrayList<>();
    public static Color[] colour;
    public static BufferedImage image = null;
    public static BufferedImage image_gray = null;
    public static BufferedImage image_order = null;
    public static BufferedImage image_dynamic = null;


    public static void main(String[] args) throws IOException
    {
        //choose file
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.tif","tif");
        fileChooser.setFileFilter(filter);
        File current_directory = new File(System.getProperty("user.dir"));//get current directory
        fileChooser.setCurrentDirectory(current_directory.getParentFile());//set current directory to file chooser
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null))
        {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            InputStream stream = Files.newInputStream(Paths.get(path));
            data = stream.readAllBytes();
            int IFD = Decode.getIFH();
            Decode.getIFD(IFD);
            image = new BufferedImage(imageWidth, imageLength, BufferedImage.TYPE_INT_RGB);
            image_gray = new BufferedImage(imageWidth, imageLength, BufferedImage.TYPE_INT_RGB);
            image_order = new BufferedImage(imageWidth, imageLength, BufferedImage.TYPE_INT_RGB);
            image_dynamic = new BufferedImage(imageWidth, imageLength, BufferedImage.TYPE_INT_RGB);
            for (int i=0; i < imageLength; i++)
            {
                for (int j=0; j < imageWidth; j++)
                {
                    image.setRGB(j,i,colour[i * imageWidth + j].getRGB());
                    image_gray.setRGB(j,i,colour[i * imageWidth + j].getRGB());
                    image_order.setRGB(j,i,colour[i * imageWidth + j].getRGB());
                    image_dynamic.setRGB(j,i,colour[i * imageWidth + j].getRGB());
                }
            }
            boolean quit = false;
            int step = 0;
            while (!quit)
            {
                switch (step) {
                    case 0 ->
                            {
                                quit = ShowImage.show(image, step);
                                step++;
                                step %= 4;
                            }
                    case 1 ->
                            {
                                quit = ShowImage.show(Method.gray_processing(image_gray), step);
                                step++;
                                step %= 4;
                            }
                    case 2 ->
                            {
                                quit = ShowImage.show(Method.ordered_dithering(image_order), step);
                                step++;
                                step %= 4;
                            }
                    case 3 ->
                            {
                                quit = ShowImage.show(Method.adjusting_the_dynamic_range(image_dynamic), step);
                                step++;
                                step %= 4;
                            }
                }
            }
            JOptionPane.showMessageDialog(null, "The process is over!");
        }
        else//not select file
        {
            JOptionPane.showMessageDialog(null, "Not select any file.\nThe process is over!");
        }
        System.exit(0);
    }
}
