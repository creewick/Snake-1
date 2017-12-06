package tests;

import org.junit.Test;
import snake.IFieldObject;
import snake.Vector;
import snake.fieldObjects.Empty;
import snake.fieldObjects.Wall;
import snake.levelGenerators.GridLevelField;
import snake.levelGenerators.GridLevelGenerator;
import static org.junit.Assert.*;

public class GridGeneratorTest {

    @Test
    public void EveryCellContainAtLeaseTwoExits_EvenSize(){
        EveryCell_ContainTwoOrMoreExits(20, 20);
    }

    @Test
    public void EveryCellContainAtLeaseTwoExits_OddSize(){
        EveryCell_ContainTwoOrMoreExits(21, 21);
    }

    private void EveryCell_ContainTwoOrMoreExits(int width, int height){
        GridLevelGenerator generator = new GridLevelGenerator();
        IFieldObject[][] field = new GridLevelField(width, height)
                .addGrid()
                .addPasses()
                .toArray();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (field[y][x] instanceof Wall)
                    continue;
                int emptyNeiboursCount = 0;
                if (x > 0 && NotWall(field[y][x-1]))
                    emptyNeiboursCount++;
                if (x < width - 1 && NotWall(field[y][x+1]))
                    emptyNeiboursCount++;
                if (y > 0 && NotWall(field[y-1][x]))
                    emptyNeiboursCount++;
                if (y < height - 1 && NotWall(field[y+1][x]))
                    emptyNeiboursCount++;
                assertTrue(printfield(field, y, x, emptyNeiboursCount),
                        emptyNeiboursCount > 1);
            }
        }
    }

    private String printfield(IFieldObject[][] field, int y, int x, int r) {
        String result = y + "," + x + "(" + r + ")\n";
        for (int x1 = 0; x1 < 20; x1++){
            String row = "";
            for (int y1 = 0; y1 < 20; y1++){
                if (x1 == x && y1 == y)
                    row = row + "*";
                else if (NotWall(field[y1][x1]))
                    row = row + "0";
                else
                    row = row + "1";
            }
            result += row + "\n";
        }
        return result;
    }

    private boolean NotWall(IFieldObject object){
        return !(object instanceof Wall);
    }
}
