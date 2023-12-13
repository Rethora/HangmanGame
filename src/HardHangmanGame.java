public class HardHangmanGame extends HangmanGame
{
    private final static int MAX_ATTEMPTS = 6;
    private WordList wordList;

    public HardHangmanGame()
    {
        super(Difficulty.HARD, MAX_ATTEMPTS);
        wordList = new WordList(Difficulty.HARD);
    }

    @Override
    protected char[] chooseWord()
    {
        return wordList.getRandomWordFromList().toCharArray();
    }
}
