package snakeGUI.animation;

import snakeGUI.animation.AnimationObject;

public interface IAnimationObjectCreator {
    AnimationObject createFieldObject(String[] fileImages);
}
