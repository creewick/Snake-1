package snake.fieldObjects;

import snake.Game;
import snake.IFieldObject;
import snake.ImageFileName;

@ImageFileName(fileNames = "empty.jpg")
public class Empty implements IFieldObject {
    @Override
    public void intersectWithSnake(Game game) {
    }
}
