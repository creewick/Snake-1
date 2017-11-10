package tests;

import org.junit.Test;
import snake.*;
import snake.fieldObjects.Apple;
import snake.fieldObjects.Empty;
import snake.fieldObjects.Wall;
import snake.fieldObjects.listed.SnakeHead;
import snake.fieldObjects.listed.SnakePart;

import static org.junit.Assert.*;

public class SnakeTest {

    private void fillField(Level level) {
        level.setObjectOnField(0,0, new Empty());
        level.setObjectOnField(0,1, new Empty());
        level.setObjectOnField(0,2, new Empty());
        level.setObjectOnField(1,0, new Apple());
        level.setObjectOnField(1,1, new Wall());
        level.setObjectOnField(1,2, new Empty());
        level.setObjectOnField(2,0, new Wall());
        level.setObjectOnField(2,1, new Empty());
    }

    @Test
    public void snake_addPartAndReturnTail_newSnake() {
        Snake snake = new Snake(2, 0, Direction.RIGHT);
        SnakePart head = snake.getHead();

        SnakePart tail = snake.addPartAndReturnTail(new Vector(10, 10));

        assertEquals(head, snake.getHead());
        assertEquals(tail, snake.getTail());
        assertEquals(1, tail.getX());
        assertEquals(0, tail.getY());
        assertEquals(Direction.RIGHT, tail.getDirection());
        assertEquals(tail, snake.getHead().getChild());
        assertEquals(head, snake.getTail().getParent());
    }

    @Test
    public void snake_addPartAndReturnTail_notEmptySnake() {
        Vector levelSize = new Vector(30, 30);
        Snake snake = new Snake(2, 2, Direction.BOTTOM);
        snake.addPartAndReturnTail(levelSize);
        SnakePart tail = snake.addPartAndReturnTail(levelSize);

        assertEquals(snake.getHead().getChild().getChild(), tail);
        assertEquals(snake.getHead().getChild(), tail.getParent());
        assertEquals(null, tail.getChild());
    }

    @Test
    public void level_addSnakePart() {
        Level level = new Level(
                3, 4, 3, new Vector(1, 1), Direction.TOP);
        level.addSnakePart();
        level.addSnakePart();

        assertEquals(level.getFieldObject(1, 2), level.getSnake().getHead().getChild());
        assertEquals(level.getFieldObject(1, 3), level.getSnake().getTail());
    }
    
    @Test
    public void someElementsSnakeMoving() {
        Level level = new Level(
                3 ,3,1, new Vector(2, 2), Direction.RIGHT);
        fillField(level);
        level.addSnakePart();

        level.moveSnakeAndReturnOldCell(Direction.TOP);
        assertEquals(2, level.getSnake().getHead().getX());
        assertEquals(1, level.getSnake().getHead().getY());
        assertEquals(2, level.getSnake().getTail().getX());
        assertEquals(2, level.getSnake().getTail().getY());
        assertTrue(level.getFieldObject(1, 2) instanceof Empty);
    }

    @Test
    public void oneElementSnakeMoving() {
        Level level = new Level(
                3 ,3,1, new Vector(2, 2), Direction.LEFT);
        fillField(level);

        level.moveSnakeAndReturnOldCell(null);
        assertEquals(1, level.getSnake().getHead().getX());
        assertEquals(2, level.getSnake().getHead().getY());
    }

    @Test
    public void snakeMovingInCircle() {
        Level level = new Level(
                4 ,5,1, new Vector(1, 1), Direction.TOP);

        level.addSnakePart();
        level.getSnake().getTail().setDirection(Direction.LEFT);
        level.addSnakePart();
        level.getSnake().getTail().setDirection(Direction.BOTTOM);
        level.addSnakePart();
        SnakeHead head = level.getSnake().getHead();

        IFieldObject oldCell = level.moveSnakeAndReturnOldCell(Direction.RIGHT);
        assertTrue(oldCell instanceof Empty);
        assertEquals(head, level.getFieldObject(2, 1));
    }

    @Test
    public void goToTheNextLevel() throws Exception {
        Level[] levels = new Level[2];
        levels[0] = new Level(new FieldReader("level5.txt"), 0);
        levels[1] = new Level(new FieldReader("_testMap_snakeWithSomeElements.txt"), 1);
        Game game = new Game(levels);
        assertEquals(levels[0], game.getCurrentLevel());
        game.setPlayerDirection(Direction.RIGHT);
        game.makeTurn();
        System.out.println(game.getCurrentLevel().isOver());
        assertEquals(levels[1], game.getCurrentLevel());
    }

    @Test
    public void isOpposite_ifOppositeNotZeroVectors_True() {
        Vector first = new Vector(1, 1);
        Vector second = new Vector(-1, -1);

        assertTrue(first.isOpposite(second));
    }

    @Test
    public void isOpposite_ifOneOfVectorsIsZero_False() {
        Vector first = new Vector(4, 5);
        Vector second = Direction.ZERO;

        assertFalse(first.isOpposite(second));
        assertFalse(second.isOpposite(first));
    }

    @Test
    public void isOpposite_ifNotOppositeVectors_False() {
        Vector first = new Vector(4, 5);
        Vector second = new Vector(4, 26);

        assertFalse(first.isOpposite(second));
    }
}