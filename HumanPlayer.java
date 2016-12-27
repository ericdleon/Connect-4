public class HumanPlayer implements Player {
//do not change the line above	

    //Define your fields here
    private int playerID;
    private int row;
    private int col;
    private int lastMove;
    public int[] nRow;


    //constructor takes the player id for this player, and the number of
    // rows and columns of the board we're playing on
    public HumanPlayer(int playerID, int row, int col) {
        this.playerID = playerID;
        this.row = row;
        this.col = col;
        nRow = new int[col];
    }

    //used to notify your AI player of the other players last move
    public void lastMove(int c) {
        this.lastMove = c;
        nRow[c]++;
    }

    //returns column of where to play a token
    public int playToken() {
        int cols = GIO.readInt("Player"+ " "+ playerID+"'s" + " " + "Turn");
        while (cols < 0 || cols > col - 1|| nRow[cols] >= row) {
           // IO.reportBadInput();
            GIO.displayMessage("Bad Input!");
            cols = GIO.readInt("Player"+ " "+ playerID+"'s" + " " + "Turn");
        }
        nRow[cols]++;
        return cols;
    }

    //get this player's id
    public int getPlayerID() {
        return this.playerID;
    }

    //resets the state of the player in preparation for a new grid
    public void reset() {
        this.nRow = new int[col];
    }


}