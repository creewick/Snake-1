package snake.levelGenerators;

import snake.*;
import snake.Vector;
import snake.fieldObjects.Apple;
import snake.fieldObjects.Empty;
import snake.fieldObjects.Wall;
import snake.fieldObjects.listed.SnakeHead;
import java.util.*;

public class RoomLevelGenerator implements ILevelGenerator {
    private final int minRoomWidth = 5;
    private final int minRoomHeight = 5;
    private final Random random = new Random();

    public Level generateLevel(int width, int height, int applesOnFieldAmount, int applesToGenerateAmount) {
        List<Leaf> leafs = splitLevelOnLeafs(width, height);
        HashMap<Leaf, Rectangle> rooms = generateRectanglesInLeafs(leafs);
        HashMap<Leaf, Leaf> neighbours = makeNeighbourConnections(leafs);
        List<Rectangle> halls = generateHalls(rooms, neighbours);

        List<Rectangle> rectangles = getListOfAllRectangles(rooms, halls);

        IFieldObject[][] field = generateFieldByRectanglesMask(rectangles, width, height);
        generateApples(field, applesOnFieldAmount);
        Snake snake = createSnake(field);
        return new Level(field, applesToGenerateAmount, snake);
    }

    private HashMap<Leaf, Leaf> makeNeighbourConnections(List<Leaf> leafs) {
        HashMap<Leaf, Leaf> result = new HashMap<>();
        for (Leaf leaf : leafs) {
            for (Leaf neigbour : getLeafNeigbours(leaf, leafs)) {
                result.put(leaf, neigbour);
            }
        }

        return result;
    }

    private List<Leaf> getLeafNeigbours(Leaf current, List<Leaf> leafs) {
        List<Leaf> result = new ArrayList<>();

        for (Leaf leaf : leafs) {
            if (leaf.equals(current))
                continue;

            if (leaf.isHaveCommonSides(current))
                result.add(leaf);
        }

        return result;
    }

    private List<Rectangle> getListOfAllRectangles(HashMap<Leaf, Rectangle> rooms, List<Rectangle> halls) {
        List<Rectangle> result = halls;

        for (Map.Entry<Leaf, Rectangle> pair : rooms.entrySet())
            result.add(pair.getValue());

        return result;
    }

    private List<Rectangle> generateHalls(HashMap<Leaf, Rectangle> rectangles, HashMap<Leaf, Leaf> neighbours) {
        List<Rectangle> result = new ArrayList<>();

        for (Map.Entry<Leaf, Rectangle> pair : rectangles.entrySet()) {
            if (neighbours.containsKey(pair.getKey())) {
                Leaf a = neighbours.get(pair.getKey());
                if (rectangles.containsKey(a)) {
                    result.addAll(getHall(
                            pair.getValue(),
                            rectangles.get(a)));
                }
            }
        }

        return result;
    }

    private List<Rectangle> getHall(Rectangle first, Rectangle second) {
        List<Rectangle> result = new ArrayList<>();

        List<Vector> pointsToConnect = getConnectPointList(first, second);

        for (int i = 0; i < pointsToConnect.size() - 1; i++) {
            if (pointsToConnect.get(i).equals(pointsToConnect.get(i + 1)))
                continue;

            result.add(generateHallPart(pointsToConnect, i));
        }

        return result;
    }

    private Rectangle generateHallPart(List<Vector> pointsToConnect, int i) {
        int minX = Math.min(pointsToConnect.get(i).x, pointsToConnect.get(i + 1).x);
        int maxX = Math.max(pointsToConnect.get(i).x, pointsToConnect.get(i + 1).x);
        int minY = Math.min(pointsToConnect.get(i).y, pointsToConnect.get(i + 1).y);
        int maxY = Math.max(pointsToConnect.get(i).y, pointsToConnect.get(i + 1).y);

        return new Rectangle(
                new Vector(minX, minY),
                new Size(Math.max(maxX - minX, 1), Math.max(maxY - minY, 1))
        );
    }

    private List<Vector> getConnectPointList(Rectangle first, Rectangle second) {
        Random random = new Random();
        List<Vector> pointsToConnect = new ArrayList<>();

        pointsToConnect.add(new Vector(
                first.getLeft() + random.nextInt(first.getSize().getWidth() - 1),
                first.getTop() + random.nextInt(first.getSize().getHeight() - 1)
        ));

        Vector pointFromSecondRectangle = new Vector(
                second.getLeft() + random.nextInt(second.getSize().getWidth()),
                second.getTop() + random.nextInt(second.getSize().getHeight())
        );
        pointsToConnect.add(new Vector(
                pointsToConnect.get(0).x,
                pointFromSecondRectangle.y
        ));
        pointsToConnect.add(pointFromSecondRectangle);

        return pointsToConnect;
    }

    private HashMap<Leaf, Rectangle> generateRectanglesInLeafs(List<Leaf> leafs) {
        Random random = new Random();
        HashMap<Leaf, Rectangle> result = new HashMap<>();
        for (Leaf leaf  : leafs) {
            int roomWidth = minRoomWidth + random.nextInt(leaf.getWidth() - minRoomWidth);
            int roomHeight = minRoomHeight + random.nextInt(leaf.getHeight() - minRoomHeight);
            int roomX = random.nextInt(leaf.getWidth() - roomWidth);
            int roomY = random.nextInt(leaf.getHeight() - roomHeight);
            roomX += leaf.getX();
            roomY += leaf.getY();
            result.put(leaf, new Rectangle(
                    new Vector(roomX, roomY),
                    new Size(roomWidth, roomHeight)
            ));
        }

        return result;
    }

    private List<Leaf> splitLevelOnLeafs(int width, int height) {
        List<Leaf> result = new ArrayList<>();
        Queue<Leaf> leafQueue = new ArrayDeque<Leaf>();
        leafQueue.add(new Leaf(0, 0, width, height));

        while (leafQueue.size() > 0) {
            Leaf current = leafQueue.poll();
            if (current.trySplit()) {
                leafQueue.add(current.getFirstChild());
                leafQueue.add(current.getSecondChild());
            }
            else
                result.add(current);
        }

        return result;
    }

    private void generateApples(IFieldObject[][] field, int applesOnFieldAmount) {
        List<Vector> freeCells = getFreeCells(field);
        List<Vector> busyCells = new ArrayList<>();

        while (busyCells.size() < applesOnFieldAmount) {
            Vector appleCell = freeCells.get(random.nextInt(freeCells.size()));
            if (busyCells.contains(appleCell))
                continue;

            field[appleCell.y][appleCell.x] = new Apple();
            busyCells.add(appleCell);
        }
    }

    private Snake createSnake(IFieldObject[][] field) {
        List<Vector> freeCells = getFreeCells(field);
        Vector snakeCell = freeCells.get(random.nextInt(freeCells.size()));
        SnakeHead snakeHead = new SnakeHead(snakeCell.x, snakeCell.y, Direction.ZERO, null, null);
        field[snakeCell.y][snakeCell.x] = snakeHead;
        return new Snake(snakeHead);
    }

    private List<Vector> getFreeCells(IFieldObject[][] field) {
        List<Vector> result = new ArrayList<>();

        for (int y = 0; y < field.length; y++)
            for (int x = 0; x < field[0].length; x++)
                if (field[y][x] instanceof Empty)
                    result.add(new Vector(x, y));

        return result;
    }

    private IFieldObject[][] generateFieldByRectanglesMask(
            List<Rectangle> rectangles, int width, int height) {

        IFieldObject[][] field = new IFieldObject[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < height; x++)
                field[y][x] = new Wall();

        for (Rectangle rectangle : rectangles) {
            for (int y = rectangle.getTop(); y < rectangle.getBottom(); y++)
                for (int x = rectangle.getLeft(); x < rectangle.getRight(); x++)
                    field[y][x] = new Empty();
        }

        return field;
    }
}

