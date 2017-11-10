package snakeGUI.animation;

import snake.IFieldObject;

public abstract class AnimationObject {
    protected String[] fileImages;

    public abstract String getImageFileName(IFieldObject obj);

    public AnimationObject(String[] fileImages) {
        this.fileImages = fileImages;
    }
}
