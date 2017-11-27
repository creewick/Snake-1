package snake;

import javafx.util.Pair;
import snake.fieldObjects.Portal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Program {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IOException, IllegalAccessException, TurnException {
        Level[] levels = new Level[1];
        FieldReader fieldReader =
                new FieldReader("level8.txt");
        levels[0] = new Level(fieldReader, 1);
        Pair<Portal, Portal> portals = Portal.generatePortals(
                new Vector(3, 0),
                new Vector(0, 0));
        levels[0].setObjectOnField(portals.getKey().getPosition(), portals.getKey());
        levels[0].setObjectOnField(portals.getValue().getPosition(), portals.getValue());
        Game game = new Game(levels);
        game.setPlayerDirection(Direction.LEFT);
        game.makeTurn();
        game.setPlayerDirection(Direction.BOTTOM);
        game.makeTurn();
        game.makeTurn();
    }
}
