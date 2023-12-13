import java.util.ArrayList;
import java.util.Arrays;

public abstract class HangmanGame
{
    private int attemptsLeft;
    private char[] wordToGuess;
    private char[] currentGuess;
    private ArrayList<Character> wrongGuesses;
    private final Difficulty difficulty;
    private final int maxAttempts;

    public HangmanGame(Difficulty d, int ma)
    {
        difficulty = d;
        maxAttempts = ma;
        wrongGuesses = new ArrayList<Character>();
    }

    protected boolean didPlayerWin()
    {
        for (int i = 0; i < wordToGuess.length; i++)
        {
            if (Character.toLowerCase(wordToGuess[i]) != Character
                    .toLowerCase(currentGuess[i]))
                return false;
        }

        return true;
    }

    protected boolean isGameOver()
    {
        return attemptsLeft == 0;
    }

    protected abstract char[] chooseWord();

    public void startGame()
    {
        wordToGuess = chooseWord();
        currentGuess = new char[wordToGuess.length];
        Arrays.fill(currentGuess, '_');
        attemptsLeft = maxAttempts;
    }

    public boolean playerMove(char playerGuess) throws InvalidGuessException
    {
        if (!Character.isLetter(playerGuess)) throw new InvalidGuessException();

        ArrayList<Integer> occurrences = findOccurrenceIndices(playerGuess);
        if (!occurrences.isEmpty())
        {
            for (int i : occurrences)
            {
                currentGuess[i] = Character.toUpperCase(playerGuess);
            }
            return true;
        }

        addToWrongGuesses(playerGuess);
        attemptsLeft--;
        return false;
    }

    private ArrayList<Integer> findOccurrenceIndices(char playerGuess)
    {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < wordToGuess.length; i++)
        {
            if (Character.toLowerCase(playerGuess) == Character
                    .toLowerCase(wordToGuess[i]))
                indices.add(i);
        }

        return indices;
    }

    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    public String getWordToGuess()
    {
        StringBuilder sb = new StringBuilder();
        for (char c : wordToGuess)
        {
            sb.append(c);
        }

        return sb.toString();
    }

    public int getAttemptsLeft()
    {
        return attemptsLeft;
    }

    public char[] getCurrentGuess()
    {
        return currentGuess;
    }

    public ArrayList<Character> getWrongGuesses()
    {
        return wrongGuesses;
    }

    private void addToWrongGuesses(char playerGuess)
    {
        wrongGuesses.add(playerGuess);
    }

    public ArrayList<Character> getAllGuessesMade()
    {
        ArrayList<Character> guesses = new ArrayList<Character>();

        for (char c : currentGuess)
        {
            if (c != '_') guesses.add(c);
        }

        guesses.addAll(wrongGuesses);

        return guesses;
    }

    public int getMaxAttempts()
    {
        return maxAttempts;
    };
}
