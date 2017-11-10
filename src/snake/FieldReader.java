package snake;

import snake.fieldObjects.Apple;
import snake.fieldObjects.Empty;
import snake.fieldObjects.Wall;
import snake.fieldObjects.listed.SnakeHead;
import snake.fieldObjects.listed.SnakePart;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class FieldReader {
    private String fileName;
    private static final HashMap<Character, IObjectCreator>
            CHARACTER_TO_FIELD_OBJECT;
    private IFieldObject[][] field;
    private Snake snake;

    static {
        CHARACTER_TO_FIELD_OBJECT = new HashMap<>();
        CHARACTER_TO_FIELD_OBJECT.put('#',
                (x, y, vector, parent, child) -> new Wall());
        CHARACTER_TO_FIELD_OBJECT.put('.',
                (x, y, vector, parent, child) -> new Empty());
        CHARACTER_TO_FIELD_OBJECT.put('A',
                (x, y, vector, parent, child) -> new Apple());
        CHARACTER_TO_FIELD_OBJECT.put('S', SnakePart::new);
        CHARACTER_TO_FIELD_OBJECT.put('H', SnakeHead::new);
    }

    public FieldReader(String fileName) throws IllegalAccessException,
            InstantiationException, NoSuchMethodException,
            InvocationTargetException, IOException {
        this.fileName = fileName;
        fillFieldAndCreateSnake();
        fillSnakePartsDirections();
    }

    private void fillFieldAndCreateSnake() throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        List<String> lines = Files.readAllLines(
                Paths.get(Settings.LEVEL_URL +fileName),
                StandardCharsets.UTF_8);

        try {
            field = new IFieldObject[lines.size()][lines.get(0).length()];
        } catch (IndexOutOfBoundsException e){
            throw new IllegalArgumentException("File is empty. Can't create a new level");
        }
        List<SnakePart> snakeParts = new ArrayList<>();
        SnakeHead head = null;
        for(int i = 0; i < lines.size(); i++){
            for (int j = 0; j < lines.get(i).length(); j++){
                Character symbol = lines.get(i).charAt(j);
                field[i][j] = CHARACTER_TO_FIELD_OBJECT.get(symbol)
                        .createFieldObject(
                                j, i, null,null,null);
                if(symbol == 'S'){
                    snakeParts.add((SnakePart) field[i][j]);
                }
                if(symbol == 'H'){
                    head = (SnakeHead) field[i][j];
                }
            }
        }
        createSnake(head,snakeParts);
    }

    private void createSnake(SnakeHead head, List<SnakePart> snakeParts){
        Snake snake = new Snake(head);
        List<SnakePart> neighbors =
                getNearbySnakeParts(getNeighbours(head), snakeParts);
        constructSnake(neighbors, snake, snakeParts);
        this.snake = snake;
    }

    private List<SnakePart> getNearbySnakeParts(
            List<IFieldObject> neighbours,
            List<SnakePart> snakeParts){
        List<SnakePart> nearbySnakeParts = new ArrayList<>();
        for (IFieldObject neighbour : neighbours) {
            if (neighbour instanceof SnakePart && snakeParts.contains(neighbour)) {
                nearbySnakeParts.add((SnakePart) neighbour);
            }
        }
        return nearbySnakeParts;
    }

    private void constructSnake(
            List<SnakePart> nearbySnakeParts,
            Snake snake,
            List<SnakePart> snakeParts){
        SnakePart next;
        for (SnakePart nearbySnakePart : nearbySnakeParts) {
            next = nearbySnakePart;
            snake.addPart(next);
            snakeParts.remove(next);
            List<SnakePart> tmp = getNearbySnakeParts(
                    getNeighbours(nearbySnakePart),
                    snakeParts);
            constructSnake(tmp, snake, snakeParts);
        }
        if(snakeParts.size()!=0){
            snakeParts.add(snake.getTail());
            snake.removeTail();
        }
    }

    private List<IFieldObject> getNeighbours(SnakePart center){
        List<Vector> offset = Arrays.asList(
                Direction.LEFT,
                Direction.RIGHT,
                Direction.BOTTOM,
                Direction.TOP);

        List<IFieldObject> neighbours = new ArrayList<>();
        for (Vector anOffset : offset) {
            Vector neighbour = center.getPosition().sum(anOffset);
            if (neighbour.x >= 0 && neighbour.y >= 0 &&
                    neighbour.x <= field[0].length &&
                    neighbour.y <= field.length) {
                neighbours.add(field[neighbour.y][neighbour.x]);
            }
        }
        return neighbours;
    }

    private void fillSnakePartsDirections(){
        SnakePart current = snake.getHead();
        SnakePart next = snake.getHead().getChild();
        while (next != null) {
            current.setDirection(
                    current
                        .getPosition()
                        .subtract(next.getPosition()));
            current = next;
            next = current.getChild();

        }
        current.setDirection(Direction.ZERO);
    }

    public IFieldObject[][] getField() {
        return field;
    }

    public Snake getSnake() {
        return snake;
    }
}
