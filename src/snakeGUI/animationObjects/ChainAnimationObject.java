package snakeGUI.animationObjects;

import snake.IFieldObject;
import snake.IListedFieldObject;
import snakeGUI.AnimationObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChainAnimationObject extends AnimationObject {
    private Integer firstChildImageIndex;
    private List<IListedFieldObject> elementsOrder;
    private HashMap<IListedFieldObject, Boolean> isTakeImage;

    public ChainAnimationObject(String[] fileImages) {
        super(fileImages);
        firstChildImageIndex = 0;
        elementsOrder = new ArrayList<>();
        isTakeImage = new HashMap<>();
    }

    @Override
    public String getImageFileName(IFieldObject obj) {
        IListedFieldObject listedObj = (IListedFieldObject) obj;
        if (listedObj == null)
            throw new IllegalArgumentException();

        if (!elementsOrder.contains(listedObj))
            generateChain((IListedFieldObject) obj);

        isTakeImage.put(listedObj, Boolean.TRUE);
        int indexOfObjOrder = elementsOrder.indexOf(obj);
        int indexOfImage = (firstChildImageIndex + indexOfObjOrder)
                % fileImages.length;

        shiftAnimationIfNeed(listedObj);
        return fileImages[indexOfImage];
    }

    private void shiftAnimationIfNeed(IListedFieldObject obj) {
        for (Map.Entry<IListedFieldObject, Boolean> entry : isTakeImage.entrySet()) {
            if (!entry.getValue())
                return;
        }

        firstChildImageIndex = (firstChildImageIndex + 1) % fileImages.length;
        generateChain(obj);
    }

    private void generateChain(IListedFieldObject obj) {
        elementsOrder = new ArrayList<>();
        isTakeImage = new HashMap<>();

        IListedFieldObject current = obj;
        while (current.getParent() != null)
            current = current.getParent();

        Boolean isHead = Boolean.TRUE;

        while (current != null) {
            elementsOrder.add(current);
            isTakeImage.put(current, isHead);
            isHead = Boolean.FALSE;
            current = current.getChild();
        }
    }
}
