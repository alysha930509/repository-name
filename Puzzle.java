package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;



public class Puzzle {

	Cell[][] results = new Cell[GameBoardPanel.GRID_SIZE + 1][GameBoardPanel.GRID_SIZE + 1];
//	boolean[][] isGiven = new boolean[GameBoardPanel.GRID_SIZE + 1][GameBoardPanel.GRID_SIZE + 1];
	ArrayList<Cell> emptys = new ArrayList<>(); // 空值集合
	boolean hasAns;
	
	public Puzzle() {
		super();
	}

	public void newPuzzle(double percent, Cell[][] cells) {
		
		next(percent, cells);
		// Generate Random Numbers For Cells\
		// TODO:Rule for Generating
//		int generatedNumber;
//
//		for (int i = 1; i <= GameBoardPanel.GRID_SIZE; i++) {
//			for (int j = 1; j <= GameBoardPanel.GRID_SIZE; j++) {
//				generatedNumber = generateRandomNum();
//				for (int rows = 0; rows < j; rows++) {
//					int y_numbers[] = new int[GameBoardPanel.GRID_SIZE];
//					for (int k = 0; k <= rows; k++) {
//						y_numbers[k] = numbers[k][j];
//					}
//					if (y_numbers[rows] == generatedNumber) {
//						generatedNumber = generateRandomNum();
//						rows = 0;
//					}
//				}
//				for (int col = 0; col < i; col++) {
//					int x_numbers[] = numbers[col];
//					for (int k = 0; k <= col; k++) {
//						x_numbers[k] = numbers[k][j];
//					}
//					if (x_numbers[col] == generatedNumber) {
//						generatedNumber = generateRandomNum();
//						col = 0;
//					}
//				}
//				numbers[i][j] = generatedNumber;
//
//			}
//			// numbers[i][j]=generateRandomNum();
//		}
//		// Check if number exists in row or column
//		// If it does, generate a new number
//		// If it doesn't, add it to the array
//
//		boolean[][] hardcodedIsGiven = { { false, true, true, true, true, true, true, true, true , true },
//				{ true, true, true, true, true, true, true, true, true , true },
//				{ true, true, true, true, true, true, true, true, true , true },
//				{ true, true, true, true, true, true, true, true, true , true },
//				{ true, true, true, true, true, true, true, true, true , true },
//				{ true, true, true, true, true, true, true, true, true , true },
//				{ true, true, true, true, true, true, true, true, true , true },
//				{ true, true, true, true, true, true, true, true, true , true },
//				{ true, true, true, true, true, true, true, true, true , true },
//				{ true, true, true, true, true, true, true, true, true , true } };
//		// Generate Random Boolean for Cells
//		// TODO:Rule for hiding elements(amount)
//		for (int row = 1; row <= GameBoardPanel.GRID_SIZE; row++) {
//			for (int col = 1; col <= GameBoardPanel.GRID_SIZE; col++) {
//				// isGiven[row][col]=generateRandomIsGiven();
//				isGiven[row][col] = hardcodedIsGiven[row][col];
//			}
//		}

	}
	
	public void reset(GameBoardPanel gamePanel){
		for (int row = 1; row <= GameBoardPanel.GRID_SIZE; row++) {
			for (int col = 1; col <= GameBoardPanel.GRID_SIZE; col++) {
				gamePanel.cells[row][col].newGame(gamePanel.puzzle.results[row][col].number, gamePanel.puzzle.results[row][col].status);
			}
		}
	}
	
	// 随机生成题目，需要传入难度系数，即数独完成度
	public void next(double percent, Cell[][] cells) {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        if (percent > 1 || percent < 0) {
            percent = 0.36;
        }
        // 确定需要填写数独的个数
        int cnt = (int) Math.ceil(percent * GameBoardPanel.GRID_SIZE * GameBoardPanel.GRID_SIZE);
        // 确保是有解的方案
        do {
        	// 初始化变量
            init();
            copyof(cells, results);
            // 执行次数为空格子数量，now为当前填写的次数，cnt为需要填写数据的个数
            for (int now = 0; now < cnt; now++) {
            	//随机生成空格坐标
                int row = rnd.nextInt(GameBoardPanel.GRID_SIZE) + 1;
                int col = rnd.nextInt(GameBoardPanel.GRID_SIZE) + 1;
                int times = 0;
                // 如果生成的随机坐标对应的棋盘为空
                if (results[row][col] == null || results[row][col].number == 0) {
                    do {
                    	results[row][col].number = rnd.nextInt(9) + 1;
                    	results[row][col].status = CellStatus.GIVEN;
                        times++;
                        if (times == 9) {
                        	results[row][col].number=0;
                            now--;
                            break;
                        }
                        if(check(row, col, results) == true){
                        	break;
                        }
                    } while (true);
                } else {
                    now--;
                }
            }
         
       
//            System.out.println("原始矩阵");
//            print(results);
            setHasAns(results);
//            System.out.println("答案矩阵");
//            print(results);
//            System.out.println("是否有解" + hasAns);
        } while (hasAns == false);
        // consoleOut();
      System.out.println("答案矩阵");
      print(results);
    }
	
	public void copyof(Cell[][] source, Cell[][] target) {
		for (int row=1; row <=GameBoardPanel.GRID_SIZE; row++) {
			for(int col=1; col <=GameBoardPanel.GRID_SIZE; col++){
				target[row][col] = new Cell(source[row][col].row, source[row][col].col, source[row][col].number, source[row][col].status);
			}
		}
	}
	
	public void print(Cell[][] cells){
		for (int row = 1; row <= GameBoardPanel.GRID_SIZE; row++) {
			for (int col = 1; col <= GameBoardPanel.GRID_SIZE; col++) {
				if (cells[row][col] != null) {
					System.out.print(cells[row][col].number + " ");
				}
			}
			System.out.println();
		}
	}
	
	public void init(){
//		results = new Cell[GameBoardPanel.GRID_SIZE + 1][GameBoardPanel.GRID_SIZE + 1];
//        for (int row = 1; row <= GameBoardPanel.GRID_SIZE; row++) {
//            for (int col = 1; col <= GameBoardPanel.GRID_SIZE; col++) {
//            	results[row][col] = new Cell(row, col);
//            }
//        }
        emptys = new ArrayList<Cell>();
        hasAns = false;
	}
	
	public void setHasAns(Cell[][] cells){
//		Puzzle bak = new Puzzle();
		if(emptys == null || emptys.size() == 0){
			getEmpty(cells);
		}
//    	hasAns = bak.solve(0, cells);
		hasAns = solve(0, cells);
	}
	
	public void getEmpty(Cell[][] cells){
		emptys.clear();
		for (int row = 1; row <= GameBoardPanel.GRID_SIZE; row++) {
            for (int col = 1; col <= GameBoardPanel.GRID_SIZE; col++) {
                if (cells[row][col] == null || cells[row][col].number == 0) {
                    emptys.add(cells[row][col]);
                }
            }
        }
	}
	
	public boolean isSolved(Cell[][] cells) {
		for (int row = 1; row <= GameBoardPanel.GRID_SIZE; row++) {
			for (int col = 1; col <= GameBoardPanel.GRID_SIZE; col++) {
				if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean solve(int cnt, Cell[][] cells) {
		
		if (cnt == emptys.size()) {
			return true;
		}
		for (int i = 1; i <= GameBoardPanel.GRID_SIZE; i++) {
			cells[emptys.get(cnt).row][emptys.get(cnt).col].number = i;
			if (check(emptys.get(cnt).row, emptys.get(cnt).col,cells) && solve(cnt + 1, cells)) {
				return true;
			}
		}
		cells[emptys.get(cnt).row][emptys.get(cnt).col].number = 0;
		return false;
	}

	
    public boolean check(int row, int col, Cell[][] cells) {
        for (int i = 1; i <= GameBoardPanel.GRID_SIZE; i++) {
            if (i != col && cells[row][col].number == cells[row][i].number) {
                return false;
            }
            if (i != row && cells[row][col].number == cells[i][col].number) {
                return false;
            }
        }
        for (int r = (row - 1) / 3 * 3 + 1; r <= (row + 2) / 3 * 3; r++) {
            for (int c = (col - 1) / 3 * 3 + 1; c <= (col + 2) / 3 * 3; c++) {
                if (r != row && c != col && cells[row][col].number == cells[r][c].number) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Rule for checking if a number is valid for the specific Cell
 	public boolean numIsValid(int numberIsValid, int numbers[][]) {

 		for (int row = 1; row <= GameBoardPanel.GRID_SIZE; row++) {
 			for (int col = 1; col <= GameBoardPanel.GRID_SIZE; col++) {
 				if (numbers[row][col] == numberIsValid) {
 					return false;
 				}
 			}

 		}
 		return true;
 	}

	public int generateRandomNum() {
		return (int) Math.floor(Math.random() * (Cell.UPPERBOUND - Cell.LOWERBOUND + 1) + Cell.LOWERBOUND);
	}

	public boolean generateRandomIsGiven() {
		Random rd = new Random();
		return rd.nextBoolean();
	}

	
}
