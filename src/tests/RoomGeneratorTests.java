package tests;

import org.junit.Test;
import snake.IFieldObject;
import snake.Vector;
import snake.levelGenerators.Rectangle;
import snake.levelGenerators.RoomLevelGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class RoomGeneratorTests {
    @Test
    public void roomGenerator_EachRoomHaveEntry() {
        RoomLevelGenerator generator = new RoomLevelGenerator();
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            IFieldObject[][] field = generator.generateField(
                    6 + random.nextInt(60),
                    6 + random.nextInt(60));

            HashMap<Rectangle, List<Vector>> entries = generator.getExitPoints();

            for (Map.Entry<Rectangle, List<Vector>> pair : entries.entrySet())
                assertTrue(pair.getValue().size() >= 2);
        }

    }
}
