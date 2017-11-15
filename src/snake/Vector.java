package snake;

public class Vector {
    public final int x;
    public final int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isOpposite(Vector vector) {
        if (this.equals(Direction.ZERO) || vector.equals(Direction.ZERO))
            return false;

        double scalarProduct = vector.x * this.x + vector.y * this.y;
        double cosOfAngle = scalarProduct / getLength(this) / getLength(vector);
        return cosOfAngle - (-1) < 1e-5;
    }

    private double getLength(Vector vector) {
        return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
    }

    public boolean equals(Vector other){
        return x == other.x && y == other.y;
    }

    public Vector subtract(Vector other) {
        return new Vector(x - other.x, y - other.y);
    }

    public Vector sum(Vector other) {
        return new Vector(x + other.x, y + other.y);
    }
}

