package snakeGUI;

import snake.*;
import snake.fieldObjects.Portal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Program {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IOException, IllegalAccessException {
        Level[] levels = new Level[1];
//        levels[0] = new GridLevelGenerator().generateLevel(60, 60, 1, 3);
//        levels[0] = new CombineLevelGenerator().generateLevel(60, 30, 2, 5);
        levels[0] = new Level(new FieldReader("level8.txt"), 5);
        Portal.generatePortalsOnLevel(levels[0], new Vector(0, 0), new Vector(2, 0));
        Portal.generatePortalsOnLevel(levels[0], new Vector(2, 2), new Vector(4, 1));
        Game game = new Game(levels);
        MainSnakeWindow window = new MainSnakeWindow(game);
    }
}
