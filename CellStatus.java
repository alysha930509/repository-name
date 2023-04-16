package gui;

public enum CellStatus {
    GIVEN,//clue,no need guess
    TO_GUESS,//need to guess-not attempted
    CORRECT_GUESS,//need to guess-correct guess
    WRONG_GUESS//need to guess-wrong guess
}
