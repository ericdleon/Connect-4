import java.util.Random;

public class AIPlayer implements Player {
    private final int playerID;
    private final int row;
    private final int col;
    private int lastMove;
    private AIBoard board;
    private int turns = 0;
//do not change the line above


    //constructor takes the player id for this player, and the number of
    // rows and columns of the board we're playing on
    public AIPlayer(int playerID, int row, int col) {
        this.playerID = playerID;
        this.row = row;
        this.col = col;
        this.board = new AIBoard(row, col);
        //    recursionForLoopOfDeath(1000);
    }

    //used to notify your AI player of the other players last move
    public void lastMove(int c) {
        board.play(2, c);
        this.lastMove = c;
    }

    //returns column of where to play a token
    public int playToken() {
//        turns++;
//        if (turns<4)return 0;
        int c = new Random().nextInt(col);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board.below(i, j, 2) && i - 1 >= 0 && board.grid[i - 1][j] == ' ') c = j;

                if (board.hRight(i, j, 2) && j - 1 >= 0 && board.grid[i][j - 1] == ' ') c = j - 1;
                if (board.hLeft(i, j, 2) && j + 1 < col && board.grid[i][j + 1] == ' ') c = j + 1;


                if (board.diagonalRight(i, j, 2) && j - 1 >= 0 && i - 1 >= 0 && board.grid[i - 1][j - 1] == ' ')
                    c = j  - 1;
                if (board.diagonalLeft(i, j, 2) && j - 1 >= 0 && i - 1 >= 0 && board.grid[i - 1][j - 1] == ' ')
                    c = j + 1;


                if (board.diagonalRight2(i, j, 2) && j + 1 < col && i - 1 >= 0 && board.grid[i - 1][j + 1] == ' ')
                    c = j -1;
                if (board.diagonalLeft2(i, j, 2) && j - 1 >= 0 && i + 1 < row && board.grid[i + 1][j - 1] == ' ')
                    c = j + 1;
            }
        }

        // Pick random if it doesn't work
        while (!board.play(1, c)) {
            c = new Random().nextInt(col);
        }
        return c;
    }

//    public void recursionForLoopOfDeath(int amount){
//
//        //CODE HERE
//        System.out.println(amount+": IJUSTWANTTODIE");
//
//        if (amount>1)recursionForLoopOfDeath(amount-1);
//    }

    //get this player's id
    public int getPlayerID() {
        return this.playerID;
    }

    //resets the state of the player in preparation for a new grid
    public void reset() {
        this.board = new AIBoard(row, col);
    }


    private static void printBoard(AIBoard board) {
        String borders = "+ ";
        for (int i = 0; i < board.grid[0].length; i++) borders += "- ";
        borders += "+";
        System.out.print(borders);
        System.out.println();
        for (int i = 0; i < board.grid.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < board.grid[0].length; j++) System.out.print(board.grid[i][j] + " ");
            System.out.print("|");
            System.out.println();
        }
        System.out.print(borders);
    }

    private class AIBoard {
        //Instance Variables
        private int nRows;
        private int nCols;
        private char player1 = '1';
        private char player2 = '2';
        private int pieceCount;
        public char grid[][];

        //Constructor
        public AIBoard() {//creates a default board of size 7 columns x 6 rows
//        nRows = 6;
//        nCols = 7;
            this(6, 7);
        }

        //Constructor
        public AIBoard(int nRows, int nCols) { //creates a board of size row x col
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

            if (hRight(row, col, 3) || hLeft(row, col, 3)) return true;
            //horizontal////////////////////
//            for (int i = 1; i <= 3; i++) {
//                if (col + i >= grid[0].length || col + i < 0) {
//                    continue;
//                }
//                char cur = grid[row][col + i];
//                if (p == cur) {
//                    count++;
//                } else break;
//            }
//            for (int i = 1; i <= 3; i++) {
//                if (col - i >= grid[0].length || col - i < 0) {
//                    continue;
//                }
//                char cur = grid[row][col - i];
//                if (p == cur) {
//                    count++;
//                } else break;
//            }
//            if (count == 4) {
//                return true;
//            }
            /////////////////////////////////

            //Vertical///////////////////////
            if (below(row, col, 3)) return true;
            /////////////////////////////////
            if (diagonalRight(row, col, 3) || diagonalLeft(row, col, 3) || diagonalRight2(row, col, 3) || diagonalLeft2(row, col, 3)) return true;

            count = 1;
            //Diagonal///////////////////////
//            for (int i = 1; i <= 3; i++) {
//                if (row + i >= grid.length || row + i < 0 || col + i >= grid[0].length || col + i < 0) {
//                    continue;
//                }
//                char cur = grid[row + i][col + i];
//                if (p == cur) {
//                    count++;
//                } else break;
//            }
//            for (int i = 1; i <= 3; i++) {
//                if (row - i >= grid.length || row - i < 0 || col - i >= grid[0].length || col - i < 0) {
//                    continue;
//                }
//                char cur = grid[row - i][col - i];
//                if (p == cur) {
//                    count++;
//                } else break;
//            }
//            if (count == 4) {
//                return true;
//            }
//            count = 1;
//            ///////////////////////////
//            for (int i = 1; i <= 3; i++) {
//                if (row - i >= grid.length || row - i < 0 || col + i >= grid[0].length || col + i < 0) {
//                    continue;
//                }
//                char cur = grid[row - i][col + i];
//                if (p == cur) {
//                    count++;
//                } else break;
//            }
//            for (int i = 1; i <= 3; i++) {
//                if (row + i >= grid.length || row + i < 0 || col - i >= grid[0].length || col - i < 0) {
//                    continue;
//                }
//                char cur = grid[row + i][col - i];
//                if (p == cur) {
//                    count++;
//                } else break;
//            }
//            if (count == 4) {
//                return true;
//            }
            /////////////////////////////////
            return false;
        }

        public boolean below(int row, int col, int amount) {
            if (amount < 1) return true;
            char below = grid[row][col];
            if (below == ' ') return false;
            try {
                if (amount == 1) return grid[row + 1][col] == below;
                else return below(row + 1, col, amount - 1);
            } catch (ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }

        public boolean hRight(int row, int col, int amount) {
            if (amount < 1) return true;
            char hRight = grid[row][col];
            if (hRight == ' ') return false;
            try {
                if (amount == 1) return grid[row][col + 1] == hRight;
                else return hRight(row, col + 1, amount - 1);
            } catch (ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }

        public boolean hLeft(int row, int col, int amount) {
            if (amount < 1) return true;
            char hLeft = grid[row][col];
            if (hLeft == ' ') return false;
            try {
                if (amount == 1) return grid[row][col - 1] == hLeft;
                else return hLeft(row, col - 1, amount - 1);
            } catch (ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }

        public boolean diagonalRight(int row, int col, int amount) {
            if (amount < 1) return true;
            char dRight = grid[row][col];
            if (dRight == ' ') return false;
            try {
                if (amount == 1) return grid[row - 1][col - 1] == dRight;
                else return diagonalRight(row - 1, col - 1, amount - 1);
            } catch (ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }

        public boolean diagonalLeft(int row, int col, int amount) {
            if (amount < 1) return true;
            char dLeft = grid[row][col];
            if (dLeft == ' ') return false;
            try {
                if (amount == 1) return grid[row + 1][col + 1] == dLeft;
                else return diagonalLeft(row + 1, col + 1, amount - 1);
            } catch (ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }

        public boolean diagonalRight2(int row, int col, int amount) {
            if (amount < 1) return true;
            char dRight2 = grid[row][col];
            if (dRight2 == ' ') return false;
            try {
                if (amount == 1) return grid[row + 1][col - 1] == dRight2;
                else return diagonalRight2(row + 1, col - 1, amount - 1);
            } catch (ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }

        public boolean diagonalLeft2(int row, int col, int amount) {
            if (amount < 1) return true;
            char dLeft2 = grid[row][col];
            if (dLeft2 == ' ') return false;
            try {
                if (amount == 1) return grid[row - 1][col + 1] == dLeft2;
                else return diagonalLeft2(row - 1, col + 1, amount - 1);
            } catch (ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }

        public AIBoard clone() {
            AIBoard board = new AIBoard(row, col);
            for (int i = 0; i < getNumRows(); i++)
                for (int j = 0; j < getNumCols(); j++)
                    board.grid[i][j] = grid[i][j];
            return board;
        }


    }

}
