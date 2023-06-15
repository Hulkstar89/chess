package chess;

import java.io.PrintStream;

import java.nio.charset.Charset;

public class testing {
    //
    public static void main(String[] args) {

        // System.out.println("\u0005");
        
        // System.out.println("♔");
        // char unicodeChar = '\u03A9'; // The Unicode code for the Greek capital letter Omega
        
//         System.out.println("Unicode character: " + unicodeChar);
         System.out.println("White King: " + '\u2654');
         System.out.println("White Queen: " + '\u2655');
         System.out.println("White Rook: " + '\u2656');
         System.out.println("White Bishop: " + '\u2657');
         System.out.println("White Knight: " + '\u2658');
         System.out.println("White Pawn: " + '\u2659');
         System.out.println("Black King: " + '\u265A');
         System.out.println("Black Queen: " + '\u265B');
         System.out.println("Black Rook: " + '\u265C');
         System.out.println("Black Bishop: " + '\u265D');
         System.out.println("Black Knight: " + '\u265E');
         System.out.println("Black Pawn: " + '\u265F');

//         PrintWriter printWriter = new PrintWriter(System.out,true);
// char aa = '\u0905';
// printWriter.println("aa = " + aa);  
//try {
//     Charset utf8 = Charset.forName("UTF-8");
//        Charset def = Charset.defaultCharset();
//
//        String charToPrint = "\u265F";
//
//        byte[] bytes = charToPrint.getBytes("UTF-8");
//        String message = new String(bytes , def.name());
//
//        PrintStream printStream = new PrintStream(System.out, true, utf8.name());
//        printStream.println(message); // should print your character
//} catch (Exception e) {
//    // TODO: handle exception
//}

       
    }
}
