package snake;

public class Game {
    private Level level;
    private Vector playerDirection;
    public boolean isGameOver;
    public boolean isWin;

    public void makeTurn() {
        FieldObject oldCell = level.moveSnakeAndReturnOldCell(playerDirection);
        oldCell.intersectWithSnake(this);

        if (level.appleGenerator.isNeedToAdd(oldCell)) {
            if (level.appleGenerator.getApplesCount() == 0) {
                isWin = true;
                return;
            }
            level.appleGenerator.generate(level);
        }
    }

    public Game(Level level) {
        this.level = level;
        isGameOver = false;
        isWin = false;
    }

    public void setPlayerDirection(Vector playerDirection) {
        this.playerDirection = playerDirection;
    }

    public Level getLevel() {
        return level;
    }
}
