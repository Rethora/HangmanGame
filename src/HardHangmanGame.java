/**
 * The HardHangmanGame class represents an hard level implementation of the
 * Hangman game
 * with functionality specific to the hard difficulty.
 */
public class HardHangmanGame extends HangmanGame
{
    private final static int MAX_ATTEMPTS = 6;
    private WordList wordList;

    /**
     * Constructs an HardHangmanGame object with the difficulty set to hard
     * and the maximum attempts set to the predefined value.
     */
    public HardHangmanGame()
    {
        super(Difficulty.HARD, MAX_ATTEMPTS);
        wordList = new WordList(Difficulty.HARD);
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
