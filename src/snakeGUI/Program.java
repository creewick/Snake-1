package snakeGUI;

import snake.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Program {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
            IOException, IllegalAccessException {
//        levels[0] = new GridLevelGenerator().generateLevel(60, 60, 1, 3);
  //      levels[0] = new CombineLevelGenerator().generateLevel(60, 30, 2, 5);
        Level level = new LevelReader("level7.txt").getLevel();
        Game game = new Game(level);
        new MainSnakeWindow().loadGame(game);
    }
}
