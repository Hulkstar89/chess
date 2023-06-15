package chess;

public class ui {
    public static void main(String[] args) {
        //chcp 65001
        board game = new board();
        do {
            System.out.println("please enter the move for white");
            game.moveByMove("white");
       
          
            
        } while (true);
       
    }
   
}
