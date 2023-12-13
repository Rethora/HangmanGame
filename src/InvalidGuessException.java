public class InvalidGuessException extends Exception
{
    @Override
    public String getMessage()
    {
        return "Guess must be a letter from A-Z";
    }
}
