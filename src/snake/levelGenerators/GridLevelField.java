package snake.levelGenerators;

import snake.Direction;
import snake.IFieldObject;
import snake.Vector;
import snake.fieldObjects.Empty;
import snake.fieldObjects.Wall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridLevelField {
    private IFieldObject[][] array;
    private int width, height;

    public IFieldObject[][] toArray() {
        return array;
    }

    public GridLevelField(int width, int height) {
        this.width = width;
        this.height = height;
        array = new IFieldObject[height][width];
    }

    public GridLevelField addGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x % 2 == 1 || y % 2 == 1) {
                    array[y][x] = new Wall();
                } else {
                    array[y][x] = new Empty();
                }
            }
        }
        return this;
    }

    public GridLevelField addPasses() {
        for (int x = 0; x < width; x++) {
            array[0][x] = new Empty();
            array[height - 1][x] = new Empty();
        }
        for (int y = 0; y < height; y++) {
            array[y][0] = new Empty();
            array[y][width - 1] = new Empty();
        }
        for (int y = 1; y < height - 1; y+=2){
            for (int x = 1; x < width - 1; x+=2){
                for (Vector vector : getTwoVectors(x, y)) {
                    array[y+vector.y][x+vector.x] = new Empty();
                }
            }
        }
        return this;
    }

    public GridLevelField addDoors(List<Vector> doors) {
//        for (Vector door : doors) {
//            array[door.y][door.x] = new Empty();
//            Vector direction = Direction.ZERO;
//            if (door.x == 0)
//                direction = Direction.RIGHT;
//            if (door.x == width - 1)
//                direction = Direction.LEFT;
//            if (door.y == 0)
//                direction = Direction.BOTTOM;
//            if (door.y == height - 1)
//                direction = Direction.TOP;
//            Vector nextCell = door.sum(direction);
//            array[nextCell.y][nextCell.x] = new Empty();
//            if ((door.x == width - 1 && width % 2 == 0) || (door.y == height - 1 && height % 2 == 0)){
//                nextCell = nextCell.sum(direction);
//                array[nextCell.y][nextCell.x] = new Empty();
//            }
//        }
        return this;
    }

    private Vector[] getTwoVectors(int x, int y) {
        Vector[] result = new Vector[2];
        Random random = new Random();
        List<Vector> variants = new ArrayList<Vector>();
        if (x > 0)
            variants.add(Direction.LEFT);
        if (x < width - 1)
            variants.add(Direction.RIGHT);
        if (y > 0)
            variants.add(Direction.TOP);
        if (y < height - 1)
            variants.add(Direction.BOTTOM);
        result[0] = (variants.get(random.nextInt(variants.size())));
        variants.remove(result[0]);
        result[1] = (variants.get(random.nextInt(variants.size())));
        return result;
    }
}
