package snake.levelGenerators;

import snake.Vector;

public class Rectangle {
    private Vector position;
    private Vector size;

    private int top;
    private int bottom;
    private int left;
    private int right;

    public Rectangle(Vector position, Vector size) {
        setPosition(position);
        this.size = size;
        top = position.y;
        bottom = position.y + size.y;
        left = position.x;
        right = position.x + size.x;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getSize() {
        return size;
    }

    public boolean isIntersectWith(Rectangle rectangle) {
        return (left < rectangle.left && right > rectangle.left ||
                left < rectangle.right && right > rectangle.right)
                && (bottom > rectangle.bottom && top < rectangle.bottom ||
                bottom > rectangle.top && top < rectangle.top);
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
}
