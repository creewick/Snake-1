package snake.fieldObjects.listed;

import snake.ImageFileName;
import snake.Vector;

@ImageFileName(fileNames = "snakehead.jpg")
public class SnakeHead extends SnakePart {
    public SnakeHead(int x, int y, Vector direction, SnakePart parent, SnakePart child) {
        super(x, y, direction, parent, child);
    }
}
