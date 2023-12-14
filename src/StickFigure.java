import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a stick figure for visual representation in the Hangman game.
 */
public class StickFigure
{
    // Fields
    private final Path ASSETS_PATH = Paths.get("src", "assets", "stickfigure");
    public static final int HEIGHT = 792;
    public static final int WIDTH = 612;
    private int sequence = 0;

    /**
     * Gets the path for the next sequence image of the stick figure.
     *
     * @return The path to the next sequence image.
     */
    public Path getNextSequencePath()
    {
        if (sequence == 10) return ASSETS_PATH.resolve("10.png");
        return ASSETS_PATH.resolve(Integer.toString(++sequence) + ".png");
    }

    /**
     * Gets the path for the previous sequence image of the stick figure.
     *
     * @return The path to the previous sequence image.
     */
    public Path getPreviousSequencePath()
    {
        if (sequence == 0) return ASSETS_PATH.resolve("0.png");
        return ASSETS_PATH.resolve(Integer.toString(--sequence) + ".png");
    }

    /**
     * Gets the path for the current sequence image of the stick figure.
     *
     * @return The path to the current sequence image.
     */
    public Path getCurrentSequencePath()
    {
        return ASSETS_PATH.resolve(Integer.toString(sequence) + ".png");
    }

    /**
     * Gets the current sequence number.
     *
     * @return The current sequence number of the stick figure.
     */
    public int getCurrentSequenceNumber()
    {
        return sequence;
    }

    /**
     * Resets the sequence number of the stick figure to the initial state.
     */
    public void reset()
    {
        sequence = 0;
    }
}
