package snakeGUI;

import snake.Direction;
import snake.GameReader;
import snake.GameWriter;
import snake.Vector;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

public class KeysListener implements KeyListener {
    private MainSnakeWindow parent;
    private Hashtable<Integer, Vector> keys;

    KeysListener(MainSnakeWindow parent) {
        super();
        this.parent = parent;

        initKeys();
    }

    private void initKeys() {
        keys = new Hashtable<>();
        keys.put(KeyEvent.VK_LEFT, Direction.LEFT);
        keys.put(KeyEvent.VK_DOWN, Direction.BOTTOM);
        keys.put(KeyEvent.VK_RIGHT, Direction.RIGHT);
        keys.put(KeyEvent.VK_UP, Direction.TOP);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keys.containsKey(keyCode))
            parent.playerDirection = keys.get(keyCode);
        if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) {
            int number = keyCode - KeyEvent.VK_0;

            if (e.isShiftDown()) {
                GameWriter.writeSave(parent.getGame(), number);
            } else {
                try {
                    parent.loadGame(GameReader.readSave(number));
                }
                catch (Exception ex)
                {

                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}
