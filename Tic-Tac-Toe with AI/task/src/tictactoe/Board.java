package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Board {
    char[][] board;
    int x = 0;
    int o = 0;
    int empty = 9;
    boolean isOWin;
    boolean isXWin;
    boolean isDraw;

    public void setBoard() {

        this.board = new char[3][3];

        for (int x = 0; x < this.board.length; x++) {
            for (int y = 0; y < this.board.length; y++) {
                this.board[x][y] = ' ';
            }
        }
        this.printBoard();
    }

    public void printBoard() {
        System.out.println("---------");
        for (char[] chars : this.board) {
            System.out.print("| ");
            for (int y = 0; y < this.board.length; y++) {
                System.out.print(chars[y] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public void placeNext() {
        boolean correctInput = false;

        while (!correctInput) {
            System.out.println("Enter the coordinates: ");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int x = scanner.nextInt() - 1;
                int y = scanner.nextInt() - 1;
                if (x < 0 || x > 2 || y < 0 || y > 2) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else {
                    if (this.board[x][y] == 'X' || this.board[x][y] == 'O') {
                        System.out.println("This cell is occupied! Choose another one!");
                    } else {
                        if (this.x == this.o) {
                            this.board[x][y] = 'X';
                            this.x++;
                        } else {
                            this.board[x][y] = 'O';
                            this.o++;
                        }
                        this.empty--;
                        correctInput = true;
                    }
                }
            } else {
                System.out.println("You should enter numbers!");
            }
            this.printBoard();
            this.checkWin();
            this.writeResult();
        }
    }

    public void checkWin() {
        int checkRow;
        for (int i = 0; i < 3; i++) {
            checkRow = 0;
            for (int j = 0; j < 3; j++) {
                checkRow += this.board[i][j];
                if (checkRow == 264) {
                    isXWin = true;
                } else if (checkRow == 237) {
                    isOWin = true;
                }
            }
        }

        int checkColumn;
        if (!isOWin && !isXWin) {
            for (int i = 0; i < 3; i++) {
                checkColumn = 0;
                for (int j = 0; j < 3; j++) {
                    checkColumn += this.board[j][i];
                    if (checkColumn == 264) {
                        isXWin = true;
                    } else if (checkColumn == 237) {
                        isOWin = true;
                    }
                }
            }
        }

        int checkDiagonal = 0;
        if (!isOWin && !isXWin) {
            for (int i = 0; i < 3; i++) {
                checkDiagonal += this.board[i][i];
                if (checkDiagonal == 264) {
                    isXWin = true;
                } else if (checkDiagonal == 237) {
                    isOWin = true;
                }
            }
        }

        int checkOtherDiagonal = 0;
        if (!isOWin && !isXWin) {
            for (int i = 0; i < 3; i++) {
                checkOtherDiagonal += this.board[i][2 - i];
                if (checkOtherDiagonal == 264) {
                    isXWin = true;
                } else if (checkOtherDiagonal == 237) {
                    isOWin = true;
                }
            }
        }

        if ((!isOWin && !isXWin) && this.empty == 0) {
            this.isDraw = true;
        }
    }

    public void writeResult() {
        if (Math.abs(this.x - this.o) > 1 || ((isOWin) && (isXWin))) {
            System.out.println("Impossible");
        } else if ((!isOWin && !isXWin) && this.empty > 0) {
            System.out.print("");
        } else if ((!isOWin && !isXWin) && this.empty == 0) {
            this.isDraw = true;
            System.out.println("Draw");
        } else if (isOWin) {
            System.out.println("O wins");
        } else if (isXWin) {
            System.out.println("X wins");
        }
    }

    public void moveEasyAI() {
        Random random = new Random();
        boolean validMove = false;
        System.out.println("Making move level \"easy\"");
        while (!validMove) {
            int x = random.nextInt(3);
            int y = random.nextInt(3);
            if (board[x][y] == ' ') {
                if (this.x == this.o) {
                    board[x][y] = 'X';
                    this.x++;
                } else {
                    board[x][y] = 'O';
                    this.o++;
                }
                this.empty--;
                validMove = true;
            }
        }
        this.printBoard();
        this.checkWin();
        this.writeResult();
    }

    public void moveMediumAI() {
        System.out.println("Making move level \"medium\"");

        char c = this.x == this.o ? 'X' : 'O';
        int target = this.x == this.o ? 2 * 'X' + ' ' : 2 * 'O' + ' ';
        Random random = new Random();

        boolean validMove = checkIfWinnable(c, target);


        if (validMove) {
            if (c == 'X') {
                this.x++;
            } else {
                this.o++;
            }
            this.empty--;
        } else {
            target = this.x == this.o ? 2 * 'O' + ' ' : 2 * 'X' + ' ';
            validMove = checkIfWinnable(c, target);
            if (validMove) {
                if (c == 'X') {
                    this.x++;
                } else {
                    this.o++;
                }
                this.empty--;
            } else {
                while (!validMove) {
                    int x = random.nextInt(3);
                    int y = random.nextInt(3);
                    if (board[x][y] == ' ') {
                        if (this.x == this.o) {
                            board[x][y] = 'X';
                            this.x++;
                        } else {
                            board[x][y] = 'O';
                            this.o++;
                        }
                        this.empty--;
                        validMove = true;
                    }
                }
            }
        }
        this.printBoard();
        this.checkWin();
        this.writeResult();
    }

    public boolean checkIfWinnable(char c, int target) {

        int x = 0;
        int y = 0;

            int checkRow;
            for (int i = 0; i < 3; i++) {
                checkRow = 0;
                for (int j = 0; j < 3; j++) {
                    checkRow += this.board[i][j];
                    if (this.board[i][j] == ' ') {
                        x = i;
                        y = j;
                    }
                    if (checkRow == target) {
                        this.board[x][y] = c;
                        return true;
                    }
                }
            }

            int checkColumn;
            for (int i = 0; i < 3; i++) {
                checkColumn = 0;
                for (int j = 0; j < 3; j++) {
                    checkColumn += this.board[j][i];
                    if (this.board[j][i] == ' ') {
                        x = j;
                        y = i;
                    }
                    if (checkColumn == target) {
                        this.board[x][y] = c;
                        return true;
                    }
                }
            }

            int checkDiagonal = 0;
            for (int i = 0; i < 3; i++) {
                checkDiagonal += this.board[i][i];
                if (this.board[i][i] == ' ') {
                    x = i;
                    y = i;
                }
                if (checkDiagonal == target) {
                    this.board[x][y] = c;
                    return true;
                }
            }

            int checkOtherDiagonal = 0;
            for (int i = 0; i < 3; i++) {
                checkOtherDiagonal += this.board[i][2 - i];
                if (this.board[i][2 - i] == ' ') {
                    x = i;
                    y = 2 - i;
                }
                if (checkOtherDiagonal == target) {
                    this.board[x][y] = c;
                    return true;
                }
            }

            return false;
    }

    public void bestMove() {
        System.out.println("Making move level \"hard\"");
        char ai = this.o == this.x ? 'X' : 'O';
        char human = ai == 'X' ? 'O' : 'X';
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (this.board[x][y] == ' ') {
                    board[x][y] = ai;
                    empty--;
                    int score = minimax(this.board, 0, false, ai, human);
                    board[x][y] = ' ';
                    empty++;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = x;
                        bestMove[1] = y;
                    }
                }
            }
        }
        board[bestMove[0]][bestMove[1]] = ai;
        if (ai == 'X') {
            this.x++;
        } else {
            this.o++;
        }
        this.empty--;

        this.printBoard();
        this.checkWin();
        this.writeResult();
    }

    public int minimax(char[][] board, int depth, boolean isMaximizing, char ai, char human) {
        this.checkWin();

        if (ai == 'X') {
            if (this.isXWin) {
                this.isXWin = false;
                return 10;
            } else if (this.isOWin) {
                this.isOWin = false;
                return -10;
            } else if (this.isDraw) {
                this.isDraw = false;
                return 0;
            }
        } else if (ai == 'O') {
            if (this.isXWin) {
                this.isXWin = false;
                return -10;
            } else if (this.isOWin) {
                this.isOWin = false;
                return 10;
            } else if (this.isDraw) {
                this.isDraw = false;
                return 0;
            }
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    if (this.board[x][y] == ' ') {
                        board[x][y] = ai;
                        empty--;
                        int score = minimax(board, depth + 1, false, ai, human);
                        board[x][y] = ' ';
                        empty++;
                        if (score > bestScore) {
                            bestScore = score;
                        }
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    if (this.board[x][y] == ' ') {
                        board[x][y] = human;
                        empty--;
                        int score = minimax(board, depth + 1, true, ai, human);
                        board[x][y] = ' ';
                        empty++;
                        if (score < bestScore) {
                            bestScore = score;
                        }
                    }
                }
            }
        }
        return bestScore;
    }
}
