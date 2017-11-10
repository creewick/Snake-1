package snake.levelGenerators;

import snake.IFieldObject;
import snake.ILevelGenerator;
import snake.Level;

public class RoomLevelGenerator implements ILevelGenerator {
    private final double fieldFullness = 0.5;
    private final int minRoomWidth = 3;
    private final int minRoomHeight = 3;

    public Level generateLevel(int width, int height, int applesOnFieldAmount, int applesToGenerateAmount) {
        getRandomUnintersectedRectangles(width, height);
        return null;
    }

    private Rectangle[] getRandomUnintersectedRectangles(int width, int height) {

    }
}

