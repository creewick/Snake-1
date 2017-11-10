package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import snake.Vector;
import snake.levelGenerators.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RectangleTest {
    @Test
    public void isIntersectWith_IfInsideRectangle_True() {
        Rectangle rectangle = new Rectangle(
                new Vector(0, 0),
                new Vector(200, 200)
        );
        Rectangle rectangleInside = new Rectangle(
                new Vector(5, 5),
                new Vector(10, 10)
        );

        assertTrue(rectangle.isIntersectWith(rectangleInside));
        assertTrue(rectangleInside.isIntersectWith(rectangle));
    }

    @Test
    public void isIntersectWith_IfOneVertexInside_True() {
        Rectangle rectangle = new Rectangle(
                new Vector(0, 0),
                new Vector(200, 200)
        );

        List<Vector> positions = Arrays.asList(
                new Vector(195, 195),
                new Vector(-10, 180),
                new Vector(-10, -10),
                new Vector(195, -10)
        );

        List<Vector> sizes = Arrays.asList(
                new Vector(15, 15),
                new Vector(50, 50),
                new Vector(50, 50),
                new Vector(50, 50)
        );

        assert positions.size() == sizes.size();
        for (int i = 0; i < positions.size(); i++) {
            Rectangle rectangleInside = new Rectangle(
                    positions.get(i), sizes.get(i)
            );
            assertTrue(rectangle.isIntersectWith(rectangleInside));
            assertTrue(rectangleInside.isIntersectWith(rectangle));
        }


    }

    @Test
    public void isIntersectWith_IfNoIntersect_False() {
        Rectangle rectangle = new Rectangle(
                new Vector(0, 0),
                new Vector(200, 200)
        );
        Rectangle rectangleInside = new Rectangle(
                new Vector(201, 201),
                new Vector(10, 10)
        );

        assertFalse(rectangle.isIntersectWith(rectangleInside));
        assertFalse(rectangleInside.isIntersectWith(rectangle));
    }
}
