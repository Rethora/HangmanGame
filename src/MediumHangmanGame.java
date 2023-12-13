public class MediumHangmanGame extends HangmanGame
{

    private final static int MAX_ATTEMPTS = 8;
    private WordList wordList;

    public MediumHangmanGame()
    {
        super(Difficulty.MEDIUM, MAX_ATTEMPTS);
        wordList = new WordList(Difficulty.MEDIUM);
    }

    @Override
    protected char[] chooseWord()
    {
        return wordList.getRandomWordFromList().toCharArray();
    }
}
