package edu.hw1;

public class Task8 {
    private Task8() {
    }

    private static final int BOARD_SIZE = 8;
    private static final int[][] KNIGHT_MOVES = {
        {2, 1}, {2, -1},
        {-2, 1}, {-2, -1},
        {1, 2}, {1, -2},
        {-1, 2}, {-1, -2}
    };

    public static boolean knightBoardCapture(int[][] board) {

        if (board == null || board.length != BOARD_SIZE) {
            return false;
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i] == null || board[i].length != BOARD_SIZE) {
                return false;
            }
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 1) {
                    if (canCaptureOtherKnight(board, i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean canCaptureOtherKnight(int[][] board, int row, int col) {
        for (int[] move : KNIGHT_MOVES) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (isWithinBoard(newRow, newCol)) {
                if (board[newRow][newCol] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isWithinBoard(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }
}
