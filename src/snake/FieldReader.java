package snake;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class FieldReader {
    public String fileName;
    private Map<String, Class> characterSymbol;
    public FieldObject[][] objects;
    public SnakePart snake;
    private int snakeX;
    private int snakeY;
    private Vector direction;

    public FieldReader(String fileName) throws IllegalAccessException,
            InstantiationException, NoSuchMethodException,
            InvocationTargetException, IOException {
        this.fileName = fileName;
        characterSymbol = getCharacterSymbol();
        fillObjects();
    }

    private HashMap<String, Class> getCharacterSymbol(){
        HashMap<String, Class> characterSymbol = new HashMap<>();
        characterSymbol.put("#", Wall.class);
        characterSymbol.put(" ", Empty.class);
        characterSymbol.put("A", Apple.class);
        characterSymbol.put("S", SnakePart.class);
        return characterSymbol;
    }

    private void fillObjects() throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        final String dir = System.getProperty("user.dir");
        List<String> lines = Files.readAllLines(
                Paths.get(dir+"/levels/"+fileName),
                StandardCharsets.UTF_8);

        try {
            objects = new FieldObject[lines.size()][lines.get(0).length()];
        } catch (IndexOutOfBoundsException e){
            throw new IllegalArgumentException("Level is incorrect");
        }
        for (int i = 0; i < lines.size() && i < 1; i++){
            String[] a = lines.get(i).split(" ");
            direction = new Vector(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
        }
        for(int i = 1; i < lines.size(); i++){
            for (int j = 0; j < lines.get(i).length(); j++){
                String symbol = String.valueOf(lines.get(i).charAt(j));
                Class[] type;
                Integer[] args;
                if (Objects.equals(symbol, "S")){
                    type = new Class[]{int.class, int.class, Vector.class, SnakePart.class, SnakePart.class};
                    args = new Integer[]{j, i, null, null, null};
                    snakeX = j;
                    snakeY = i;
                }
                else {
                    type = new Class[]{int.class, int.class};
                    args = new Integer[]{j, i};
                }
                objects[i][j] = getNewObject(getConstructor(symbol,type), args);
            }
        }
        snake = (SnakePart) objects[snakeY][snakeX];
        snake.direction = direction;
    }

    private Constructor getConstructor(String symbol, Class... type) throws NoSuchMethodException {
        return characterSymbol.get(symbol).getConstructor(type);
    }
    private FieldObject getNewObject(Constructor constructor, Integer... args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return (FieldObject) constructor.newInstance(args);
    }
}
