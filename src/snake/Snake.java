package snake;

import snake.fieldObjects.listed.SnakeHead;
import snake.fieldObjects.listed.SnakePart;

public class Snake {
    private SnakePart tail;
    private SnakeHead head;

    public Snake(int x, int y, Vector direction) {
        head = new SnakeHead(x, y, direction, null, null);
        tail = head;
    }

    public Snake(SnakePart snakePart) {
        this(snakePart.getX(), snakePart.getY(), snakePart.getDirection());
    }

    public SnakePart addPartAndReturnTail(Vector levelSize) {
        int x = (tail.getX() + tail.getDirection().x * (-1)) % levelSize.x;
        int y = (tail.getY() + tail.getDirection().y * (-1)) % levelSize.y;
        SnakePart newPart = new SnakePart(x, y, tail.getDirection(), tail, null);
        tail.setChild(newPart);
        tail = newPart;
        return newPart;
    }

    public void addPart(SnakePart part) {
        part.setDirection(tail.getDirection());
        part.setParent(tail);
        tail.setChild(part);
        tail = part;
    }

    public void removeTail() {
        SnakePart futureTail = tail.getParent();
        futureTail.setChild(null);
        tail = futureTail;
    }

    public SnakeHead getHead() {
        return head;
    }

    public SnakePart getTail() {
        return tail;
    }
}
