import java.util.ArrayList;
import java.util.Arrays;

/**
 * The HangmanGame abstract class represents a game of Hangman with
 * functionality
 * for managing game logic and player interactions.
 */
public abstract class HangmanGame
{
    private int attemptsLeft;
    private char[] wordToGuess;
    private char[] currentGuess;
    private ArrayList<Character> wrongGuesses;
    private final Difficulty difficulty;
    private final int maxAttempts;

    /**
     * Constructs a HangmanGame object with the specified difficulty and maximum
     * attempts.
     *
     * @param d  The difficulty level of the game.
     * @param ma The maximum number of attempts allowed.
     */
    public HangmanGame(Difficulty d, int ma)
    {
        difficulty = d;
        maxAttempts = ma;
        wrongGuesses = new ArrayList<Character>();
    }

    /**
     * Checks if the player has won the game.
     *
     * @return true if the player has won, false otherwise.
     */
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

    /**
     * Checks if the game is over.
     *
     * @return true if attempts left are zero, indicating the game is over.
     */
    protected boolean isGameOver()
    {
        return attemptsLeft == 0;
    }

    /**
     * Method to be implemented by subclasses to choose the word for the game.
     *
     * @return the word to be guessed as a character array.
     */
    protected abstract char[] chooseWord();

    /**
     * Initializes the game by choosing a word and setting up initial
     * parameters.
     */
    public void startGame()
    {
        wordToGuess = chooseWord();
        currentGuess = new char[wordToGuess.length];
        Arrays.fill(currentGuess, '_');
        attemptsLeft = maxAttempts;
    }

    /**
     * Processes the player's move.
     *
     * @param playerGuess The character guessed by the player.
     * @return true if the guess is correct, false otherwise.
     * @throws InvalidGuessException if the input character is not a letter.
     */
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

    /**
     * Finds the indices of occurrences of the guessed character in the word.
     *
     * @param playerGuess The character guessed by the player.
     * @return ArrayList containing the indices of the guessed character in the
     *         word.
     */
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

    /**
     * Gets the difficulty level of the game.
     *
     * @return the difficulty level.
     */
    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    /**
     * Gets the word to be guessed as a string.
     *
     * @return the word to be guessed.
     */
    public String getWordToGuess()
    {
        StringBuilder sb = new StringBuilder();
        for (char c : wordToGuess)
        {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Gets the number of attempts left.
     *
     * @return the number of attempts left.
     */
    public int getAttemptsLeft()
    {
        return attemptsLeft;
    }

    /**
     * Gets the current guessed word status.
     *
     * @return the current guessed word as a character array.
     */
    public char[] getCurrentGuess()
    {
        return currentGuess;
    }

    /**
     * Gets the list of wrong guesses made by the player.
     *
     * @return the list of wrong guesses as an ArrayList of characters.
     */
    public ArrayList<Character> getWrongGuesses()
    {
        return wrongGuesses;
    }

    /**
     * Adds the wrong guess made by the player to the list of wrong guesses.
     *
     * @param playerGuess The wrong guess made by the player.
     */
    private void addToWrongGuesses(char playerGuess)
    {
        wrongGuesses.add(playerGuess);
    }

    /**
     * Gets all the guesses made by the player (correct and wrong).
     *
     * @return the list of all guesses made by the player.
     */
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

    /**
     * Gets the maximum number of attempts allowed in the game.
     *
     * @return the maximum number of attempts allowed.
     */
    public int getMaxAttempts()
    {
        return maxAttempts;
    }
}
