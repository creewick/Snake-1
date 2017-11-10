package snakeGUI;

import javafx.util.Pair;
import snake.*;
import snakeGUI.animation.AnimationObject;
import snakeGUI.animation.AnimationType;
import snakeGUI.animation.animationObjects.ElementAnimationObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;

public class ImageSaver {
    private HashMap<String, ImageIcon> images;
    private static Pair<String[], ImageIcon> defaultImage;
    private HashMap<Class, AnimationObject> animations;
    private int cellSize;

    public ImageSaver(int cellSize) {
        setCellSize(cellSize);
        images = new HashMap<>();
        animations = new HashMap<>();

        String[] defaultImages = new String[1];
        defaultImages[0] = Settings.IMAGE_URL + Settings.DEFAULT_IMAGE_FILE_NAME;
        defaultImage = new Pair<>(
            defaultImages,
            getImageIcon(Settings.DEFAULT_IMAGE_FILE_NAME)
        );
    }

    private ImageIcon getImageIcon(String fileName) {
        Image img = new ImageIcon(Settings.IMAGE_URL + fileName)
                .getImage()
                .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon();
        imageIcon.setImage(img);

        return imageIcon;
    }

    public ImageIcon getIcon(IFieldObject obj) {
        if (!animations.containsKey(obj.getClass())) {
            addInformationAboutClass(obj);
        }

        String fileName = animations.get(obj.getClass()).getImageFileName(obj);
        return images.get(fileName);
    }

    private void addInformationAboutClass(IFieldObject obj) {
        Class objClass = obj.getClass();
        ImageFileName annotation = obj.getClass().getAnnotation(ImageFileName.class);
        String[] fileNames;

        if (annotation == null) {
            animations.put(objClass, new ElementAnimationObject(defaultImage.getKey()));
            fileNames = defaultImage.getKey();
        }
        else {
            fileNames = annotation.fileNames();
            animations.put(objClass,
                    AnimationType.getAnimationClass.get(
                            annotation.type()).createFieldObject(fileNames));
        }

        HashMap<String, ImageIcon> fileImages = getFileImages(fileNames);

        for (Map.Entry<String, ImageIcon> fileImage : fileImages.entrySet()) {
            images.put(fileImage.getKey(), fileImage.getValue());
        }
    }

    private HashMap<String, ImageIcon> getFileImages(String[] fileNames) {
        HashMap<String, ImageIcon> result = new HashMap<>();

        for (String fileName : fileNames) {
            ImageIcon fileImage;
            if (!new File(Settings.IMAGE_URL + fileName).exists())
                fileImage = defaultImage.getValue();
            else
                fileImage = getImageIcon(fileName);
            result.put(fileName, fileImage);
        }

        return result;
    }

    private void setCellSize(int cellSize) {
        if (cellSize < 0)
            throw new IllegalArgumentException("Cell size can't be negative");

        this.cellSize = cellSize;
    }
}
