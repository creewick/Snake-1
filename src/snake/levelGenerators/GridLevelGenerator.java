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
        field = generateField(width, height);

        Snake snake = addSnake(1, 1);
        field[3][3] = new Apple();

        return new Level(field, applesToGenerateAmount, snake);
    }

    private Snake addSnake(int x, int y) {
        field[y][x] = new SnakeHead(x, y, Direction.ZERO, null, null);
        return new Snake(x, y, Direction.ZERO);
    }

    private IFieldObject[][] generateField(int width, int height) {
        IFieldObject[][] emptyField = new IFieldObject[height][width];
        IFieldObject[][] fieldForEmptyGraph = fieldForEmptyGraph(emptyField);
        IFieldObject[][] fieldWithTwoWays = fieldWithTwoWays(fieldForEmptyGraph);
        return fieldWithTwoWays;
    }

    private Vector[] getTwoVectors(int x, int y) {
        Vector[] result = new Vector[2];
        Random random = new Random();
        List<Vector> variants = new ArrayList<>();
        if (x > 1)
            variants.add(Direction.LEFT);
        if (x < width - 2)
            variants.add(Direction.RIGHT);
        if (y > 1)
            variants.add(Direction.TOP);
        if (y < height - 2)
            variants.add(Direction.BOTTOM);
        result[0] = (variants.get(random.nextInt(variants.size())));
        variants.remove(result[0]);
        result[1] = (variants.get(random.nextInt(variants.size())));
        return result;
    }

    private IFieldObject[][] fieldWithTwoWays(IFieldObject[][] field) {
        for (int y = 1; y < height; y+=2){
            for (int x = 1; x < width; x+=2){
                for (Vector vector : getTwoVectors(x, y)) {
                    field[y+vector.y][x+vector.x] = new Empty();
                }
            }
        }
        return field;
    }

    private IFieldObject[][] fieldForEmptyGraph(IFieldObject[][] field) {
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                if (x % 2 == 0 || y % 2 == 0){
                    field[y][x] = new Wall();
                } else {
                    field[y][x] = new Empty();
                }
            }
        }
        return field;
    }
}
