/**
 * The MediumHangmanGame class represents an medium level implementation of the
 * Hangman game
 * with functionality specific to the medium difficulty.
 */
public class MediumHangmanGame extends HangmanGame
{

    /**
     * Constructs an MediumHangmanGame object with the difficulty set to medium
     * and the maximum attempts set to the predefined value.
     */
    private final static int MAX_ATTEMPTS = 8;
    private WordList wordList;

    public MediumHangmanGame()
    {
        super(Difficulty.MEDIUM, MAX_ATTEMPTS);
        wordList = new WordList(Difficulty.MEDIUM);
    }

    /**
     * Chooses a word from the word list for the game.
     *
     * @return the chosen word converted into a character array.
     */
    @Override
    protected char[] chooseWord()
    {
        return wordList.getRandomWordFromList().toCharArray();
    }
}
