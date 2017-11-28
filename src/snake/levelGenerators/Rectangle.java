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
        bottom = position.y + size.getHeight() - 1;
        left = position.x;
        right = position.x + size.getWidth() - 1;
    }

    public Rectangle(int x, int y, int width, int height) {
        this(new Vector(x, y), new Size(width, height));
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
        boolean a = Math.max(left, rectangle.left) <= Math.min(right, rectangle.right);
        boolean b = Math.max(top, rectangle.top) <= Math.min(bottom, rectangle.bottom);

        return a && b;
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

    public Vector getPosition() {
        return position;
    }

    public boolean isPointOnEdge(Vector point) {
        return ((this.right == point.x || this.left == point.x) &&
                    (point.y >= this.top && point.y <= this.bottom)) ||
                ((this.top == point.y || this.bottom == point.y) &&
                    (point.x >= this.left && point.x <= this.right));
    }
}
