import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a Word List used in the Hangman game.
 */
public class WordList
{
    // Fields
    private ArrayList<String> wordList;
    private ArrayList<String> usedWords;
    private final Path ASSETS_PATH = Paths.get("src", "assets", "words");

    /**
     * Constructor for WordList class.
     * Initializes the WordList based on the specified difficulty level.
     *
     * @param difficulty The difficulty level for the word list.
     */
    public WordList(Difficulty difficulty)
    {
        wordList = new ArrayList<String>();
        usedWords = new ArrayList<String>();
        Path path;
        switch (difficulty)
        {
            case EASY:
                path = ASSETS_PATH.resolve("easy.txt");
                break;
            case MEDIUM:
                path = ASSETS_PATH.resolve("medium.txt");
                break;
            case HARD:
            default:
                path = ASSETS_PATH.resolve("hard.txt");
                break;
        }
        parseWords(path);
    }

    /**
     * Parses words from the specified file path and adds them to the word list.
     *
     * @param filePath The path of the file containing the words to be parsed.
     */
    private void parseWords(Path filePath)
    {
        try
        {
            BufferedReader reader = new BufferedReader(
                    new FileReader(filePath.toFile()));
            String word;
            while ((word = reader.readLine()) != null)
            {
                wordList.add(word);
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a random word from the word list.
     * Resets the list if all words have been used.
     *
     * @return A random word from the word list.
     */
    public String getRandomWordFromList()
    {
        // Reset list if fully used
        Random rand = new Random();
        if (usedWords.size() == wordList.size())
        {
            wordList = new ArrayList<String>();
        }

        int randomIndex = rand.nextInt(wordList.size());
        String word = wordList.get(randomIndex);

        while (usedWords.contains(word))
        {
            randomIndex = rand.nextInt(wordList.size());
            word = wordList.get(randomIndex);
        }

        usedWords.add(word);
        return word;
    }
}
