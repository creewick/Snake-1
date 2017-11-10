package tests;

import org.junit.Test;
import snake.Direction;
import snake.FieldReader;
import snake.fieldObjects.listed.SnakePart;
import snake.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FieldReaderTest {
    @Test
    public void readLevelFromFile_ifSnakeWithSomeSnakeParts_snakePartsCoordinatesAreSet() throws Exception {
        FieldReader reader = new FieldReader(
                "_testMap_snakeWithSomeElements.txt");
        Vector[] expected = new Vector[5];
        expected[0] = new Vector(21, 4);
        expected[1] = new Vector(21, 5);
        expected[2] = new Vector(22, 5);
        expected[3] = new Vector(22, 4);
        expected[4] = new Vector(22, 3);
        SnakePart current = reader.getSnake().getHead();

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].x, current.getX());
            assertEquals(expected[i].y, current.getY());
            current = current.getChild();
        }
        assertNull(current);
    }
    @Test
    public void readLevelFromFile_ifSnakeWithSomeParts_directionsOfSnakePartsAreSet() throws Exception {
        FieldReader reader = new FieldReader(
                "_testMap_snakeWithSomeElements.txt");

        SnakePart current = reader.getSnake().getHead();
        Vector[] expected = new Vector[5];
        expected[0] = Direction.TOP;
        expected[1] = Direction.LEFT;
        expected[2] = Direction.BOTTOM;
        expected[3] = Direction.BOTTOM;
        expected[4] = Direction.ZERO;

        for (int i = 0; i < expected.length; i++) {
            assertTrue(expected[i].equals(current.getDirection()));
            current = current.getChild();
        }
    }
}
