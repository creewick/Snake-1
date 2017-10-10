package snake;

import org.junit.Test;

import static org.junit.Assert.*;

public class SnakeTest {

    private void fillField(Level level){
        level.objects[0][0] = new Empty();
        level.objects[0][1] = new Empty();
        level.objects[0][2] = new Empty();
        level.objects[1][0] = new Apple();
        level.objects[1][1] = new Wall();
        level.objects[1][2] = new Empty();
        level.objects[2][0] = new Wall();
        level.objects[2][1] = new Empty();
    }

    @Test
    public void snake_addPartAndReturnTail_newSnake() {
        Snake snake = new Snake(2, 0, Direction.RIGHT);
        SnakePart head = snake.head;

        SnakePart tail = snake.addPartAndReturnTail();

        assertEquals(head, snake.head);
        assertEquals(tail, snake.tail);
        assertEquals(1, tail.getX());
        assertEquals(0, tail.getY());
        assertEquals(Direction.RIGHT, tail.direction);
        assertEquals(tail, snake.head.child);
        assertEquals(head, snake.tail.parent);
    }

    @Test
    public void snake_addPartAndReturnTail_notEmptySnake() {
        Snake snake = new Snake(2, 2, Direction.BOTTOM);
        snake.addPartAndReturnTail();
        SnakePart tail = snake.addPartAndReturnTail();

        assertEquals(snake.head.child.child, tail);
        assertEquals(snake.head.child, tail.parent);
        assertEquals(null, tail.child);
    }

    @Test
    public void level_addSnakePart() {
        Level level = new Level(3, 4, 3, 1, 1, Direction.TOP);
        level.addSnakePart();
        level.addSnakePart();

        assertEquals(level.objects[1][2], level.snake.head.child);
        assertEquals(level.objects[1][3], level.snake.tail);
    }

    @Test
    public void someElementsSnakeMoving() {
        Level level = new Level(
                3 ,3,1, 2, 2, Direction.RIGHT);
        fillField(level);
        level.addSnakePart();

        level.moveSnakeAndReturnOldCell(Direction.TOP);
        assertEquals(2, level.snake.head.getX());
        assertEquals(1, level.snake.head.getY());
        assertEquals(2, level.snake.tail.getX());
        assertEquals(2, level.snake.tail.getY());
        assertTrue(level.objects[1][2] instanceof Empty);
    }

    @Test
    public void oneElementSnakeMoving() {
        Level level = new Level(
                3 ,3,1, 2, 2, Direction.LEFT);
        fillField(level);

        level.moveSnakeAndReturnOldCell(null);
        assertEquals(1, level.snake.head.getX());
        assertEquals(2, level.snake.head.getY());
    }
}