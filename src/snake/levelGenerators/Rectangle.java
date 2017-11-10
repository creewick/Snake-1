package snake.levelGenerators;

import snake.Vector;

public class Rectangle {
    private Vector position;
    private Vector size;

    public int Top = position.y;
    public int Bottom = position.y + size.y;
    public int Left = position.x;
    public int Right = position.x + size.x;

    public Rectangle(Vector position, Vector size) {
        setPosition(position);
        this.size = size;
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
        return (Left < rectangle.Left && Right > rectangle.Left ||
                Left < rectangle.Right && Right > rectangle.Right)
                && (Bottom > rectangle.Bottom && Top < rectangle.Bottom ||
                Bottom > rectangle.Top && Top < rectangle.Top);
    }
}
