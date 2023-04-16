package sudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GameBoardPanel extends JPanel {
	public static final int GRID_SIZE = 9;
	public static final int SUBGRID_SIZE = 3;
	public static final int CELL_SIZE = 60;
	public static final int BOARD_WIDTH = CELL_SIZE * GRID_SIZE;
	public static final int BOARD_HEIGHT = CELL_SIZE * GRID_SIZE;

	public Cell[][] cells = new Cell[GRID_SIZE + 1][GRID_SIZE + 1];

	static Font GameFont = new Font("OCR A Extended", Font.BOLD, 32);
	static Font ToolFont = new Font("OCR A Extended", Font.PLAIN, 20);

	public Cell[][] getCells(){
		return cells;
	}
	// private double percent;

	public Puzzle puzzle = new Puzzle();
	
	public Puzzle getPuzzle(){
		return puzzle;
	}

	public GameBoardPanel() {
		initSudoku();
		initWindow();
	}

	public void initSudoku() {
		for (int row = 1; row <= GRID_SIZE; row++) {
			for (int col = 1; col <= GRID_SIZE; col++) {
				cells[row][col] = new Cell(row, col);
//				super.add(cells[row][col]);
			}
		}
		
	}

	public void initWindow() {
		GridLayout grid = new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE);
		setLayout(grid);
		for (int row = 1; row <= SUBGRID_SIZE; row++) {
			for (int col = 1; col <= SUBGRID_SIZE; col++) {
				AreaBlocks areaBlocks = new AreaBlocks(row, col);
				areaBlocks.setBackground(Color.gray);
				areaBlocks.setBorder(BorderFactory.createLineBorder(Color.white, 2));
				add(areaBlocks);
			}
		}
//		super.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
//		for (int row = 1; row <= GRID_SIZE; row++) {
//			for (int col = 1; col <= GRID_SIZE; col++) {
//				cells[row][col] = new Cell(row, col);
//				super.add(cells[row][col]);
//			}
//		}
		
		// [TODO 3]
		CellInputListener listener = new CellInputListener();
		
		// [TODO 4]
		for (int row = 1; row <= GRID_SIZE; row++) {
			for (int col = 1; col <= GRID_SIZE; col++) {
				if (cells[row][col].isEditable()) {
					cells[row][col].addActionListener(listener);  // For all editable rows and cols
				}
			}
		}
		super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
	}

	class AreaBlocks extends JPanel {
		private static final long serialVersionUID = 1L;
		private GridLayout grid = new GridLayout(3, 3);
		private int Row;
		private int Col;

		AreaBlocks(int Row, int Col) {
			this.Row = Row;
			this.Col = Col;
			init();
		}

		private void init() {
			setLayout(grid);
			for (int r = 0; r < 3; r++) {
				for (int c = 0; c < 3; c++) {
					int row = (Row - 1) * 3 + r + 1;
					int col = (Col - 1) * 3 + c + 1;
					
					if (cells[row][col].status == CellStatus.TO_GUESS) {
						add(cells[row][col]);
					} else {
						add(cells[row][col]);
					}
				}
			}
		}
	}

	class BlockViewer extends JLabel {
		private static final long serialVersionUID = 1L;

		BlockViewer(int row, int col) {
			super(String.valueOf(cells[row][col].number), JLabel.CENTER);
			setFont(GameFont);
			setBorder(BorderFactory.createLineBorder(Color.black, 1));
			setForeground(Color.black);
		}
	}

	class BlockEditor extends JTextField {
		private static final long serialVersionUID = 1L;

		BlockEditor(int row, int col) {
			if (cells[row][col].number != 0) {
				setText(String.valueOf(cells[row][col].number));
			}
			setFont(GameFont);
			setBorder(BorderFactory.createLineBorder(Color.white, 1));
			setRightColor();
			setHorizontalAlignment(JTextField.CENTER);
			getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					if (getDocument().getLength() > 1) {
						cells[row][col].number = 0;
						setWrongColor();
					} else {
						changedUpdate(e);
					}
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					if (getDocument().getLength() == 0) {
						cells[row][col].number = 0;
						setRightColor();
					} else {
						changedUpdate(e);
					}
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					// System.out.println("in : function_changedUpdate");
					try {
						int value = Integer.valueOf(getDocument().getText(0, 1));
						cells[row][col].number = value;
						if (puzzle.check(row, col, cells)) {
							setRightColor();
						} else {
							throw new Exception();
						}
					} catch (Exception err) {
						cells[row][col].number = 0;
						setWrongColor();
						return;
					}
					if (puzzle.isSolved(cells)) {
						puzzle.getEmpty(cells);
						puzzle.solve(0, cells);
					}
				}
			});
		}

		private void setRightColor() {
			setForeground(Color.yellow);
			setBackground(Color.black);
		}

		private void setWrongColor() {
			setForeground(Color.white);
			setBackground(Color.red);
		}
	}

	public void newGame(String difficulty) {
		System.out.println("GameBoardPanle: " + difficulty);
		double percent = 0.36;
		if("Easy".equals(difficulty)){
			percent = 0.42;
		}else if("Intermediate".equals(difficulty)){
			percent = 0.36;
		}else if("Difficult".equals(difficulty)){
			percent = 0.26;
		}
		for (int row = 1; row <= GRID_SIZE; row++) {
			for (int col = 1; col <= GRID_SIZE; col++) {
				cells[row][col].number = 0;
				cells[row][col].status = CellStatus.TO_GUESS;
			}
		}
		puzzle.newPuzzle(percent, cells);

		for (int row = 1; row <= GRID_SIZE; row++) {
			for (int col = 1; col <= GRID_SIZE; col++) {
				cells[row][col].newGame(puzzle.results[row][col].number, puzzle.results[row][col].status);
			}
		}
	}

	// [TODO 2] Define a Listener Inner Class for all the editable Cells
	private class CellInputListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Get a reference of the JTextField that triggers this action event
			Cell sourceCell = (Cell) e.getSource();

			// Retrieve the int entered
			int numberIn = Integer.parseInt(sourceCell.getText());

			// For debugging
			System.out.println("You entered " + numberIn);

			
			/*
	          * [TODO 5] (later - after TODO 3 and 4)
	          * Check the numberIn against sourceCell.number.
	          * Update the cell status sourceCell.status,
	          * and re-paint the cell via sourceCell.paint().
	          */
			if (numberIn == sourceCell.number) {
				sourceCell.status = CellStatus.CORRECT_GUESS;
			} else {
				sourceCell.status = CellStatus.WRONG_GUESS;
			}
			sourceCell.paint();

			
			/*
	          * [TODO 6] (later)
	          * Check if the player has solved the puzzle after this move,
	          *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
	          */
			if (puzzle.isSolved(cells)) {
				JOptionPane.showMessageDialog(null, "Congratulation!");
			}
		}
	}
}