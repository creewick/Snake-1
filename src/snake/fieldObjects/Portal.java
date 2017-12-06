package snake.fieldObjects;

import javafx.util.Pair;
import snake.*;
import snake.fieldObjects.listed.SnakeHead;

import java.time.temporal.Temporal;

@ImageFileName(fileNames = "13.jpg")
public class Portal implements IFieldObject {
    private Portal exit;
    private Vector position;

    public Portal(Vector position) {
        setPosition(position);
    }

    public static void generatePortalsOnLevel(Level level, Vector first, Vector second) {
        Portal firstPort = new Portal(first);
        Portal secondPort = new Portal(second);
        firstPort.setExit(secondPort);
        secondPort.setExit(firstPort);

        level.setObjectOnField(first, firstPort);
        level.setObjectOnField(second, secondPort);
    }

    @Override
    public void intersectWithSnake(Game game) {
        SnakeHead snakeHead = game.getCurrentLevel().getSnake().getHead();
        Vector directionByPortal;
        if (snakeHead.getChild() != null)
            directionByPortal = exit.position.subtract(snakeHead.getChild().getPosition());
        else
            directionByPortal = Direction.ZERO;
        game.getCurrentLevel().setObjectOnField(snakeHead.getPosition(), new Empty());
        snakeHead.setDirection(directionByPortal);
        snakeHead.setPosition(exit.position);
        game.getCurrentLevel().setObjectOnField(exit.position, snakeHead);
    }

    public Portal getExit() {
        return exit;
    }

    public void setExit(Portal exit) {
        this.exit = exit;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }
}
