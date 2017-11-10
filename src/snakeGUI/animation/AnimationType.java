package snakeGUI.animation;

import snakeGUI.animation.animationObjects.ChainAnimationObject;
import snakeGUI.animation.animationObjects.ElementAnimationObject;

import java.util.HashMap;

public final class AnimationType {
    public final static HashMap<TypeOfElement, IAnimationObjectCreator> getAnimationClass;

    static {
        getAnimationClass = new HashMap<>();
        getAnimationClass.put(TypeOfElement.BLOCK, ElementAnimationObject::new);
        getAnimationClass.put(TypeOfElement.CHAIN, ChainAnimationObject::new);
    }
}
