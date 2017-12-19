package snakeGUI;

import snake.Game;
import snake.TurnException;
import snake.Vector;
import snake.levelGenerators.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class MainSnakeWindow extends JFrame
{
    private Game game;
    private Size size;
    private int cellSize;
    private Timer timer;
    Vector playerDirection;

    private FieldPanel fieldPanel;
    private ScorePanel scorePanel;

    MainSnakeWindow() {
        super("Snake");
    }

    public Game getGame() {
        return game;
    }

    public void loadGame(Game game) {
        this.game = game;

        setWindowSizeConstants(game);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setSize(size.getWidth(), size.getHeight());

        scorePanel = new ScorePanel(
                "Уровень 1", 0, size.getWidth(), 30);
        fieldPanel = new FieldPanel(game.getCurrentLevel(), cellSize);

        mainPanel.add(scorePanel, BorderLayout.NORTH);
        mainPanel.add(fieldPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        setStartSettings(game);
    }

    private void setStartSettings(Game game) {
        addKeyListener(new KeysListener(this));

        timer = new Timer(Settings.FREQUENCY, timerTick(game));
        timer.start();

        setSize(size.getWidth(), size.getHeight());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setResizable(false);
        setVisible(true);
    }

    private ActionListener timerTick(Game game) {
        return e -> {
            if (playerDirection == null)
                return;

            game.setPlayerDirection(playerDirection);
            try {
                game.makeTurn();
            } catch (TurnException exception) {
                exception.printStackTrace();
            }

            if (game.isGameOver) {
                if (game.isWin)
                    fieldPanel.updateLabels();
                endGame(game.isWin);
                return;
            }

            fieldPanel.updateLabels();

        };
    }

    private void setWindowSizeConstants(Game game) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.width * 0.9);
        int maxHeight = (int) (screenSize.height * 0.9);

        Vector levelSize = game.getCurrentLevel().getLevelSize();
        cellSize = calculateCellSize(maxWidth, maxHeight, levelSize.x, levelSize.y);

        size = new Size(cellSize * levelSize.x + 17, cellSize * levelSize.y + 70
        );
    }

    private int calculateCellSize(
            int width, int height, int fieldWidth, int fieldHeight) {
        return Math.min(width / fieldWidth, height / fieldHeight)                                                       ;
    }

    private void endGame(boolean isWin) {
        timer.stop();
        String isWinMessage = isWin
                ? "Поздравляем! Вы победили!"
                : "Упс! Кажется, вы проиграли";
        JOptionPane.showMessageDialog(this,
                isWinMessage,
                "End of the game", JOptionPane.INFORMATION_MESSAGE);
    }
}
