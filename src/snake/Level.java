package snake;

import snake.fieldObjects.Empty;
import snake.fieldObjects.listed.SnakePart;

public class Level {
    private IFieldObject[][] field;
    private Snake snake;
    public AppleGenerator appleGenerator;

    public Level(
            int width,
            int height,
            int applesCount,
            Vector snakePosition,
            Vector snakeDirection) {
        field = new IFieldObject[height][width];
        appleGenerator = new AppleGenerator(applesCount);
        snake = new Snake(snakePosition.x, snakePosition.y, snakeDirection);
        field[snakePosition.y][snakePosition.x] = snake.getHead();
    }

    public Level(FieldReader reader, int applesCountToGenerate) {
        field = reader.getField();
        snake = reader.getSnake();
        appleGenerator = new AppleGenerator(applesCountToGenerate);
    }

    public Level(IFieldObject[][] field, int applesCount, Snake snake) {
        this.field = field;
        appleGenerator = new AppleGenerator(applesCount);
        this.snake = snake;
    }

    public IFieldObject moveSnakeAndReturnOldCell(Vector snakeDirection) {
        SnakePart currentSnakePart = snake.getHead().getChild();
        Vector parentDirection = snake.getHead().getDirection();
        Empty emptyObj = new Empty();

        while (currentSnakePart != null) {
            Vector nextPosition = getCoordinates(
                    currentSnakePart.getPosition(),
                    parentDirection);

            Vector tmp = currentSnakePart.getDirection();
            field[currentSnakePart.getY()][currentSnakePart.getX()] = emptyObj;
            currentSnakePart.setDirection(parentDirection);
            currentSnakePart.setPosition(nextPosition);
            field[nextPosition.y][nextPosition.x] = currentSnakePart;

            parentDirection = tmp;
            currentSnakePart = currentSnakePart.getChild();
        }

        return moveSnakeHeadAndReturnOldCell(snakeDirection);
    }

    private IFieldObject moveSnakeHeadAndReturnOldCell(Vector snakeDirection) {
        Vector direction;

        if (snakeDirection == null ||
                (snake.getHead().getDirection().isOpposite(snakeDirection) &&
                snake.getHead().getDirection().isDeltaDirection() &&
                snakeDirection.isDeltaDirection()))
            direction = snake.getHead().getDirection();
        else
            direction = snakeDirection;

        Vector nextPosition = getCoordinates(
                snake.getHead().getPosition(),
                direction);

        IFieldObject oldCell = field[nextPosition.y][nextPosition.x];
        if (snake.getHead().getChild() == null)
            field[snake.getHead().getY()][snake.getHead().getX()] = new Empty();
        snake.getHead().setPosition(nextPosition);
        snake.getHead().setDirection(direction);
        field[nextPosition.y][nextPosition.x] = snake.getHead();

        return oldCell;
    }

    public void addSnakePart() {
        SnakePart tail = snake.addPartAndReturnTail(getLevelSize());
        field[tail.getY()][tail.getX()] = tail;
    }

    public boolean isOver(){
        return appleGenerator.getApplesCount() == 0;
    }

    private Vector getCoordinates(Vector position, Vector direction) {
        Vector sumVector = position.sum(direction);
        return new Vector(
                (sumVector.x + field[0].length) % field[0].length,
                (sumVector.y + field.length) % field.length
        );
    }

    public IFieldObject getFieldObject(int x, int y) {
        return field[y][x];
    }

    public IFieldObject getFieldObject(Vector position) {
        return getFieldObject(position.x, position.y);
    }

    public void setObjectOnField(Vector coordinates, IFieldObject object) {
        field[coordinates.y][coordinates.x] = object;
    }

    public void setObjectOnField(int x, int y, IFieldObject object) {
        setObjectOnField(new Vector(x, y), object);
    }

    public Vector getLevelSize() {
        return new Vector(field[0].length, field.length);
    }

    public Snake getSnake() {
        return snake;
    }
}
