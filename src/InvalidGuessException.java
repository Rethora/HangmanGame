/**
 * Represents an exception thrown for an invalid guess in the Hangman game.
 */
public class InvalidGuessException extends Exception
{
    /**
     * Retrieves the error message for the invalid guess exception.
     *
     * @return The error message specifying that the guess must be a letter from
     *         A-Z.
     */
    @Override
    public String getMessage()
    {
        return "Guess must be a letter from A-Z";
    }
}
