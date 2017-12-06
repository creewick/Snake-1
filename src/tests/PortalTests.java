package tests;

import org.junit.Before;
import org.junit.Test;
import snake.*;
import snake.fieldObjects.Empty;
import snake.fieldObjects.Portal;
import snake.fieldObjects.listed.SnakeHead;

import javax.sound.sampled.Port;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class PortalTests {
    private Level level;
    private Game game;
    @Before
    public void setUp() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IOException, IllegalAccessException {
        Level[] levels = new Level[1];
        level = new Level(new FieldReader("_testMap_oneElementSnake_portal.txt"), 5);
        levels[0] = level;

        game = new Game(levels);
    }

    @Test
    public void generatePortalsOnLevel_portalsShouldBeOnField() {
        Vector firstPortalPosition = new Vector(3, 1);
        Vector secondPortalPosition = new Vector(4, 4);
        Portal.generatePortalsOnLevel(level, firstPortalPosition, secondPortalPosition);

        assertTrue(level.getFieldObject(firstPortalPosition) instanceof Portal);
        assertTrue(level.getFieldObject(secondPortalPosition) instanceof Portal);
    }

    @Test
    public void generatePortalsOnLevel_portalsShouldHaveRelation() {
        Vector firstPortalPosition = new Vector(3, 1);
        Vector secondPortalPosition = new Vector(2, 2);
        Portal.generatePortalsOnLevel(level, firstPortalPosition, secondPortalPosition);

        Portal firstPortal = (Portal)level.getFieldObject(firstPortalPosition);
        Portal secondPortal = (Portal)level.getFieldObject(secondPortalPosition);

        assertTrue(firstPortal.getExit().equals(secondPortal));
        assertTrue(secondPortal.getExit().equals(firstPortal));
    }

    @Test
    public void portal_intersectWithSnake_moveSnakeHeadToAnotherPortal() throws TurnException {
        Vector firstPortalPosition = new Vector(1, 0);
        Vector secondPortalPosition = new Vector(3, 3);
        Portal.generatePortalsOnLevel(
                game.getCurrentLevel(),
                firstPortalPosition,
                secondPortalPosition
        );
        Vector snakePositionBeforeMove = game.getCurrentLevel()
                .getSnake()
                .getHead()
                .getPosition();

        game.setPlayerDirection(Direction.LEFT);
        assertTrue(game.getCurrentLevel().getFieldObject(snakePositionBeforeMove)
                instanceof SnakeHead);
        game.makeTurn();

        assertTrue(game.getCurrentLevel().getFieldObject(snakePositionBeforeMove)
                instanceof Empty);
        assertTrue(game.getCurrentLevel().getFieldObject(secondPortalPosition)
                instanceof  SnakeHead);
        assertEquals(
                game.getCurrentLevel().getSnake().getHead().getPosition(),
                secondPortalPosition
        );
    }

    @Test
    public void portal_moveSnakeWithManyParts() throws TurnException, InvocationTargetException, NoSuchMethodException, InstantiationException, IOException, IllegalAccessException {
        Level[] another_levels = new Level[1];
        another_levels[0] = new Level(new FieldReader("_testMap_manyElementsSnake_portal.txt"), 2);
        Game anotherGame = new Game(another_levels);
        Vector firstPortalPosition = new Vector(1, 0);
        Vector secondPortalPosition = new Vector(4, 3);
        Portal.generatePortalsOnLevel(
                anotherGame.getCurrentLevel(),
                firstPortalPosition,
                secondPortalPosition
        );

        anotherGame.setPlayerDirection(Direction.LEFT);
        anotherGame.makeTurn();
        anotherGame.makeTurn();
        anotherGame.makeTurn();

        assertEquals(
                anotherGame.getCurrentLevel().getFieldObject(2, 3),
                anotherGame.getCurrentLevel().getSnake().getHead()
        );
        assertEquals(
                anotherGame.getCurrentLevel().getFieldObject(3, 3),
                anotherGame.getCurrentLevel().getSnake().getHead().getChild()
        );
        assertEquals(
                anotherGame.getCurrentLevel().getFieldObject(4, 3),
                anotherGame.getCurrentLevel().getSnake().getHead().getChild().getChild()
        );
    }
}
