package snake.levelGenerators;

import snake.*;
import snake.fieldObjects.Apple;
import snake.fieldObjects.listed.SnakeHead;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridLevelGenerator implements ILevelGenerator {

    private int width, height;
    private IFieldObject[][] field;

    public Level generateLevel(int width, int height,
                               int applesOnFieldAmount,
                               int applesToGenerateAmount) {
        this.width = width;
        this.height = height;
        List<Vector> doors = Arrays.asList(
                new Vector(0,0),
                new Vector(width - 1, height - 1),
                new Vector(2, 0),
                new Vector(0, 18)
            );
        field = generateField(width, height, doors);

        Snake snake = addSnake(1, 1);
        field[3][3] = new Apple();

        return new Level(field, applesToGenerateAmount, snake);
    }

    public IFieldObject[][] generateField(int width, int height, List<Vector> doors) {
        return new GridLevelField(width, height)
                .addGrid()
                .addPasses()
                .addDoors(doors)
                .toArray();
    }

    private Snake addSnake(int x, int y) {
        field[y][x] = new SnakeHead(x, y, Direction.ZERO, null, null);
        return new Snake(x, y, Direction.ZERO);
    }
}
