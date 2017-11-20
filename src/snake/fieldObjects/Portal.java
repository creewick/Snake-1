package snake.fieldObjects;

import javafx.util.Pair;
import snake.Game;
import snake.IFieldObject;
import snake.ImageFileName;
import snake.Vector;
import snake.fieldObjects.listed.SnakeHead;

import java.time.temporal.Temporal;

@ImageFileName(fileNames = "13.jpg")
public class Portal implements IFieldObject {
    private Portal exit;
    private Vector position;

    public Portal(Vector position) {
        setPosition(position);
    }

    public static Pair<Portal, Portal> generatePortals(Vector first, Vector second) {
        Portal firstPort = new Portal(first);
        Portal secondPort = new Portal(second);
        firstPort.setExit(secondPort);
        secondPort.setExit(firstPort);

        return new Pair<>(firstPort, secondPort);
    }

    @Override
    public void intersectWithSnake(Game game) {
        SnakeHead snakeHead = game.getCurrentLevel().getSnake().getHead();
        Vector snakeHeadDirection = snakeHead.getChild().getDirection();
        Vector positionShouldBe = exit.position.sum(snakeHeadDirection).subtract(snakeHead.getPosition());
        Vector levelSize = game.getCurrentLevel().getLevelSize();
        positionShouldBe = new Vector(
                (positionShouldBe.x + levelSize.x) % levelSize.x,
                (positionShouldBe.y + levelSize.y) % levelSize.y
        );
        game.getCurrentLevel().setObjectOnField(snakeHead.getPosition(), new Empty());
        snakeHead.setDirection(positionShouldBe);
        game.getCurrentLevel().setObjectOnField(positionShouldBe, snakeHead);
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
