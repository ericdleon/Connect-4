public class Board {
    //Instance Variables
    private int nRows;
    private int nCols;
    private char player1 = ' ';
    private char player2 = ' ';
    private int pieceCount;
    public char grid[][];

    //Constructor
    public Board() {//creates a default board of size 7 columns x 6 rows
//        nRows = 6;
//        nCols = 7;
        this(6, 7);
    }

    //Constructor
    public Board(int nRows, int nCols) { //creates a board of size row x col
        this.nCols = nCols;
        this.nRows = nRows;
        grid = new char[nRows][nCols];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    //Getters
    public int getNumRows() {//returns the number of rows in board
        return nRows;
    }


    public int getNumCols() {//returns the number of cols in board
        return nCols;
    }


    public char getPlayerOne() {//returns char representing player 1
        return player1;
    }


    public char getPlayerTwo() {//returns char representing player 2
        return player2;
    }

    //Setters
    public void setPlayerOne(char o) {//sets char representing player 1
        player1 = o;
    }


    public void setPlayerTwo(char t) {//sets char representing player 2
        player2 = t;
    }

    //Getter
    public char getToken(int row, int col) {//returns the char representing token at location row,col, returns '\0' if indices are invalid
        if (row > nRows - 1 || col > nCols - 1 || row < 0 || col < 0) {
            return '\0';
        } else
            return grid[row][col];
    }

//    public int getPieceCount() {
//        return pieceCount;
//    }


    public boolean canPlay() { //returns true if a token is still able to placed onto the board, false otherwise
        return isFinished() == -1;
    }


    public boolean play(int p, int c) {//places the appropriate token for player p in column c. returns true if successful, false otherwise.
        if (c >= grid[0].length || c < 0)
            return false;
        if (p == 1) {
            for (int i = nRows - 1; i >= 0; i--) {
                if (grid[i][c] == ' ') {
                    grid[i][c] = player1;
//                    pieceCount++;
                    return true;
                }
            }
        } else if (p == 2) {
            for (int i = nRows - 1; i >= 0; i--) {
                if (grid[i][c] == ' ') {
                    grid[i][c] = player2;
//                    pieceCount++;
                    return true;
                }
            }
        }
        return false;
    }


    public int isFinished() {//returns either the ID of the player who has won (1 or 2) OR 0 if the grid has ended in a tie OR -1 if nobody has won yet

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (grid[i][j] == ' ') {
                    continue;
                }
                if (fourInARow(i, j)) {
                    if (grid[i][j] == getPlayerOne()) {
                        return 1;
                    } else return 2;
                }
            }
        }
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (grid[i][j] == ' ') {
                    return -1;
                }
            }
        }


        return 0;
    }

    public boolean fourInARow(int row, int col) {
        int count = 1;
        char p = grid[row][col];
        //horizontal////////////////////
        for (int i = 1; i <= 3; i++) {
            if (col + i >= grid[0].length || col + i < 0) {
                continue;
            }
            char cur = grid[row][col + i];
            if (p == cur) {
                count++;
            } else break;
        }
        for (int i = 1; i <= 3; i++) {
            if (col - i >= grid[0].length || col - i < 0) {
                continue;
            }
            char cur = grid[row][col - i];
            if (p == cur) {
                count++;
            } else break;
        }
        if (count == 4) {
            return true;
        }
        /////////////////////////////////

        count = 1;
        //Vertical///////////////////////
        for (int i = 1; i <= 3; i++) {
            if (row + i >= grid.length || row + i < 0) {
                continue;
            }
            char cur = grid[row + i][col];
            if (p == cur) {
                count++;
            } else break;
        }
        for (int i = 1; i <= 3; i++) {
            if (row - i >= grid.length || row - i < 0) {
                continue;
            }
            char cur = grid[row - i][col];
            if (p == cur) {
                count++;
            } else break;
        }

        if (count == 4) {
            return true;
        }
        /////////////////////////////////

        count = 1;
        //Diagonal///////////////////////
        for (int i = 1; i <= 3; i++) {
            if (row + i >= grid.length || row + i < 0 || col + i >= grid[0].length || col + i < 0) {
                continue;
            }
            char cur = grid[row + i][col + i];
            if (p == cur) {
                count++;
            } else break;
        }
        for (int i = 1; i <= 3; i++) {
            if (row - i >= grid.length || row - i < 0 || col - i >= grid[0].length || col - i < 0) {
                continue;
            }
            char cur = grid[row - i][col - i];
            if (p == cur) {
                count++;
            } else break;
        }
        if (count == 4) {
            return true;
        }
        count = 1;
        ///////////////////////////
        for (int i = 1; i <= 3; i++) {
            if (row - i >= grid.length || row - i < 0 || col + i >= grid[0].length || col + i < 0) {
                continue;
            }
            char cur = grid[row - i][col + i];
            if (p == cur) {
                count++;
            } else break;
        }
        for (int i = 1; i <= 3; i++) {
            if (row + i >= grid.length || row + i < 0 || col - i >= grid[0].length || col - i < 0) {
                continue;
            }
            char cur = grid[row + i][col - i];
            if (p == cur) {
                count++;
            } else break;
        }
        if (count == 4) {
            return true;
        }
        /////////////////////////////////
        return false;
    }
}