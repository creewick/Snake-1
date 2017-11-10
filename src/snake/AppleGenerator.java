package snake;

import snake.fieldObjects.Apple;
import snake.fieldObjects.Empty;

import java.util.ArrayList;
import java.util.Random;

public class AppleGenerator {
    private int applesCount;

    public AppleGenerator(int applesCount) {
        this.applesCount = applesCount;
    }

    public boolean isNeedToAdd(IFieldObject oldCell) {
        return oldCell instanceof Apple;
    }

    public int getApplesCount() {
        return applesCount;
    }

    public void generate(Level level) {
        if (applesCount <= 0)
            return;

        ArrayList<Vector> freeCellsIndexes = getAllFreeCells(level);
        Random random = new Random();
        int index = random.nextInt(freeCellsIndexes.size());
        Vector randomFreeCell = freeCellsIndexes.get(index);
        level.setObjectOnField(randomFreeCell, new Apple());
        applesCount--;
    }

    private ArrayList<Vector> getAllFreeCells(Level level) {
        ArrayList<Vector> indexesFreeCells = new ArrayList<>();
        Vector levelSize = level.getLevelSize();
        for (int y = 0; y < levelSize.y; y++) {
            for (int x = 0; x < levelSize.x; x++) {
                if(level.getFieldObject(x, y) instanceof Empty) {
                    indexesFreeCells.add(new Vector(x, y));
                }
            }
        }
        return indexesFreeCells;
    }
}
