import java.util.Scanner;

public class Game {

    // The following five constants were defined in the starter code (kt54)

    private static String WHITEPLAYS_MSG = "White plays. Enter move:";
    private static String BLACKPLAYS_MSG = "Black plays. Enter move:";
    private static String ILLEGALMOVE_MSG = "Illegal move!";
    private static String WHITEWINS_MSG = "White wins!";
    private static String BLACKWINS_MSG = "Black wins!";

    private Board gameBoard;
    private char piece;
    private int[] index;

    // Minimal constructor. Expand as needed (kt54)

    public Game() {

        gameBoard = new Board();
    }

    // Build on this method to implement game logic.

    public void play() {
        Scanner reader = new Scanner(System.in);
        gameBoard = new Board();
        boolean done = false;
        boolean isBlackTurn = true;
        String command = "";

        do {
            gameBoard.printBoard();
            if (isBlackTurn) {
                System.out.println(BLACKPLAYS_MSG);
            }
            else {
                System.out.println(WHITEPLAYS_MSG);
            }

            String pos1 = reader.nextLine().trim();
            checkForQuit(pos1);

            String pos2 = reader.nextLine().trim();
            checkForQuit(pos2);

            if (pos1 != pos2 && checkValidInput(pos1) && checkValidInput(pos2)){
                if (isBlackTurn) {
                    if(gameBoard.isPieceBlack(gameBoard.getColIndex(pos1), gameBoard.getRowIndex(pos1))){
                        if(checkForKing(pos1, gameBoard.getBlackMan())){
                            gameBoard.isMoveLegal(pos1, pos2, gameBoard.getBlackKing());
                            gameBoard.makeMove(pos1, pos2, gameBoard.getBlackKing());
                            
                        }
                    else if (gameBoard.isMoveLegal(pos1, pos2, gameBoard.getBlackMan())) {
                            gameBoard.makeMove(pos1, pos2, gameBoard.getBlackMan());
                    }
                        else {
                            System.out.println(ILLEGALMOVE_MSG);
                            continue; // leave loop here and start next iteration
                        }     
                    }
                    else{
                        System.out.println(ILLEGALMOVE_MSG);
                        continue;
                    }
            }
            else{ //white turn
                    if(gameBoard.isPieceWhite(gameBoard.getColIndex(pos1), gameBoard.getRowIndex(pos1))){
                        if(checkForKing(pos1, gameBoard.getWhiteMan())){
                            gameBoard.isMoveLegal(pos1, pos2, gameBoard.getWhiteKing());
                            gameBoard.makeMove(pos1, pos2, gameBoard.getWhiteKing());    
                        }
                        else if (gameBoard.isMoveLegal(pos1, pos2, gameBoard.getWhiteMan())) {
                                gameBoard.makeMove(pos1, pos2, gameBoard.getWhiteMan());
                        }                     
                        else {
                            System.out.println(ILLEGALMOVE_MSG);
                            continue;
                        }
                    }
                    else{
                        System.out.print(ILLEGALMOVE_MSG);
                        continue;
                    }
                }
            }
            else {
                System.out.println(ILLEGALMOVE_MSG);
                continue;
            }

            index = gameBoard.checkForKing();

            if(index[0] != -1){
                this.piece = gameBoard.becomeKing(index);
            }
            

            // This is just demonstration code, so we immediately let white win
            // to avoid unnecessary violence.
           
            if(isBlackTurn){
                if(gameBoard.checkGameOver(gameBoard.getWhiteMan())){
                    System.out.println(BLACKWINS_MSG);
                    done = true;
                }
            }
            else {
                if(gameBoard.checkGameOver(gameBoard.getBlackMan())){
                System.out.println(WHITEWINS_MSG);
                done = true;
                }
            }

            isBlackTurn = !isBlackTurn;
            
        } while (!done);
    }

    public void checkForQuit(String input) {
        if (input.equals("quit")) {
            System.exit(0);
        }
    }

    // sees if cooridnates are on board

    public boolean checkValidInput(String input) {
        if (input.length() == 2) {
            int colIndex = gameBoard.getColIndex(input);
            int rowIndex = gameBoard.getRowIndex(input);
            if (colIndex >= 0 && colIndex < gameBoard.getBoardSize()) {
                if (rowIndex >= 0 && rowIndex < gameBoard.getBoardSize()) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean checkForKing(String pos2, char piece){
        if(piece == gameBoard.getBlackMan()){
            if(gameBoard.getRowIndex(pos2) == gameBoard.getBoardSize() - 1){
                this.piece = gameBoard.getBlackKing();
                return true;
            }
        }
        else if(piece == gameBoard.getWhiteMan()){
            if(gameBoard.getRowIndex(pos2) == 0){
                this.piece = gameBoard.getWhiteKing();
                return true;
            }
        }return false;
    }
    
}
