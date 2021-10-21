import java.awt.image.BufferedImage;

public class Method
{
    public static int getInt(int position, int length)
    {
        int value = 0;
        if (Main.order)//True="II"
        {
            for (int i = 0; i < length; i++)
            {
                value |= Byte.toUnsignedInt(Main.data[position + i]) << (i * 8);
            }
        }
        else//False="MM"
        {
            for (int i = 0; i < length; i++)
            {
                value |= Byte.toUnsignedInt(Main.data[position + length - 1 - i]) << (i * 8);
            }
        }
        return value;
    }

    public static BufferedImage gray_processing(BufferedImage image)
    {
        for (int i = 0; i < Main.imageLength; i++)
        {
            for (int j = 0; j < Main.imageWidth; j++)
            {
                int R = Main.colour[i * Main.imageWidth + j].getRed();
                int G = Main.colour[i * Main.imageWidth + j].getGreen();
                int B = Main.colour[i * Main.imageWidth + j].getBlue();
                int Y = (int)(0.299 * R + 0.587 * G + 0.114 * B);
                int colour = (Y << 16) | (Y << 8) | Y;
                image.setRGB(j, i, colour);
            }
        }
        return image;
    }

    public static BufferedImage ordered_dithering(BufferedImage image)
    {
        int[][] matrix = {{1 ,49 ,13 ,61 ,4 ,52 ,16 ,64 },
                {33 ,17 ,45 ,29 ,36 ,20 ,48 ,32 },
                {9 ,57 ,5 ,37 ,12 ,60 ,8 ,40 },
                {41 ,25 ,53 ,21 ,44 ,28 ,56 ,24},
                {3 ,51 ,15 ,63 ,2 ,50 ,14 ,62},
                {35 ,19 ,47 ,31 ,34 ,18 ,46 ,30},
                {11 ,59 ,7 ,55 ,10 ,58 ,6 ,54},
                {43 ,27 ,39 ,23 ,42 ,26 ,38 ,22}
        };
        for (int i = 0; i < Main.imageLength; i++)
        {
            for (int j = 0; j < Main.imageWidth; j++)
            {
                int R = Main.colour[i * Main.imageWidth + j].getRed();
                int G = Main.colour[i * Main.imageWidth + j].getGreen();
                int B = Main.colour[i * Main.imageWidth + j].getBlue();
                int Y = (int)(0.299 * R + 0.587 * G + 0.114 * B);
                Y = (int) Math.floor(Y/(256.0/65));
                if (Y > matrix[j % 8][i % 8])
                {
                    Y = 255;
                }
                else
                {
                    Y = 0;
                }
                int colour = (Y << 16) | (Y << 8) | Y;
                image.setRGB(j, i, colour);
            }
        }
        return image;
    }

    public static BufferedImage adjusting_the_dynamic_range(BufferedImage image)
    {
        int avg = 0;
        for (int i = 0; i < Main.imageLength; i++)
        {
            for (int j = 0; j < Main.imageWidth; j++)
            {
                int R = Main.colour[i * Main.imageWidth + j].getRed();
                int G = Main.colour[i * Main.imageWidth + j].getGreen();
                int B = Main.colour[i * Main.imageWidth + j].getBlue();
                avg += (int)(0.299 * R + 0.587 * G + 0.114 * B);
            }
        }
        avg /= Main.imageLength * Main.imageWidth;
        for (int i = 0; i < Main.imageLength; i++)
        {
            for (int j = 0; j < Main.imageWidth; j++)
            {
                int R = Main.colour[i * Main.imageWidth + j].getRed();
                int G = Main.colour[i * Main.imageWidth + j].getGreen();
                int B = Main.colour[i * Main.imageWidth + j].getBlue();
                int Y = (int)(0.299 * R + 0.587 * G + 0.114 * B);
                int U = (int)(-0.299 * R - 0.587 * G + 0.886 * B);
                int V = (int)(0.701 * R - 0.587 * G - 0.114 * B);
                if (Y > avg)
                {
                    Y *= 0.85;
                }
                else if (Y < avg/2)
                {
                    Y *= 2;//1.75
                }
                R = (Y                     + V);
                G = (int)(Y - 0.194 * U - 0.510 * V);
                B = (Y         + U);
                if (R > 255)
                {
                    R = 255;
                }
                if (R < 0)
                {
                    R = 0;
                }
                if (G > 255)
                {
                    G = 255;
                }
                if (G < 0)
                {
                    G = 0;
                }
                if (B > 255)
                {
                    B = 255;
                }
                if (B < 0)
                {
                    B = 0;
                }
                int color = (R << 16) | (G << 8) | B;
                image.setRGB(j, i, color);
            }
        }
        return image;
    }
}
