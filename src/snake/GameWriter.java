package snake;

import snake.fieldObjects.Apple;
import snake.fieldObjects.Empty;
import snake.fieldObjects.Wall;
import snake.fieldObjects.listed.SnakeHead;
import snake.fieldObjects.listed.SnakePart;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public final class GameWriter {
    private static final HashMap<Class, Character> FIELD_OBJECT_TO_CHARACTER;

    static {
        FIELD_OBJECT_TO_CHARACTER = new HashMap<>();
        FIELD_OBJECT_TO_CHARACTER.put(Wall.class, '#');
        FIELD_OBJECT_TO_CHARACTER.put(Empty.class, '.');
        FIELD_OBJECT_TO_CHARACTER.put(Apple.class, 'A');
        FIELD_OBJECT_TO_CHARACTER.put(SnakePart.class, 'S');
        FIELD_OBJECT_TO_CHARACTER.put(SnakeHead.class, 'H');
    }

    public static void writeSave(Game game, int number) throws IOException {
        String filename = String.format("%d.save", number);
        Level level = game.getCurrentLevel();
        int applesCount = level.appleGenerator.getApplesCount();
        ArrayList<String> lines = constructLinesFromLevel(level);
        lines.add(Integer.toString(applesCount));
        Path path = Paths.get(Settings.LEVEL_URL + filename);
        Files.write(path, lines);
    }

    private static ArrayList<String> constructLinesFromLevel(Level level) {
        ArrayList<String> lines = new ArrayList<>();
        for (int y = 0; y < level.getLevelSize().y; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < level.getLevelSize().x; x++) {
                Class objectClass = level.getFieldObject(x, y).getClass();
                char character = FIELD_OBJECT_TO_CHARACTER.get(objectClass);
                line.append(character);
            }
            lines.add(line.toString());
        }
        return lines;
    }
}
