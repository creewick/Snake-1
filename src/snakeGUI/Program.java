package snakeGUI;

import javafx.util.Pair;
import snake.*;
import snake.Settings;
import snake.fieldObjects.Portal;
import snake.levelGenerators.GridLevelGenerator;
import snake.levelGenerators.RoomLevelGenerator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Program {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IOException, IllegalAccessException {
        Level[] levels = new Level[1];
        // levels[0] = new GridLevelGenerator().generateLevel(30, 30, 1, 3);
        //levels[0] = new RoomLevelGenerator().generateLevel(30, 30, 2, 5);
        FieldReader fieldReader =
                new FieldReader("level7.txt");
        levels[0] = new Level(fieldReader, 1);
        Pair<Portal, Portal> portals = Portal.generatePortals(
                new Vector(3, 0),
                new Vector(0, 3));
        levels[0].setObjectOnField(portals.getKey().getPosition(), portals.getKey());
        levels[0].setObjectOnField(portals.getValue().getPosition(), portals.getValue());
        Game game = new Game(levels);
        MainSnakeWindow window = new MainSnakeWindow(game);
    }
}
