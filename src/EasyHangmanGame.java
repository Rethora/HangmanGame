public class EasyHangmanGame extends HangmanGame
{
    private final static int MAX_ATTEMPTS = 10;
    private WordList wordList;

    public EasyHangmanGame()
    {
        super(Difficulty.EASY, MAX_ATTEMPTS);
        wordList = new WordList(Difficulty.EASY);
    }

    @Override
    protected char[] chooseWord()
    {
        return wordList.getRandomWordFromList().toCharArray();
    }
}
