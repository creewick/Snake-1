package snakeGUI;

import javafx.util.Pair;
import snake.*;
import snake.Settings;
import snake.fieldObjects.Portal;
import snake.levelGenerators.CombineLevelGenerator;
import snake.levelGenerators.GridLevelGenerator;
import snake.levelGenerators.RoomLevelGenerator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Program {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IOException, IllegalAccessException {
        Level[] levels = new Level[1];
//        levels[0] = new RoomLevelGenerator().generateLevel(60, 60, 1, 3);
        levels[0] = new CombineLevelGenerator().generateLevel(60, 30, 2, 5);
        Game game = new Game(levels);
        MainSnakeWindow window = new MainSnakeWindow(game);
    }
}
