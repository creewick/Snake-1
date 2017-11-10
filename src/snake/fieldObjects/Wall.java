package snake.fieldObjects;

import snake.Game;
import snake.IFieldObject;
import snake.ImageFileName;

@ImageFileName(fileNames = "wall.jpg")
public class Wall implements IFieldObject {
    @Override
    public void intersectWithSnake(Game game) {
        game.isGameOver = true;
    }
}
