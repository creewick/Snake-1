package snake.levelGenerators;

import snake.Vector;

import java.util.Random;

public class Leaf extends Rectangle {
    private static final int MIN_LEAF_WIDTH = 6;
    private static final int MIN_LEAF_HEIGHT = 6;
    private static final double MAX_LIMIT_ASPECT = 1.25;

    private Leaf firstChild;
    private Leaf secondChild;

    public Leaf(int x, int y, int width, int height) {
        super(new Vector(x, y), new Size(width, height));
        firstChild = null;
        secondChild = null;
    }

    public boolean trySplit() {
        Random random = new Random();
        boolean isSplitByVertical = random.nextDouble() > 0.5;
        if (getWidth() > MAX_LIMIT_ASPECT * getHeight())
            isSplitByVertical = false;
        if (getHeight() > MAX_LIMIT_ASPECT * getWidth())
            isSplitByVertical = true;

        if (isSplitByVertical && getHeight() < 2 * MIN_LEAF_HEIGHT)
            return false;
        if (!isSplitByVertical && getWidth() < 2 * MIN_LEAF_WIDTH)
            return false;

        if (isSplitByVertical) {
            int delta = getHeight() > 2 * MIN_LEAF_HEIGHT
                    ? random.nextInt(getHeight() - 2 * MIN_LEAF_HEIGHT)
                    : 0;
            int firstChildHeight = MIN_LEAF_HEIGHT + delta;
            firstChild = new Leaf(getX(), getY(), getWidth(), firstChildHeight);
            secondChild = new Leaf(getX(), getY() + firstChildHeight, getWidth(), getHeight() - firstChildHeight);
        }
        else {
            int delta = getWidth() > 2 * MIN_LEAF_HEIGHT
                    ? random.nextInt(getWidth() - 2 * MIN_LEAF_WIDTH)
                    : 0;
            int firstChildWidth = MIN_LEAF_WIDTH + delta;
            firstChild = new Leaf(getX(), getY(), firstChildWidth, getHeight());
            secondChild = new Leaf(getX() + firstChildWidth, getY(), getWidth() - firstChildWidth, getHeight());
        }
        return true;
    }

    public Leaf getFirstChild() {
        return firstChild;
    }

    public Leaf getSecondChild() {
        return secondChild;
    }

    public boolean equals(Leaf other) {
        return this.getX() == other.getX() &&
                this.getY() == other.getY() &&
                other.getWidth() == this.getWidth() &&
                other.getHeight() == this.getHeight();
    }
}
