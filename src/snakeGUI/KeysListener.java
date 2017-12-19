package snakeGUI;

import snake.Direction;
import snake.Vector;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

public class KeysListener implements KeyListener {
    private String fileNameMask;
    private MainSnakeWindow parent;
    private Hashtable<Integer, Vector> vectors;

    KeysListener(MainSnakeWindow parent, String fileNameMask) {
        super();
        this.parent = parent;
        this.fileNameMask = fileNameMask;

        initVectors();
    }

    private void initVectors() {
        vectors = new Hashtable<>();
        vectors.put(KeyEvent.VK_LEFT, Direction.LEFT);
        vectors.put(KeyEvent.VK_DOWN, Direction.BOTTOM);
        vectors.put(KeyEvent.VK_RIGHT, Direction.RIGHT);
        vectors.put(KeyEvent.VK_UP, Direction.TOP);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Integer keyCode = e.getKeyCode();

        if (vectors.containsKey(keyCode))
            parent.playerDirection = vectors.get(keyCode);

        if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) {
            if (e.isShiftDown()) {

            } else {

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}
