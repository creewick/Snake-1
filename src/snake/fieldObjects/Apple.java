package snake.fieldObjects;

import snake.Game;
import snake.IFieldObject;
import snake.ImageFileName;

@ImageFileName(fileNames = "apple.jpg")
public class Apple implements IFieldObject {
    @Override
    public void intersectWithSnake(Game game) {
        game.getCurrentLevel().addSnakePart();
    }
}
