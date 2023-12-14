/**
 * The EasyHangmanGame class represents an easy level implementation of the
 * Hangman game
 * with functionality specific to the easy difficulty.
 */
public class EasyHangmanGame extends HangmanGame
{
    private final static int MAX_ATTEMPTS = 10;
    private WordList wordList;

    /**
     * Constructs an EasyHangmanGame object with the difficulty set to easy
     * and the maximum attempts set to the predefined value.
     */
    public EasyHangmanGame()
    {
        super(Difficulty.EASY, MAX_ATTEMPTS);
        wordList = new WordList(Difficulty.EASY);
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
