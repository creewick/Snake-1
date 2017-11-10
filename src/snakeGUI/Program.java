package snakeGUI;

import snake.*;
import snake.levelGenerators.GridLevelGenerator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Program {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IOException, IllegalAccessException {
        Level[] levels = new Level[1];
        //levels[0] = new Level(new FieldReader("level6.txt"), 20);
        levels[0] = new GridLevelGenerator().generateLevel(21, 21, 10, 10);
        Game game = new Game(levels);
        MainSnakeWindow window = new MainSnakeWindow(game);
    }
}
