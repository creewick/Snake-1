package snake.levelGenerators;

import snake.*;
import snake.fieldObjects.Apple;
import snake.fieldObjects.Empty;
import snake.fieldObjects.Wall;
import snake.fieldObjects.listed.SnakeHead;

public class GridLevelGenerator implements ILevelGenerator {

    private int width, height;
    private IFieldObject[][] field;

    public Level generateLevel(
            int width, int height,
            int applesOnFieldAmount,
            int applesToGenerateAmount) {
        this.width = width;
        this.height = height;
        field = generateField(width, height);

        Snake snake = addSnake(1, 1);
        field[3][3] = new Apple();

        return new Level(field, applesToGenerateAmount, snake);
    }

    private Snake addSnake(int x, int y) {
        field[y][x] = new SnakeHead(x, y, Direction.ZERO, null, null);
        return new Snake(1,1, Direction.ZERO);
    }

    // 1. Построить граф со степнью каждой вершины >= 2
    // 2. Соединить компоненты связности
    // 3. Для каждого цикла проверить, что есть две вершины с выходом
    // кроме цикла из всех элементов

    private IFieldObject[][] generateField(int width, int height) {
        IFieldObject[][] field = new IFieldObject[height][width];

        field = addGridWalls(field);

        return field;
    }

    private IFieldObject[][] addGridWalls(IFieldObject[][] field) {
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                if (x % 2 == 0 && y % 2 == 0){
                    field[y][x] = new Wall();
                } else {
                    field[y][x] = new Empty();
                }
            }
        }
        return field;
    }
}
