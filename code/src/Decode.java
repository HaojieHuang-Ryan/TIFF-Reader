import javax.swing.*;
import java.awt.*;

public class Decode
{
    public static DE_type[] TypeArray = {
            new DE_type("???",0), new DE_type("BYTE",1), new DE_type("ASCII",1), new DE_type("SHORT",2),
            new DE_type("LONG",4), new DE_type("RATIONAL",8), new DE_type("SBYTE",1),
            new DE_type("UNDEFINED",1), new DE_type("SSHORT",1), new DE_type("SLONG",1),
            new DE_type("SRATIONAL",1), new DE_type("FLOAT",4), new DE_type("DOUBLE",8)
    };

    public static int getIFH()
    {
        int byteOrder = Method.getInt(0,2);
        if (byteOrder == 18761)
        {
            Main.order = true;
        }
        else if (byteOrder == 19789)
        {
            Main.order = false;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Not II or MM.\nThe process is over!");
            System.exit(0);
        }
        if(42 == Method.getInt(2,2))
        {
            Main.version = true;
        }
        else
        {
            Main.version = false;
            JOptionPane.showMessageDialog(null, "Not a TIFF image file.\nThe process is over!");
            System.exit(0);
        }
        return Method.getInt(4, 4);
    }

    public static void getIFD(int position)
    {
        int count = position;
        int DEcount = Method.getInt(count, 2);
        count += 2;
        for (int i = 0; i < DEcount; i++)
        {
            decodeDE(count);
            count += 12;
        }
        decodeStrips();
    }

    public static void decodeDE(int position)
    {
        int tag = Method.getInt(position, 2);
        int type = Method.getInt(position + 2, 2);
        int length = Method.getInt(position + 4, 4);
        int offset = position + 8;
        int totalSize = TypeArray[type].size * length;
        if (totalSize > 4)
        {
            offset = Method.getInt(offset, 4);
        }
        getDE(tag, type, length, offset);
    }

    public static void getDE(int tag, int type, int length, int offset)
    {
        int type_size = TypeArray[type].size;
        switch (tag)
        {
            case 256:
            {//Width
                Main.imageWidth = Method.getInt(offset, type_size);
                break;
            }
            case 257:
            {//Length
                if (type == 3)
                {
                    Main.imageLength = Method.getInt(offset, type_size);
                }
                break;
            }
            case 273:
            {//stripOffsets
                for (int i = 0; i < length; i++)
                {
                    int num = Method.getInt(offset + i * type_size, type_size);
                    Main.stripOffsets.add(num);
                }
                break;
            }
            default:
                break;
        }
    }

    public static void decodeStrips()
    {
        Main.colour = new Color[Main.imageWidth* Main.imageLength];
        int position = Main.stripOffsets.get(0);
        for (int i = 0; i < Main.imageWidth*Main.imageLength; i++)
        {
            int Red = Byte.toUnsignedInt(Main.data[position+3*i]);
            int Green = Byte.toUnsignedInt(Main.data[position+3*i+1]);
            int Blue = Byte.toUnsignedInt(Main.data[position+3*i+2]);
            Main.colour[i] = new Color(Red,Green,Blue);
        }
    }

    public static class DE_type
    {
        String name;
        int size;
        public DE_type(String str, int num)
        {
            name = str;
            size = num;
        }
    }
}