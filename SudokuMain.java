package gui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SudokuMain extends JFrame{
    private static final long serialVersionUID = 1L;  
    
    private GameBoardPanel gameBoard=new GameBoardPanel();
    private JComboBox<String> difficultyComboBox = new JComboBox<>();
    private JButton btnNewGame=new JButton("New Game");
    private JButton btnResetGame=new JButton("Reset Game");
    private JButton btnExitGame=new JButton("Exit Game");
    
    public SudokuMain(){
        Container cp=getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(gameBoard,BorderLayout.CENTER);
        JPanel controlPanel = new JPanel(new FlowLayout()); // create a new JPanel for the button
        cp.add(controlPanel, BorderLayout.SOUTH); // add the JPanel to the south of the content pane
        difficultyComboBox.addItem("Easy");
        difficultyComboBox.addItem("Intermediate");
        difficultyComboBox.addItem("Difficult");
        // create the "New Game" button and add an action listener to call board.newGame() when clicked
        btnNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String difficulty = (String) difficultyComboBox.getSelectedItem();
            	gameBoard.newGame(difficulty);
            }
        });
        btnResetGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// 清空所有编辑框
            	
            	gameBoard.getPuzzle().reset(gameBoard);
            }
        });
        btnExitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//退出游戏
            	System.exit(0);
            }
        });
        controlPanel.add(btnNewGame); // add the button to the control panel
        controlPanel.add(btnResetGame);
        controlPanel.add(btnExitGame);
        controlPanel.add(difficultyComboBox);
        String difficulty = (String) difficultyComboBox.getSelectedItem();
        gameBoard.newGame(difficulty);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }
    public static void main(String[] args) throws Exception {
        new SudokuMain();
	}
}
