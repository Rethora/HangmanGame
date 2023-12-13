import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.nio.file.Paths;
import java.nio.file.Path;

public class WordList
{
    private ArrayList<String> wordList;
    private ArrayList<String> usedWords;
    private final Path ASSETS_PATH = Paths.get("src", "assets", "words");

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
                path = ASSETS_PATH.resolve("hard.txt");
                break;
            default:
                path = ASSETS_PATH.resolve("hard.txt");
                break;
        }

        parseWords(path);
    }

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

    public String getRandomWordFromList()
    {
        // reset list if fully used
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
