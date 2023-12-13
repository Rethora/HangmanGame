import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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

    public GUI()
    {
        setSize(800, 800);
        setTitle("Hangman Game");
        setResizable(false);
        stickFigure = new StickFigure();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initialize()
    {
        difficultyPanel = getNewDifficultyPanel();

        // * default options
        selectedDifficulty = Difficulty.EASY;

        add(difficultyPanel);

        JButton button = new JButton("Start");
        difficultyPanel.add(button);

        button.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                startGame();
            }

        });

        difficultyPanel.setVisible(true);
        setVisible(true);
        stickFigure.reset();
    }

    public void open()
    {
        initialize();
    }

    private JPanel getNewDifficultyPanel()
    {
        JPanel panel = new JPanel();

        JLabel label = new JLabel("Choose difficulty:");
        panel.add(label);

        Difficulty[] options = Difficulty.values();
        JComboBox<Difficulty> comboBox = new JComboBox<Difficulty>(options);
        panel.add(comboBox);

        comboBox.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                selectedDifficulty = (Difficulty) comboBox.getSelectedItem();
            }
        });

        return panel;
    }

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

    private JPanel getWrongGuessesPanel()
    {
        JPanel panel = new JPanel();
        ArrayList<Character> wrongGuesses = hangmanGame.getWrongGuesses();

        panel.setLayout(new GridLayout(0, wrongGuesses.size() + 1));
        JLabel label = new JLabel("Wrong guesses: ");
        panel.add(label);

        for (char c : wrongGuesses)
        {
            JLabel charLabel = new JLabel(Character.toString(c));
            panel.add(charLabel);
        }

        return panel;
    }

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

    private JPanel getHangmanArtPanel()
    {
        JPanel panel = new JPanel();

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(
                new ImageIcon(stickFigure.getCurrentSequencePath().toString())
                        .getImage()
                        .getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
        panel.add(label);
        return panel;
    }

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

    private JPanel getGamePanel()
    {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(5, 0));

        gameInfoPanel = getGameInfoPanel();
        gameInfoPanel.setVisible(true);
        gamePanel.add(gameInfoPanel);

        JPanel wrongGuessesPanel = getWrongGuessesPanel();
        wrongGuessesPanel.setVisible(true);
        gamePanel.add(wrongGuessesPanel);

        JPanel currentGuessPanel = getCurrentGuessPanel();
        currentGuessPanel.setVisible(true);
        gamePanel.add(currentGuessPanel);

        JPanel hangmanArtPanel = getHangmanArtPanel();
        hangmanArtPanel.setVisible(true);
        gamePanel.add(hangmanArtPanel);

        JPanel guesserPanel = getGuesserPanel();
        guesserPanel.setVisible(true);
        gamePanel.add(guesserPanel);

        return gamePanel;
    }

    private JPanel getPlayerWonPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 0));

        JLabel label = new JLabel("YOU WON with "
                + Integer.toString(hangmanGame.getAttemptsLeft())
                + " tries left! The word was " + hangmanGame.getWordToGuess()
                + ".");
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);

        JButton button = new JButton("New Game");
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
        panel.add(button);

        return panel;
    }

    private JPanel getPlayerLostPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 0));

        JLabel label = new JLabel(
                "YOU LOST! the word was " + hangmanGame.getWordToGuess() + ".");
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);

        JButton button = new JButton("New Game");
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
        panel.add(button);

        return panel;
    }

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

    private void rePaintGamePanel()
    {
        gamePanel.removeAll();
        gamePanel = getGamePanel();
        add(gamePanel);
        revalidate();
    }
}
