package snake;

import snake.fieldObjects.listed.SnakePart;

public interface IObjectCreator {
    IFieldObject createFieldObject(
            int x,
            int y,
            Vector vector,
            SnakePart parent,
            SnakePart child);
}