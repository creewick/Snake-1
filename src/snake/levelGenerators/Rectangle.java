package snake.levelGenerators;

import snake.Vector;

public class Rectangle {
    private Vector position;
    private Size size;

    private int top;
    private int bottom;
    private int left;
    private int right;

    public Rectangle(Vector position, Size size) {
        setPosition(position);
        this.size = size;
        top = position.y;
        bottom = position.y + size.getHeight();
        left = position.x;
        right = position.x + size.getWidth();
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public int getWidth() {
        return size.getWidth();
    }

    public int getHeight() {
        return size.getHeight();
    }

    public Size getSize() {
        return size;
    }

    public boolean isIntersectWith(Rectangle rectangle) {
        return (left < rectangle.left && right > rectangle.left ||
                left < rectangle.right && right > rectangle.right)
                && (bottom > rectangle.bottom && top < rectangle.bottom ||
                bottom > rectangle.top && top < rectangle.top);
    }

    public boolean isHaveCommonSides(Rectangle rectangle) {
        return left == rectangle.right || right == rectangle.right ||
                right == rectangle.left || left == rectangle.left ||
                top == rectangle.top || bottom == rectangle.top ||
                top == rectangle.bottom || bottom == rectangle.bottom;
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
