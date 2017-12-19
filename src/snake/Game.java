package snake;


public class Game {
    private Level level;
    private Vector playerDirection;
    public boolean isGameOver;
    public boolean isWin;

    public void makeTurn() throws TurnException {
        if(isGameOver) {
            throw new TurnException();
        }

        IFieldObject oldCell = getCurrentLevel()
                .moveSnakeAndReturnOldCell(playerDirection);
        oldCell.intersectWithSnake(this);

        if(isGameOver) {
            return;
        }

        if (!getCurrentLevel().appleGenerator.isNeedToAdd(oldCell)) {
            return;
        }

        if (getCurrentLevel().isOver()) {
            isWin = true;
            isGameOver = true;
            return;
        }
        getCurrentLevel().appleGenerator.generate(getCurrentLevel());
    }

    public Game(Level level) {
        this.level = level;
        isGameOver = false;
        isWin = false;
    }

    public void setPlayerDirection(Vector playerDirection) {
        this.playerDirection = playerDirection;
    }

    public Level getCurrentLevel() {
        return level;
    }
}
