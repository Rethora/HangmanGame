import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * This class represents the graphical user interface for a Hangman game.
 * It extends JFrame to create the game window.
 */
public class GUI extends JFrame
{
    private Difficulty selectedDifficulty;
    private HangmanGame hangmanGame;
    private JPanel gameInfoPanel;
    private JPanel difficultyPanel;
    private JPanel gamePanel;
    private JPanel playerWonPanel;
    private JPanel playerLostPanel;
    private StickFigure stickFigure;
    private final double ASSET_ASPECT_RATIO = 0.27;

    public GUI()
    {
        setSize(800, 800);
        setTitle("Hangman Game");
        setResizable(false);
        stickFigure = new StickFigure();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initializes a new a game by starting at the choose difficulty page and
     * resetting the stick figure art
     */
    private void initialize()
    {
        difficultyPanel = getNewDifficultyPanel();

        // * default options
        selectedDifficulty = Difficulty.EASY;

        add(difficultyPanel);

        difficultyPanel.setVisible(true);
        setVisible(true);
        stickFigure.reset();
    }

    /**
     * Opens the GUI and initializes a new game
     */
    public void open()
    {
        initialize();
    }

    /**
     * Gets the difficulty selection panel which includes the how to play text
     * 
     * @return the difficulty panel
     */
    private JPanel getNewDifficultyPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JPanel directionsPanel = new JPanel();

        JLabel howToPlayLabel = new JLabel(
                "<html><p>How to Play:</p><p>You will be given a random word to guess.</p><p>Options include letters a to z, no special characters or spaces.</p><p>As difficulty increases, the words will get harder and you will get less tries.</p>Good luck!</html>");
        howToPlayLabel.setHorizontalAlignment(JLabel.CENTER);

        directionsPanel.add(howToPlayLabel);

        panel.add(howToPlayLabel, BorderLayout.NORTH);

        JPanel selectionPanel = new JPanel();
        JLabel label = new JLabel("Choose difficulty:");
        label.setHorizontalAlignment(JLabel.CENTER);
        selectionPanel.add(label);

        Difficulty[] options = Difficulty.values();
        JComboBox<Difficulty> comboBox = new JComboBox<Difficulty>(options);

        selectionPanel.setAlignmentY(CENTER_ALIGNMENT);

        selectionPanel.add(comboBox, BorderLayout.CENTER);

        comboBox.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                selectedDifficulty = (Difficulty) comboBox.getSelectedItem();
            }
        });
        panel.add(selectionPanel);

        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Start");
        buttonPanel.add(button);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        button.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                startGame();
            }

        });

        return panel;
    }

    /**
     * Gets the game info panel with current difficulty and lives left
     * @ return the game info game panel
     */
    private JPanel getGameInfoPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        JLabel difficultyLabel = new JLabel("Current Difficulty: "
                + hangmanGame.getDifficulty().toString());
        difficultyLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(difficultyLabel);

        JLabel attemptsLeftLabel = new JLabel(
                "Attempts Left: " + hangmanGame.getAttemptsLeft());
        attemptsLeftLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(attemptsLeftLabel);

        return panel;
    }

    /**
     * Gets the panel that displays the wrong letters guessed
     * 
     * @return the JPanel with the current wrong guesses
     */
    private JPanel getWrongGuessesPanel()
    {
        JPanel panel = new JPanel(new GridLayout(2, 0));
        ArrayList<Character> wrongGuesses = hangmanGame.getWrongGuesses();

        JLabel label = new JLabel("Wrong guesses: ");
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);

        JPanel wrongLettersPanel = new JPanel(
                new GridLayout(0, wrongGuesses.size() | 1));

        for (char c : wrongGuesses)
        {
            JLabel charLabel = new JLabel(Character.toString(c));
            wrongLettersPanel.add(charLabel);
        }

        panel.add(wrongLettersPanel);
        return panel;
    }

    /**
     * Gets the panel that displays letters guessed and blank slots that still
     * need to be guessed
     * 
     * @return the JPanel that displays the current guess
     */
    private JPanel getCurrentGuessPanel()
    {
        JPanel panel = new JPanel();
        char[] currentGuess = hangmanGame.getCurrentGuess();
        panel.setLayout(new GridLayout(0, currentGuess.length));

        for (char c : currentGuess)
        {
            JLabel label = new JLabel(Character.toString(c));
            label.setHorizontalAlignment(JLabel.CENTER);
            panel.add(label);
        }

        return panel;
    }

    /**
     * Gets the panel that displays the hangman art figure
     * 
     * @return the JPanel that has the hangman stick figure image
     */
    private JPanel getHangmanArtPanel()
    {
        JPanel panel = new JPanel();

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(
                new ImageIcon(stickFigure.getCurrentSequencePath().toString())
                        .getImage().getScaledInstance(
                                (int) (StickFigure.WIDTH * ASSET_ASPECT_RATIO),
                                (int) (StickFigure.HEIGHT * ASSET_ASPECT_RATIO),
                                Image.SCALE_SMOOTH)));
        panel.add(label);
        return panel;
    }

    /**
     * Gets the panel that allows the player to guess a letter
     * 
     * @return the JPanel that contains the text field for the player to guess a
     *         letter
     */
    private JPanel getGuesserPanel()
    {
        JPanel panel = new JPanel();

        JTextField textField = new JTextField("", 1);

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                textField.requestFocus();
            }
        });

        textField.requestFocus();
        // * limit character input to 1 character
        textField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if (textField.getText().length() >= 1)
                {
                    e.consume();
                }
            }
        });
        panel.add(textField);

        JButton button = new JButton("Guess");
        button.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                String textFieldValue = textField.getText();
                if (textFieldValue.length() == 0) return;

                try
                {
                    char playerMove = textFieldValue.charAt(0);
                    if (hangmanGame.getAllGuessesMade().contains(playerMove))
                    {
                        return;
                    }
                    boolean isCorrect = hangmanGame.playerMove(playerMove);
                    if (!isCorrect) stickFigure.getNextSequencePath();
                }
                catch (InvalidGuessException exp)
                {
                    // TODO: display exp.getMessage()
                    return;
                }

                if (hangmanGame.didPlayerWin())
                {
                    gamePanel.removeAll();
                    playerWonPanel = getPlayerWonPanel();
                    add(playerWonPanel);
                    playerWonPanel.setVisible(true);
                    revalidate();
                }
                else if (hangmanGame.isGameOver())
                {
                    gamePanel.removeAll();
                    playerLostPanel = getPlayerLostPanel();
                    add(playerLostPanel);
                    playerLostPanel.setVisible(true);
                    revalidate();
                }
                else
                {
                    rePaintGamePanel();
                }
            }

        });
        getRootPane().setDefaultButton(button);
        panel.add(button);

        return panel;
    }

    /**
     * Gets the entire game panel with all components that make up the actual
     * game
     * 
     * @return the JPanel that is used for the main game logic
     */
    private JPanel getGamePanel()
    {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel northPanel = new JPanel();
        gameInfoPanel = getGameInfoPanel();
        northPanel.add(gameInfoPanel);
        gamePanel.add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 0));
        JPanel wrongGuessesPanel = getWrongGuessesPanel();
        centerPanel.add(wrongGuessesPanel);

        JPanel hangmanArtPanel = getHangmanArtPanel();

        centerPanel.add(hangmanArtPanel);

        JPanel currentGuessPanel = getCurrentGuessPanel();
        centerPanel.add(currentGuessPanel);
        gamePanel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        JPanel guesserPanel = getGuesserPanel();
        southPanel.add(guesserPanel);

        gamePanel.add(southPanel, BorderLayout.SOUTH);
        return gamePanel;
    }

    /**
     * Gets the panel that is displayed when a user wins
     * 
     * @return the JPanel that is used when a player wins a game
     */
    private JPanel getPlayerWonPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));
        JLabel label = new JLabel("YOU WON with "
                + Integer.toString(hangmanGame.getAttemptsLeft())
                + " tries left! The word was " + hangmanGame.getWordToGuess()
                + ".");
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label, BorderLayout.NORTH);

        JButton button = new JButton("New Game");
        button.setPreferredSize(new Dimension(300, 40));

        button.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                playerWonPanel.removeAll();
                initialize();
                revalidate();
            }
        });
        panel.add(button, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Gets the panel that is displayed when a player loses the game
     * 
     * @return the JPanel that is used when the player loses
     */
    private JPanel getPlayerLostPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));

        JLabel label = new JLabel(
                "YOU LOST! The word was " + hangmanGame.getWordToGuess() + ".");
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label, BorderLayout.NORTH);

        JButton button = new JButton("New Game");
        button.setPreferredSize(new Dimension(300, 40));

        button.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                playerLostPanel.removeAll();
                initialize();
                revalidate();
            }
        });
        panel.add(button, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Handles the start of the game by instantiating the appropriate hangman
     * game by chosen difficulty
     */
    private void startGame()
    {
        switch (selectedDifficulty)
        {
            case EASY:
                hangmanGame = new EasyHangmanGame();
                break;
            case MEDIUM:
                hangmanGame = new MediumHangmanGame();
                break;
            case HARD:
                hangmanGame = new HardHangmanGame();
                break;
            default:
                break;
        }

        difficultyPanel.removeAll();
        hangmanGame.startGame();
        gamePanel = getGamePanel();
        add(gamePanel);
        gamePanel.setVisible(true);
        revalidate();
    }

    /**
     * Repaints the game panel
     */
    private void rePaintGamePanel()
    {
        gamePanel.removeAll();
        gamePanel = getGamePanel();
        add(gamePanel);
        revalidate();
    }
}
