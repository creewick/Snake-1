package snake.levelGenerators;

import snake.Direction;
import snake.IFieldObject;
import snake.Vector;
import snake.fieldObjects.Empty;
import snake.fieldObjects.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Field {

    private IFieldObject[][] array;
    private int width, height;

    public IFieldObject[][] toArray() {
        return array;
    }

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        array = new IFieldObject[height][width];
    }

    public Field addGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x % 2 == 0 || y % 2 == 0) {
                    array[y][x] = new Wall();
                } else {
                    array[y][x] = new Empty();
                }
            }
        }
        return this.fillBorders();
    }

    private Field fillBorders(){
        if (width % 2 == 0){
            for (int y = 0; y < height; y++) {
                array[y][width-1] = new Wall();
            }
        }
        if (height % 2 == 0){
            for (int x = 0; x < width; x++) {
                array[height-1][x] = new Wall();
            }
        }
        return this;
    }

    public Field addPasses() {
        for (int y = 1; y < height - 1; y+=2){
            for (int x = 1; x < width - 1; x+=2){
                for (Vector vector : getTwoVectors(x, y)) {
                    array[y+vector.y][x+vector.x] = new Empty();
                }
            }
        }
        return this;
    }

    public Field addDoors(Vector[] doors) {
        for (Vector door : doors) {
            array[door.y][door.x] = new Empty();
            Vector direction = Direction.ZERO;
            if (door.x == 0)
                direction = Direction.RIGHT;
            if (door.x == width - 1)
                direction = Direction.LEFT;
            if (door.y == 0)
                direction = Direction.BOTTOM;
            if (door.y == height - 1)
                direction = Direction.TOP;
            Vector nextCell = door.sum(direction);
            array[nextCell.y][nextCell.x] = new Empty();
            if ((door.x == width - 1 && width % 2 == 0) || (door.y == height - 1 && height % 2 == 0)){
                nextCell = nextCell.sum(direction);
                array[nextCell.y][nextCell.x] = new Empty();
            }
        }
        return this;
    }

    private Vector[] getTwoVectors(int x, int y) {
        Vector[] result = new Vector[2];
        Random random = new Random();
        List<Vector> variants = new ArrayList<Vector>();
        if (x > 1)
            variants.add(Direction.LEFT);
        if (x < width - 3)
            variants.add(Direction.RIGHT);
        if (y > 1)
            variants.add(Direction.TOP);
        if (y < height - 3)
            variants.add(Direction.BOTTOM);
        result[0] = (variants.get(random.nextInt(variants.size())));
        variants.remove(result[0]);
        result[1] = (variants.get(random.nextInt(variants.size())));
        return result;
    }
}
