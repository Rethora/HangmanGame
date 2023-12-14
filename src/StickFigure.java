import java.nio.file.Path;
import java.nio.file.Paths;

public class StickFigure
{
    private final Path ASSETS_PATH = Paths.get("src", "assets", "stickfigure");
    public static final int HEIGHT = 792;
    public static final int WIDTH = 612;
    private int sequence = 0;

    public Path getNextSequencePath()
    {
        if (sequence == 10) return ASSETS_PATH.resolve("10.png");
        return ASSETS_PATH.resolve(Integer.toString(++sequence) + ".png");
    }

    public Path getPreviousSequencePath()
    {
        if (sequence == 0) return ASSETS_PATH.resolve("0.png");
        return ASSETS_PATH.resolve(Integer.toString(--sequence) + ".png");
    }

    public Path getCurrentSequencePath()
    {
        return ASSETS_PATH.resolve(Integer.toString(sequence) + ".png");
    }

    public int getCurrentSequenceNumber()
    {
        return sequence;
    }

    public void reset()
    {
        sequence = 0;
    }
}
