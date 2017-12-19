package snakeGUI;

import snake.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Program {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
            IOException, IllegalAccessException {
        Game game = GameReader.readLevel(7);
        new MainSnakeWindow().loadGame(game);
    }
}
