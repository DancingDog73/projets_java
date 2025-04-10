package util.processors;

import java.io.File;
import java.nio.file.*;
import java.util.*;


public class ImageGenerator {

    public File generateImage () {
        Path fPath = Paths.get("../res/images");
        String  p = fPath.toAbsolutePath().toString();
        File imagesDir = new File(p);
        File[] images = imagesDir.listFiles();
        return images[new Random().nextInt(images.length)];
    }

}