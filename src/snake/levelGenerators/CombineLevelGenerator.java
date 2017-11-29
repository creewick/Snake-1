package snake.levelGenerators;

import snake.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombineLevelGenerator implements ILevelGenerator {
    @Override
    public Level generateLevel(
            int width, int height,
            int applesOnFieldAmount,
            int applesToGenerateAmount) {
        RoomLevelGenerator roomGenerator = new RoomLevelGenerator();
        IFieldObject[][] field = roomGenerator.generateField(width, height);
        HashMap<Rectangle, List<Vector>> exitPoints = roomGenerator.getExitPoints();

        GridLevelGenerator fieldGenerator = new GridLevelGenerator();
        field = generateMazesInRooms(field, exitPoints, fieldGenerator);

        roomGenerator.generateApples(field, applesOnFieldAmount);
        Snake snake = roomGenerator.createSnake(field);
        return new Level(field, applesToGenerateAmount, snake);
    }

    private IFieldObject[][] generateMazesInRooms(
            IFieldObject[][] field, HashMap<Rectangle, List<Vector>> exitPoints,
            GridLevelGenerator fieldGenerator
    ) {
        for(Map.Entry<Rectangle, List<Vector>> exitPointsForOne : exitPoints.entrySet()) {
            IFieldObject[][] maze = fieldGenerator.generateField(
                exitPointsForOne.getKey().getWidth(),
                exitPointsForOne.getKey().getHeight(),
                exitPointsForOne.getValue()
            );

            field = insertMazeIntoRoom(field, maze, exitPointsForOne.getKey().getPosition());
        }
        return field;
    }

    private IFieldObject[][] insertMazeIntoRoom(IFieldObject[][] source, IFieldObject[][] maze, Vector topLeftPoint) {
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[y].length; x++) {
                int absoluteX = topLeftPoint.x + x;
                int absoluteY = topLeftPoint.y + y;

                source[absoluteY][absoluteX] = maze[y][x];
            }
        }

        return source;
    }
}
