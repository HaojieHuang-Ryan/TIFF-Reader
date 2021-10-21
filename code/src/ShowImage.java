import javax.swing.*;
import java.awt.image.BufferedImage;

public class ShowImage
{
    public static boolean show(BufferedImage image, int step)
    {
        String message = null;
        if (step == 0)
        {
            message = "Colored image";
        }
        else if (step ==1)
        {
            message = "Grayscale";
        }
        else if (step ==2)
        {
            message = "Ordered dithering";
        }
        else if (step ==3)
        {
            message = "Dynamic Range adjustment";
        }
        ImageIcon icon = new ImageIcon(image);
        String[] options = {"Next","Quit",};
        int return_num;

        return_num = JOptionPane.showOptionDialog(null,message,
                "Displays image",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,
                icon,options,options[0]);
        return return_num != 0;
    }
}
