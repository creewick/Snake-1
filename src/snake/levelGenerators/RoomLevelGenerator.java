package snake.levelGenerators;

import snake.*;
import snake.Vector;
import snake.fieldObjects.Apple;
import snake.fieldObjects.Empty;
import snake.fieldObjects.Wall;
import snake.fieldObjects.listed.SnakeHead;
import java.util.*;

public class RoomLevelGenerator implements ILevelGenerator {
    private static final int minRoomWidth = 5;
    private static final int minRoomHeight = 5;
    private final Random random = new Random();
    private HashMap<Rectangle, List<Vector>> exitPoints = new HashMap<>();

    public Level generateLevel(
            int width, int height,
            int applesOnFieldAmount,
            int applesToGenerateAmount) {
        IFieldObject[][] field = generateField(width, height);
        generateApples(field, applesOnFieldAmount);
        Snake snake = createSnake(field);
        return new Level(field, applesToGenerateAmount, snake);
    }

    public IFieldObject[][] generateField(int width, int height) {
        exitPoints = new HashMap<>();
        List<Leaf> leafs = splitLevelOnLeafs(width, height);
        HashMap<Leaf, Leaf> neighbours = makeNeighbourConnections(leafs);
        HashMap<Leaf, Rectangle> rooms = generateRectanglesInLeafs(leafs);
        List<Rectangle> halls = generateHallsAndFillExitPoints(rooms, neighbours);

        List<Rectangle> rectangles = getListOfAllRectangles(rooms, halls);

        return generateFieldByRectanglesMask(rectangles, width, height);
    }

    public HashMap<Rectangle, List<Vector>> getExitPoints() {
        return exitPoints;
    }

    private HashMap<Leaf, Leaf> makeNeighbourConnections(List<Leaf> leafs) {
        HashMap<Leaf, Leaf> result = new HashMap<>();
        for (Leaf leaf : leafs) {
            for (Leaf neighbour : getLeafNeighbours(leaf, leafs)) {
                result.put(leaf, neighbour);
            }
        }

        return result;
    }

    private List<Leaf> getLeafNeighbours(Leaf current, List<Leaf> leafs) {
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
        List<Rectangle> result = new ArrayList<>(halls);

        for (Map.Entry<Leaf, Rectangle> pair : rooms.entrySet())
            result.add(pair.getValue());

        return result;
    }

    private List<Rectangle> generateHallsAndFillExitPoints(
            HashMap<Leaf, Rectangle> rectangles,
            HashMap<Leaf, Leaf> linkedLeafs) {

        List<Rectangle> result = new ArrayList<>();

        for (Map.Entry<Leaf, Rectangle> pair : rectangles.entrySet()) {
            Leaf parentLeaf = linkedLeafs.get(pair.getKey());
            if (!rectangles.containsKey(parentLeaf))
                continue;

            for (int i = 0; i < 2; i++) {
                List<Rectangle> hall = getHall(pair.getValue(), rectangles.get(parentLeaf));
                for (Rectangle hallPart : hall) {
                    if (hallPart.isIntersectWith(pair.getValue()))
                        addExitPoints(hallPart, pair.getValue());
                    if (hallPart.isIntersectWith(rectangles.get(parentLeaf)))
                        addExitPoints(hallPart, rectangles.get(parentLeaf));
                }

                result.addAll(hall);
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

            Rectangle hallPart = generateHallPart(
                    pointsToConnect.get(i),
                    pointsToConnect.get(i + 1)
            );

            result.add(hallPart);
        }

        return result;
    }

    private void addExitPoints(Rectangle hallPart, Rectangle rectangle) {
        for (int y = hallPart.getTop(); y <= hallPart.getBottom(); y++) {
            for (int x = hallPart.getLeft(); x <= hallPart.getRight(); x++) {
                if (!rectangle.isPointOnEdge(new Vector(x, y)))
                    continue;

                if (!exitPoints.containsKey(rectangle))
                    exitPoints.put(rectangle, new ArrayList<>());

                int relativeX = x - rectangle.getPosition().x;
                int relativeY = y - rectangle.getPosition().y;
                exitPoints.get(rectangle).add(new Vector(relativeX, relativeY));
            }
        }
    }

    private Rectangle generateHallPart(Vector first, Vector second) {
        int minX = Math.min(first.x, second.x);
        int maxX = Math.max(first.x, second.x);
        int minY = Math.min(first.y, second.y);
        int maxY = Math.max(first.y, second.y);

        return new Rectangle(
                new Vector(minX, minY),
                new Size(Math.max(maxX - minX + 1, 1), Math.max(maxY - minY + 1, 1))
        );
    }

    private List<Vector> getConnectPointList(Rectangle first, Rectangle second) {
        List<Vector> pointsToConnect = new ArrayList<>();

        pointsToConnect.add(getRandomRectanglePoint(first));
        Vector pointFromSecondRectangle = getRandomRectanglePoint(second);

        pointsToConnect.add(new Vector(
                pointsToConnect.get(0).x,
                pointFromSecondRectangle.y
        ));
        pointsToConnect.add(pointFromSecondRectangle);

        return pointsToConnect;
    }

    private Vector getRandomRectanglePoint(Rectangle rectangle) {
        Random random = new Random();

        int x;
        if (rectangle.getWidth() > 2)
            x = rectangle.getLeft() + 1 + random.nextInt(rectangle.getWidth() - 2);
        else
            x = rectangle.getLeft() + random.nextInt(rectangle.getWidth());

        int y;
        if (rectangle.getHeight() > 2)
            y = rectangle.getTop() + 1 + random.nextInt(rectangle.getHeight() - 2);
        else
            y = rectangle.getTop() + random.nextInt(rectangle.getHeight());

        return new Vector(x, y);
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
        Queue<Leaf> leafQueue = new ArrayDeque<>();
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

    public void generateApples(IFieldObject[][] field, int applesOnFieldAmount) {
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

    public Snake createSnake(IFieldObject[][] field) {
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
            for (int x = 0; x < width; x++)
                field[y][x] = new Wall();

        for (Rectangle rectangle : rectangles) {
            for (int y = rectangle.getTop(); y <= rectangle.getBottom(); y++)
                for (int x = rectangle.getLeft(); x <= rectangle.getRight(); x++)
                    field[y][x] = new Empty();
        }

        return field;
    }
}

