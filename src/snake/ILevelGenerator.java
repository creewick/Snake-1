package snake;

public interface ILevelGenerator {
    public Level generateLevel(
            int width,
            int height,
            int applesOnFieldAmount,
            int applesToGenerateAmount);
}
