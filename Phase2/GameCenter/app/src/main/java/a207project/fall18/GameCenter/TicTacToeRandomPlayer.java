package a207project.fall18.GameCenter;

 class TicTacToeRandomPlayer {

     private TicTacToeBoardManager ticTacToeBoardManager;

     /**
      * @param ticTacToeBoardManager a ticTacToeBoardManager
      */
     void setTicTacToeBoardManager(TicTacToeBoardManager ticTacToeBoardManager) {
         this.ticTacToeBoardManager = ticTacToeBoardManager;
     }

     /**
      * @param ticTacToeBoardManager a ticTacToeBoardManager
      */
    TicTacToeRandomPlayer(TicTacToeBoardManager ticTacToeBoardManager) {
        this.ticTacToeBoardManager = ticTacToeBoardManager;
    }

     /**
      * @param player the player
      * @return an random int of movement
      */
     int getMove(int player) {
        return ticTacToeBoardManager.getTicTacToeBoard().getRandomEmpty();
    }
}
