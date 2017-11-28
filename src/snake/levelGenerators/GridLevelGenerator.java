package snake.levelGenerators;

import snake.*;
import snake.fieldObjects.Apple;
import snake.fieldObjects.Empty;
import snake.fieldObjects.Wall;
import snake.fieldObjects.listed.SnakeHead;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridLevelGenerator implements ILevelGenerator {

    private int width, height;
    private IFieldObject[][] field;

    public Level generateLevel(int width, int height,
                               int applesOnFieldAmount,
                               int applesToGenerateAmount) {
        this.width = width;
        this.height = height;
        field = new Field(width, height)
                .addGrid()
                .addPasses()
                .addDoors(new Vector[]{new Vector(0, 0)})
                .toArray();

        Snake snake = addSnake(1, 1);
        field[3][3] = new Apple();

        return new Level(field, applesToGenerateAmount, snake);
    }

    private Snake addSnake(int x, int y) {
        field[y][x] = new SnakeHead(x, y, Direction.ZERO, null, null);
        return new Snake(x, y, Direction.ZERO);
    }
}
