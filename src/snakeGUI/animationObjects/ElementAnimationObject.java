package snakeGUI.animationObjects;

import snake.IFieldObject;
import snakeGUI.AnimationObject;

import java.util.HashMap;

public class ElementAnimationObject extends AnimationObject {
    private HashMap<IFieldObject, Integer> indexes;

    public ElementAnimationObject(String[] fileImages) {
        super(fileImages);
        indexes = new HashMap<>();
    }

    @Override
    public String getImageFileName(IFieldObject obj) {
        Integer result = indexes.computeIfAbsent(obj, a -> 0);
        Integer nextValue = (result + 1) % fileImages.length;
        indexes.put(obj, nextValue);

        return fileImages[result];
    }
}
