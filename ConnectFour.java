public class ConnectFour {


    public static void main(String[] args) {
  //      play100();
        play();
    }

    private static void play100() {
        int[] winners = new int[3];
        for (int i = 0; i < 10; i++) winners[play()]++;
        System.out.println("WINNERS:");
        for (int i = 0; i < winners.length; i++) System.out.println(i + ": " + winners[i]);
    }

    private static int play() {
        //Create new board object
        int row = 6;
        int col = 7;
        Board board = new Board(row, col);
        CFGUI gui = new CFGUI(board, ChipColor.BLACK, ChipColor.RED);

        //Set player tokens for player 1 and player 2
        board.setPlayerOne('o');
        board.setPlayerTwo('t');
        //Create Player objects
        //Note, the code below works because of the interface Player. All classes that "implement" Player can be
        // typed as Player. Makes switching from Human to AI Players really easy. For a challenge, you might
        // consider:
        //		1. asking the user whether he/she wants to play against a human or a computer
        //		2. implementing multiple AI players (easy, med, hard) that the user can choose to play against

      //  Player p1 = new DumbAIPlayer(1, row, col);
        Player p1 = new HumanPlayer(1, row, col);
        Player p2 = new HumanPlayer(2, row, col); //comment this line when testing AIPlayer

      //  Player p2 = new AIPlayer(2, row, col); //uncomment this line when testing AIPlayer

        //WHILE the board is still playable
        //	get a column to play from player 1
        //	play that token on the board
        //  print the board
        //		has anyone won yet?
        // do the above for player 2

        int finished = board.isFinished();
        Player player = p1;
        while (finished == -1) {
            System.out.println();
            //printBoard(board);
            gui.redraw();
            System.out.println();

            System.out.println("Player " + player.getPlayerID() + "'s Turn!");

            System.out.print("Col: ");
            int playedCol = player.playToken();
            if (!board.play(player.getPlayerID(), playedCol))
                System.out.println("ERROR, ERROR, FIX YOUR CLASS, YOU MORON");

            if (player == p1) player = p2;
            else player = p1;

            player.lastMove(playedCol);

            finished = board.isFinished();
        }
     //   printBoard(board);
        gui.redraw();
        //if (finished == 1 || finished == 2) IO.outputStringAnswer("Player " + finished + " has won!");
       // else IO.outputStringAnswer("There was a tie!");
        gui.gameOver(finished);
        if(GIO.readBoolean("Play Again?")) {
            gui.close();
            play();
        }else
            gui.close();
        return finished;
    }

//    public static int playerTurns(Player p, Board board) {
//        int col;
//        col = p.playToken();
//        board.play(p.getPlayerID(), col);
//        printBoard(board);
//        if (board.isFinished() == 1) {
//            IO.outputStringAnswer("Player " + p.getPlayerID() + " has won!");
//        }
//        return p.getPlayerID();
//    }
    //Print your empty board

//    private int playerTurns(int playerID, Board board) {
//        while (board.canPlay()) {
//            System.out.println();
//            printBoard(board);
//            System.out.println();
//
//            System.out.println("Player " + playerID + "'s Turn!");
//            while (true) {
//                System.out.print("Col: ");
//                if (board.play(playerID, IO.readInt())) break;
//                IO.reportBadInput();
//            }
//            if (playerID == 1) playerID++;
//            else playerID--;
//        }
//        return playerID;
//    }


    private static void printBoard(Board board) {
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

    // Get the status code from the board (isFinished())

    // Print out the results of the grid


}

