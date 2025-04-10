package util.processors;

import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;


public class ImageSpliter {

    public List<ImageIcon> splitImage (File imageFile, int n) {

        List<ImageIcon> images = new ArrayList<>();
        try {
            BufferedImage image = ImageIO.read(imageFile);
            int width = image.getWidth() / n;
            int height = image.getHeight() / n;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    images.add(new ImageIcon(image.getSubimage(j * width, i * height, width, height)));
                }
            }

            return images;
        } catch (IOException e) {
            return images;
        }

    }

}