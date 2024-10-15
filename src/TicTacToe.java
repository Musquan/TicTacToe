import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe {
    private JFrame frame;
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private JLabel statusLabel;
    private JButton restartButton;
    private JLabel scoreLabel;
    private int xWins = 0, oWins = 0, draws = 0;

    public TicTacToe() {
        // Initialize the GUI frame
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel for the buttons (Tic-Tac-Toe board)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        // Initialize buttons and add them to the panel
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton(" ");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 80));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                panel.add(buttons[row][col]);
            }
        }

        // Label to display game status (e.g., "Player X's turn")
        statusLabel = new JLabel("Player X's turn");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        // Label to display the scoreboard
        scoreLabel = new JLabel("Player X: 0 | Player O: 0 | Draws: 0");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);

        // Panel for the status and restart button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        // Restart button
        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        // Add status label, scoreboard, and restart button to bottom panel
        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        bottomPanel.add(scoreLabel, BorderLayout.CENTER);
        bottomPanel.add(restartButton, BorderLayout.EAST);

        // Add components to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Set the frame properties
        frame.setSize(400, 500); // Adjusted size to fit the scoreboard
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameOver && buttons[row][col].getText().equals(" ")) {
                // Place the player's symbol (X or O)
                buttons[row][col].setText(String.valueOf(currentPlayer));

                gameOver = haveWon(currentPlayer);

                // Update status and switch players
                if (gameOver) {
                    statusLabel.setText("Player " + currentPlayer + " wins!");
                    updateScore(currentPlayer);
                    disableButtons();
                } else if (isBoardFull()) {
                    statusLabel.setText("It's a draw!");
                    gameOver = true;
                    draws++;
                    updateScoreboard();
                    disableButtons();
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    statusLabel.setText("Player " + currentPlayer + "'s turn");
                }
            } else if (!gameOver) {
                statusLabel.setText("Invalid move, try again!");
            }
        }
    }

    private boolean haveWon(char player) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(player)) &&
                    buttons[i][1].getText().equals(String.valueOf(player)) &&
                    buttons[i][2].getText().equals(String.valueOf(player))) {
                return true;
            }
            if (buttons[0][i].getText().equals(String.valueOf(player)) &&
                    buttons[1][i].getText().equals(String.valueOf(player)) &&
                    buttons[2][i].getText().equals(String.valueOf(player))) {
                return true;
            }
        }
        // Check diagonals
        if (buttons[0][0].getText().equals(String.valueOf(player)) &&
                buttons[1][1].getText().equals(String.valueOf(player)) &&
                buttons[2][2].getText().equals(String.valueOf(player))) {
            return true;
        }
        if (buttons[0][2].getText().equals(String.valueOf(player)) &&
                buttons[1][1].getText().equals(String.valueOf(player)) &&
                buttons[2][0].getText().equals(String.valueOf(player))) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        // Check if the board is full (draw)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private void resetGame() {
        // Reset all buttons to be empty and re-enable them
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(" ");
                buttons[row][col].setEnabled(true);
            }
        }
        // Reset the game state
        currentPlayer = 'X';
        gameOver = false;
        statusLabel.setText("Player X's turn");
    }

    private void updateScore(char winner) {
        if (winner == 'X') {
            xWins++;
        } else {
            oWins++;
        }
        updateScoreboard();
    }

    private void updateScoreboard() {
        scoreLabel.setText("Player X: " + xWins + " | Player O: " + oWins + " | Draws: " + draws);
    }

    public static void main(String[] args) {
        // Run the game
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
