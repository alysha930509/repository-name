package gui;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;

public class Cell extends JTextField {

	/**
	 * serialVersionUID
	 * 
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;

	public static final Font FONT_NUMBERS = new Font("Josefin Sans", Font.PLAIN, 25);
	public static final int UPPERBOUND = 9;
	public static final int LOWERBOUND = 1;
	public static final Color BG_GIVEN = new Color(240, 240, 240);
	public static final Color FG_GIVEN = Color.BLACK;
	public static final Color BG_TO_GUESS = Color.YELLOW;
	public static final Color FG_NOT_GIVEN = Color.GRAY;
	public static final Color BG_CORRECT_GUESS = new Color(0, 216, 0);
	public static final Color BG_WRONG_GUESS = new Color(216, 0, 0);

	public int row, col;
	public int number;
	public CellStatus status;

	public Cell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.number = 0;
		this.status = CellStatus.TO_GUESS;
		super.setHorizontalAlignment(JTextField.CENTER);
		super.setFont(FONT_NUMBERS);
	}

	public Cell(int row, int col, int number) {
		super();
		this.row = row;
		this.col = col;
		this.number = number;
		this.status = CellStatus.GIVEN;
		super.setHorizontalAlignment(JTextField.CENTER);
		super.setFont(FONT_NUMBERS);
	}
	
	public Cell(int row, int col, int number, CellStatus status) {
		super();
		this.row = row;
		this.col = col;
		this.number = number;
		this.status = status;
		super.setHorizontalAlignment(JTextField.CENTER);
		super.setFont(FONT_NUMBERS);
	}

	public void newGame(int number, CellStatus status) {
		this.number = number;
		this.status = status;//= isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
		paint();
	}

	public void paint() {
		if (status == CellStatus.GIVEN) {
			super.setText(number + "");
			super.setEditable(false);
			super.setBackground(BG_GIVEN);
			super.setForeground(FG_GIVEN);
		} else if (status == CellStatus.TO_GUESS) {
			super.setText("");
			super.setEditable(true);
			super.setBackground(BG_TO_GUESS);
			super.setForeground(FG_NOT_GIVEN);

		} else if (status == CellStatus.CORRECT_GUESS) {
			super.setBackground(BG_CORRECT_GUESS);
		} else if (status == CellStatus.WRONG_GUESS) {
			super.setBackground(BG_WRONG_GUESS);
		}
	}

}
