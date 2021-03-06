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
        field = generateField(width, height);

        Snake snake = addSnake(0, 0);
        field[4][4] = new Apple();

        return new Level(field, applesToGenerateAmount, snake);
    }

    public IFieldObject[][] generateField(int width, int height) {
        return new GridLevelField(width, height)
                .addGrid()
                .addPasses()
                .toArray();
    }

    private Snake addSnake(int x, int y) {
        field[y][x] = new SnakeHead(x, y, Direction.ZERO, null, null);
        return new Snake(x, y, Direction.ZERO);
    }
}
