package chess;

import java.util.HashMap;
import java.util.Scanner;

public class board {
    public HashMap<String, pieces> pieces = new HashMap<String, pieces>();
    private Scanner in = new Scanner(System.in);
    private String userInput;
    public String PGN = "";

    public void moveWhite() {

        AskUserForMoveByMove();

        displayBoard();

    }

    public void AskUserForMoveByMove() {

        userInput = in.nextLine();
        whatPieceDoTheyWantToMoveWhite();

    }

    public void whatPieceDoTheyWantToMoveWhite() {
        char[] userInputArr = userInput.toCharArray();
        if (userInput.length() == 2) {
            // its a pawn move
            System.out.println("pawn move");
            for (int j = 1; j <= 8; j++) {
                try {
                    pawnValidMoves("white pawn - " + j);
                } catch (Exception e) {

                }

            }
            for (int j = 1; j <= 8; j++) {
                try {
                    if (pieces.get("white pawn - " + j).validMoves.contains(userInput)) {
                        pieces.get("white pawn - " + j).setLocation(userInput);
                        pieces.get("white pawn - " + j).moreThenOnce = true;
                    }
                } catch (Exception e) {

                }

            }

        }
        char[] lettersArr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };

        // pawn capture
        if (userInput.length() == 4 && userInputArr[1] == 'x') {
            for (int whatPawnIsMoving = 0; whatPawnIsMoving < lettersArr.length; whatPawnIsMoving++) {
                if (userInputArr[0] == lettersArr[whatPawnIsMoving]) {
                    // now we know what pawn is capturing
                    System.out.println("pawn capture");
                    // so then we need to find the pawn in the hashmap
                    int locationInHashmap;
                    for (locationInHashmap = 1; locationInHashmap <= 8; locationInHashmap++) {
                        // find the pawn in the hashmap
                        if (pieces.get("white pawn - " + locationInHashmap).location.substring(0, 1)
                                .equals(lettersArr[whatPawnIsMoving])) {

                            pawnValidMoves("white pawn - " + locationInHashmap);
                        }

                    }

                    // matcch userinput to the pawns valid move
                    for (int i = 0; i < 3; i++) {

                        if (pieces.get("white pawn - " + locationInHashmap).validMoves.get(i).equals(userInput)
                                && !whoIsAtLocationBackend(userInput.substring(2)).equals("")) {
                            removePiece();
                            pieces.get("white pawn - " + locationInHashmap).setLocation(userInput.substring(2));
                            pieces.get("white pawn - " + locationInHashmap).moreThenOnce = true;
                        }

                    }

                }

            }

        }
        // big peice move
        if (userInput.length() == 3) {
            switch (userInputArr[0]) {
                case 'K':
                    System.out.println("king move");
                    // find valid move for piece
                    kingValidMoves("white king");
                    // check if a valid move matches user input
                    for (int i = 0; i < 8; i++) {
                        try {
                            if (pieces.get("white king").validMoves.get(i).equals(userInput.substring(1))) {
                                // move the piece
                                pieces.get("white king").setLocation(userInput.substring(1));
                                PGN += userInput;
                            }
                        } catch (Exception e) {

                        }

                    }

                    break;
                case 'Q':
                    System.out.println("queen move");
                    // find valid move for piece
                    queenValidMove("white queen");
                    // check if a valid move matches user input
                    for (int i = 0; i < pieces.get("white queen").validMoves.size(); i++) {
                        if (pieces.get("white queen").validMoves.get(i)
                                .equals(userInput.substring(1))) {
                            // move the piece
                            pieces.get("white queen").setLocation(userInput.substring(1));
                        }
                    }
                    break;
                case 'N':
                    System.out.println("knight move");
                    // find valid move for piece
                    // check if a valid move matches user input
                    // move the piece
                    break;
                case 'B':
                    System.out.println("bishop move");
                    // find valid move for piece
                    for (int i = 0; i < lettersArr.length; i++) {
                        try {
                            bishopValidMove("white bishop - 1");
                            bishopValidMove("white bishop - 2");
                        } catch (Exception e) {

                        }
                    }

                    // check if a valid move matches user input
                    for (int j = 1; j < 3; j++) {
                        try {
                            for (int i = 0; i < pieces.get("white bishop - " + j).validMoves.size(); i++) {
                                if (pieces.get("white bishop - " + j).validMoves.get(i)
                                        .equals(userInput.substring(1))) {
                                    // move the piece
                                    pieces.get("white bishop - " + j).setLocation(userInput.substring(1));
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }

                    break;
                case 'R':
                    System.out.println("rook move");
                    // find valid move for piece

                    rookValidMove("white rook - 1");
                    rookValidMove("white rook - 2");

                    // check if a valid move matches user input
                    for (int i = 0; i < pieces.get("white rook - 1").validMoves.size(); i++) {
                        if (pieces.get("white rook - 1").validMoves.get(i).equals(userInput.substring(1))) {
                            // move the piece
                            pieces.get("white rook - 1").setLocation(userInput.substring(1));
                        }
                    }
                    for (int i = 0; i < pieces.get("white rook - 2").validMoves.size(); i++) {
                        if (pieces.get("white rook - 2").validMoves.get(i).equals(userInput.substring(1))) {
                            // move the piece
                            pieces.get("white rook - 2").setLocation(userInput.substring(1));
                        }
                    }

                    break;
                default:
                    break;
            }
        }

        // big piece capture
        if (userInput.length() == 4 && userInputArr[1] == 'x') {
            switch (userInputArr[0]) {
                case 'K':
                    System.out.println("king capture");
                    // find valid move for piece
                    // check if a valid move matches user input
                    // remove the captured piece
                    // move the piece
                    break;
                case 'Q':
                    System.out.println("queen capture");
                    // find valid move for piece
                    // check if a valid move matches user input
                    // remove the captured piece
                    // move the piece
                    break;
                case 'N':
                    System.out.println("knight capture");
                    // find valid move for piece
                    // check if a valid move matches user input
                    // remove the captured piece
                    // move the piece
                    break;
                case 'B':
                    System.out.println("bishop capture");
                    // find valid move for piece
                    // check if a valid move matches user input
                    // remove the captured piece
                    // move the piece
                    break;
                case 'R':
                    System.out.println("its the rook");
                    // witch rook
                    // find valid move for piece
                    // check if a valid move matches user input
                    // remove the captured piece
                    // move the piece
                    break;
                default:
                    break;
            }
            // if two pieces can move to the same location
            /*
             * if (userInput.length() == 4) {
             * switch (userInputArr[0]) {
             * case 'K':
             * System.out.println("king capture");
             * // find valid move for piece
             * // check if a valid move matches user input
             * // remove the captured piece
             * // move the piece
             * break;
             * case 'Q':
             * System.out.println("queen capture");
             * // find valid move for piece
             * // check if a valid move matches user input
             * // remove the captured piece
             * // move the piece
             * break;
             * case 'N':
             * System.out.println("knight capture");
             * // find valid move for piece
             * // check if a valid move matches user input
             * // remove the captured piece
             * // move the piece
             * break;
             * case 'B':
             * System.out.println("bishop capture");
             * // find valid move for piece
             * // check if a valid move matches user input
             * // remove the captured piece
             * // move the piece
             * break;
             * case 'R':
             * System.out.println("its the rook");
             * // witch rook
             * // find valid move for piece
             * // check if a valid move matches user input
             * // remove the captured piece
             * // move the piece
             * break;
             * default:
             * break;
             * }
             */
        }
    }

    private void queenValidMove(String name) {
        pieces.get(name).validMoves.clear();
        char[] locationArr = pieces.get(name).location.toCharArray();
        char[] lettersArr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        int numberVersionOfLetter = 100;
        for (int i = 0; i < lettersArr.length; i++) {
            if (locationArr[0] == lettersArr[i]) {
                numberVersionOfLetter = i;
            }
        }
        int numberlocation = Integer.parseInt(Character.toString(locationArr[1]));
        // String letterLocation = Character.toString(locationArr[0]);
        // up and right
        for (int j = 1, i = numberVersionOfLetter; j <= 8; i++, j++) {
            int tempNumberLocation = numberlocation + j;

            String tempLetterLocation = increaseLetter(lettersArr[i]);

            // ensure locaiton is on the borad
            if (tempNumberLocation > 8 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } else {
                // someone is there and stop looking in this direction
                break;
            }

        }
        // down and right
        for (int j = 1, i = numberVersionOfLetter; j <= 8; i--, j++) {
            int tempNumberLocation = numberlocation - j;

            String tempLetterLocation = increaseLetter(lettersArr[i]);

            // ensure locaiton is on the borad
            if (tempNumberLocation < 1 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } else {
                // someone is there and stop looking in this direction
                break;
            }

        }

        // down and left
        for (int j = 1, i = numberVersionOfLetter; j <= 8; i--, j++) {
            int tempNumberLocation = numberlocation - j;

            String tempLetterLocation = decreaseLetter(lettersArr[i]);

            // ensure locaiton is on the borad
            if (tempNumberLocation < 1 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } else {
                // someone is there and stop looking in this direction
                break;
            }

        }

        // up and left
        for (int j = 1, i = numberVersionOfLetter; j <= 8; i--, j++) {
            int tempNumberLocation = numberlocation + j;

            String tempLetterLocation = decreaseLetter(lettersArr[i]);

            // ensure locaiton is on the borad
            if (tempNumberLocation > 8 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } else {
                // someone is there and stop looking in this direction
                break;
            }

        }






        String letterLocation = Character.toString(locationArr[0]);

         // up

         for (int i = numberlocation; i <= 8; i++) {

            // make sure locaiton in empty
            String tempLocation = letterLocation + i;
            if (tempLocation.equals(pieces.get(name).location)) {
                continue;
            }

            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                pieces.get(name).validMoves.add(tempLocation);
            } else {
                
                break;
            }

        }

        // down
        for (int i = numberlocation; i >= 1; i--) {

            // make sure locaiton in empty
            String tempLocation = letterLocation + i;
            if (tempLocation.equals(pieces.get(name).location)) {
                continue;
            }
            //ensure that the location is on the bou
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                pieces.get(name).validMoves.add(tempLocation);
            } else {
                break;
            }

        }

        // right
       
        for (int letterLocationInt = 0; letterLocationInt < 8; letterLocationInt++) {
            if (locationArr[0] == lettersArr[letterLocationInt]) {
                for (int i = letterLocationInt; i <= 7; i++) {
                    
                    String tempLocation = increaseLetter(lettersArr[i]) + numberlocation;
                    // check empty

                    if (whoIsAtLocationBackend(tempLocation).equals("")) {
                        pieces.get(name).validMoves.add(tempLocation);
                    } else {
                        break;
                    }
                    
                }
            }
        }

        // left
        for (int letterLocationInt = 0; letterLocationInt < 8; letterLocationInt++) {
            if (locationArr[0] == lettersArr[letterLocationInt]) {
                for (int i = letterLocationInt; i >= 1; i--) {

                    String tempLocation = decreaseLetter(lettersArr[i]) + numberlocation;
                    // check empty

                    if (whoIsAtLocationBackend(tempLocation).equals("")) {
                        pieces.get(name).validMoves.add(tempLocation);
                    } else {
                        break;
                    }

                }
            }
        }
    }

    private void bishopValidMove(String name) {
        pieces.get(name).validMoves.clear();
        char[] locationArr = pieces.get(name).location.toCharArray();
        char[] lettersArr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        int numberVersionOfLetter = 100;
        for (int i = 0; i < lettersArr.length; i++) {
            if (locationArr[0] == lettersArr[i]) {
                numberVersionOfLetter = i;
            }
        }
        int numberlocation = Integer.parseInt(Character.toString(locationArr[1]));
        // String letterLocation = Character.toString(locationArr[0]);
        // up and right
        for (int j = 1, i = numberVersionOfLetter; j <= 8; i++, j++) {
            int tempNumberLocation = numberlocation + j;

            String tempLetterLocation = increaseLetter(lettersArr[i]);

            // ensure locaiton is on the borad
            if (tempNumberLocation > 8 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } else {
                // someone is there and stop looking in this direction
                break;
            }

        }
        // down and right
        for (int j = 1, i = numberVersionOfLetter; j <= 8; i--, j++) {
            int tempNumberLocation = numberlocation - j;

            String tempLetterLocation = increaseLetter(lettersArr[i]);

            // ensure locaiton is on the borad
            if (tempNumberLocation < 1 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } else {
                // someone is there and stop looking in this direction
                break;
            }

        }

        // down and left
        for (int j = 1, i = numberVersionOfLetter; j <= 8; i--, j++) {
            int tempNumberLocation = numberlocation - j;

            String tempLetterLocation = decreaseLetter(lettersArr[i]);

            // ensure locaiton is on the borad
            if (tempNumberLocation < 1 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } else {
                // someone is there and stop looking in this direction
                break;
            }

        }

        // up and left
        for (int j = 1, i = numberVersionOfLetter; j <= 8; i--, j++) {
            int tempNumberLocation = numberlocation + j;

            String tempLetterLocation = decreaseLetter(lettersArr[i]);

            // ensure locaiton is on the borad
            if (tempNumberLocation > 8 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } else {
                // someone is there and stop looking in this direction
                break;
            }

        }

    }

    private void rookValidMove(String name) {
        pieces.get(name).validMoves.clear();
        char[] locationArr = pieces.get(name).location.toCharArray();

        int numberlocation = Integer.parseInt(Character.toString(locationArr[1]));
        String letterLocation = Character.toString(locationArr[0]);

        // up

        for (int i = numberlocation; i <= 8; i++) {

            // make sure locaiton in empty
            String tempLocation = letterLocation + i;
            if (tempLocation.equals(pieces.get(name).location)) {
                continue;
            }

            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                pieces.get(name).validMoves.add(tempLocation);
            } else {
                
                break;
            }

        }

        // down
        for (int i = numberlocation; i >= 1; i--) {

            // make sure locaiton in empty
            String tempLocation = letterLocation + i;
            if (tempLocation.equals(pieces.get(name).location)) {
                continue;
            }
            //ensure that the location is on the bou
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                pieces.get(name).validMoves.add(tempLocation);
            } else {
                break;
            }

        }

        // right
        char[] lettersArr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        for (int letterLocationInt = 0; letterLocationInt < 8; letterLocationInt++) {
            if (locationArr[0] == lettersArr[letterLocationInt]) {
                for (int i = letterLocationInt; i <= 7; i++) {
                    
                    String tempLocation = increaseLetter(lettersArr[i]) + numberlocation;
                    // check empty

                    if (whoIsAtLocationBackend(tempLocation).equals("")) {
                        pieces.get(name).validMoves.add(tempLocation);
                    } else {
                        break;
                    }
                    
                }
            }
        }

        // left
        for (int letterLocationInt = 0; letterLocationInt < 8; letterLocationInt++) {
            if (locationArr[0] == lettersArr[letterLocationInt]) {
                for (int i = letterLocationInt; i >= 1; i--) {

                    String tempLocation = decreaseLetter(lettersArr[i]) + numberlocation;
                    // check empty

                    if (whoIsAtLocationBackend(tempLocation).equals("")) {
                        pieces.get(name).validMoves.add(tempLocation);
                    } else {
                        break;
                    }

                }
            }
        }
    }

    public void kingValidMoves(String name) {
        pieces.get(name).validMoves.clear();
        char[] locationArr = pieces.get(name).location.toCharArray();

        int numberlocation = Integer.parseInt(Character.toString(locationArr[1]));

        // String tempLocation = letterLocation + numberlocation;

        for (int newNumberLocation = 1; newNumberLocation >= -1; newNumberLocation--) {
            for (int newLetterLocation = -1; newLetterLocation <= 1; newLetterLocation++) {
                if (newLetterLocation == 0 && newNumberLocation == 0) {
                    // skip if not moving
                    continue;
                }

                // get location
                int finalNumberLocation = newNumberLocation + numberlocation;
                if (finalNumberLocation == 0 || finalNumberLocation == 9) {
                    continue;
                }
                String finalLetterLocation = "fail";
                switch (newLetterLocation) {
                    case -1:

                        finalLetterLocation = decreaseLetter(locationArr[0]);
                        break;
                    case 0:

                        finalLetterLocation = Character.toString(locationArr[0]);

                        break;
                    case 1:

                        finalLetterLocation = increaseLetter(locationArr[0]);
                        break;
                    default:
                        break;
                }

                String tempLocation = finalLetterLocation + finalNumberLocation;
                // check if location is valid

                if (whoIsAtLocationBackend(tempLocation).equals("")) {
                    // add valid locations
                    pieces.get(name).validMoves.add(tempLocation);
                } else if (!whoIsAtLocationBackend(tempLocation).substring(0, 1)
                        .equals(whoIsAtLocationBackend(pieces.get(name).location).substring(0, 1))) {
                    pieces.get(name).validMoves.add(tempLocation);
                }

            }
        }

    }

    public void pawnValidMoves(String name) {

        char[] locationArr = pieces.get(name).location.toCharArray();

        String letterLocation = Character.toString(locationArr[0]);
        int numberlocation;
        String subName = pieces.get(name).name.substring(0, 1);

        if (subName.equals("w")) {
            numberlocation = Integer.parseInt(Character.toString(locationArr[1])) + 1;
        } else {
            numberlocation = Integer.parseInt(Character.toString(locationArr[1])) - 1;
        }

        String locationForMovingTwoForwardOnFirstMove = pieces.get(name).validMoves.get(0);
        String tempLocation = letterLocation + numberlocation;

        // check to make sure that their is not already a piece there.
        if (whoIsAtLocationBackend(tempLocation).equals("")) {

            pieces.get(name).validMoves.clear();
            if (!pieces.get(name).moreThenOnce) {
                pieces.get(name).validMoves.add(locationForMovingTwoForwardOnFirstMove);

            }
            pieces.get(name).validMoves.add(tempLocation);

        }
        // check to see if the pawn can capture anything

        // capture right
        String increaredLetter = increaseLetter(locationArr[0]);
        String captureRight = letterLocation + "x" + increaredLetter + numberlocation;
        if (!captureRight.equals("")) {
            String captureRightCheckwho = whoIsAtLocationBackend(captureRight);
            if (!captureRightCheckwho.equals("")) {
                // if the pawn is white
                if (pieces.get(name).name.substring(0, 1).equals("w")) {
                    if (captureRightCheckwho.substring(0, 1).equals("2")) {
                        pieces.get(name).validMoves.add(captureRight);
                    }
                }
                // if the pawn is black
                else if (pieces.get(name).name.substring(0, 1).equals("b")) {
                    if (captureRightCheckwho.substring(0, 1).equals("1")) {
                        pieces.get(name).validMoves.add(captureRight);
                    }
                }

            }
        }

        String decreasedLetter = decreaseLetter(locationArr[0]);
        String captureLeft = letterLocation + "x" + decreasedLetter + numberlocation;

        if (!captureLeft.equals("")) {
            String captureLeftCheckwho = whoIsAtLocationBackend(captureLeft);
            if (!captureLeftCheckwho.equals("")) {
                // if the pawn is white
                if (pieces.get(name).name.substring(0, 1).equals("w")) {
                    if (captureLeftCheckwho.substring(0, 1).equals("2")) {
                        pieces.get(name).validMoves.add(captureLeft);
                    }
                }
                // if the pawn is black
                else if (pieces.get(name).name.substring(0, 1).equals("b")) {
                    if (captureLeftCheckwho.substring(0, 1).equals("1")) {
                        pieces.get(name).validMoves.add(captureLeft);

                    }
                }

            }
        }
    }

    public void removePiece() {
        String whoGotCaptured = whoIsAtLocationBackend(userInput.substring(2));

        if (!whoGotCaptured.equals("")) {
            whoGotCaptured = whoGotCaptured.substring(1);
            pieces.remove(whoGotCaptured);
        }
    }

    public board() {

        createPieces();
        displayBoard();

    }

    private void createPieces() {
        createPawns();
        createRooks();
        createHorse();
        createBishop();
        createQueen();
        createKing();
    }

    public void displayBoard() {

        for (int i = 8; i > 0; i--) {
            for (int d = 1; d < 9; d++) {
                String output = whoIsAtLocation(getCharForNumber(d).toString() + i);

                System.out.print(output + " ");
            }
            System.out.print("\n");
        }

    }

    public String whoIsAtLocation(String location) {
        try {
            if (pieces.get("white rook - 1").location.equals(location)) {
                return "WR";
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            if (pieces.get("white rook - 2").location.equals(location)) {
                return "WR";
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            if (pieces.get("black rook - 1").location.equals(location)) {
                return "BR";
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {

            if (pieces.get("black rook - 2").location.equals(location)) {
                return "BR";
            }
        } catch (Exception e) {

        }
        try {

            if (pieces.get("white pawn - 1").location.equals(location)) {
                return "WP";
            }
        } catch (Exception e) {

        }
        try {

            if (pieces.get("white pawn - 2").location.equals(location)) {
                return "WP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 3").location.equals(location)) {
                return "WP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 4").location.equals(location)) {
                return "WP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 5").location.equals(location)) {
                return "WP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 6").location.equals(location)) {
                return "WP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 7").location.equals(location)) {
                return "WP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 8").location.equals(location)) {
                return "WP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 1").location.equals(location)) {
                return "BP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 2").location.equals(location)) {
                return "BP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 3").location.equals(location)) {
                return "BP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 4").location.equals(location)) {
                return "BP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 5").location.equals(location)) {
                return "BP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 6").location.equals(location)) {
                return "BP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 7").location.equals(location)) {
                return "BP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 8").location.equals(location)) {
                return "BP";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white horse - 1").location.equals(location)) {
                return "WH";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white horse - 2").location.equals(location)) {
                return "WH";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black horse - 1").location.equals(location)) {
                return "BH";
            }
        } catch (Exception e) {

        }

        try {

            if (pieces.get("black horse - 2").location.equals(location)) {
                return "BH";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white bishop - 1").location.equals(location)) {
                return "WB";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white bishop - 2").location.equals(location)) {
                return "WB";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black bishop - 1").location.equals(location)) {
                return "BB";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black bishop - 2").location.equals(location)) {
                return "BB";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white queen").location.equals(location)) {
                return "WQ";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black queen").location.equals(location)) {
                return "BQ";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white king").location.equals(location)) {
                return "WK";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black king").location.equals(location)) {
                return "BK";
            }
        } catch (Exception e) {

        }

        return location;

    }

    public String whoIsAtLocationBackend(String location) {

        try {
            if (pieces.get("white rook - 1").location.equals(location)) {
                return "1white rook - 1";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white rook - 2").location.equals(location)) {
                return "1white rook - 2";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black rook - 1").location.equals(location)) {
                return "2black rook - 1";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black rook - 2").location.equals(location)) {
                return "2black rook - 2";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 1").location.equals(location)) {
                return "1white pawn - 1";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 2").location.equals(location)) {
                return "1white pawn - 2";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 3").location.equals(location)) {
                return "1white pawn - 3";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 4").location.equals(location)) {
                return "1white pawn - 4";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 5").location.equals(location)) {
                return "1white pawn - 5";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 6").location.equals(location)) {
                return "1white pawn - 6";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 7").location.equals(location)) {
                return "1white pawn - 7";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white pawn - 8").location.equals(location)) {
                return "1white pawn - 8";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 1").location.equals(location)) {
                return "2black pawn - 1";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 2").location.equals(location)) {
                return "2black pawn - 2";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 3").location.equals(location)) {
                return "2black pawn - 3";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 4").location.equals(location)) {
                return "2black pawn - 4";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 5").location.equals(location)) {
                return "2black pawn - 5";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 6").location.equals(location)) {
                return "2black pawn - 6";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 7").location.equals(location)) {
                return "2black pawn - 7";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black pawn - 8").location.equals(location)) {
                return "2black pawn - 8";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white horse - 1").location.equals(location)) {
                return "1white horse - 1";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white horse - 2").location.equals(location)) {
                return "2white horse - 2";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black horse - 1").location.equals(location)) {
                return "2black horse - 1";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black horse - 2").location.equals(location)) {
                return "2black horse - 2";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white bishop - 1").location.equals(location)) {
                return "1white bishop - 1";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white bishop - 2").location.equals(location)) {
                return "1white bishop - 2";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black bishop - 1").location.equals(location)) {
                return "2black bishop - 1";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black bishop - 2").location.equals(location)) {
                return "2black bishop - 2";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white queen").location.equals(location)) {
                return "1white queen";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black queen").location.equals(location)) {
                return "2black queen";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("white king").location.equals(location)) {
                return "1white king";
            }
        } catch (Exception e) {

        }
        try {
            if (pieces.get("black king").location.equals(location)) {
                return "2black king";
            }
        } catch (Exception e) {

        }
        return "";

    }

    private String increaseLetter(Character letter) {

        char[] alphabet = "abcdefgh".toCharArray();
        if (letter != alphabet[7]) {
            for (int j = 0; j < alphabet.length; j++) {
                if (letter == alphabet[j]) {

                    return Character.toString(alphabet[j + 1]);
                }
            }
        }
        return "";

    }

    private String decreaseLetter(Character letter) {

        char[] alphabet = "abcdefgh".toCharArray();
        if (letter != alphabet[0]) {
            for (int j = 0; j < alphabet.length; j++) {
                if (letter == alphabet[j]) {

                    return Character.toString(alphabet[j - 1]);
                }
            }
        }
        return "";

    }

    private String getCharForNumber(int i) {
        char[] alphabet = "abcdefgh".toCharArray();
        if (i > 8) {
            return null;
        }
        return Character.toString(alphabet[i - 1]);

    }

    private void createPawns() {
        // white pawn create
        pieces.put("white pawn - 1", new pawn("a2", "white pawn"));
        pieces.put("white pawn - 2", new pawn("b2", "white pawn"));
        pieces.put("white pawn - 3", new pawn("c2", "white pawn"));
        pieces.put("white pawn - 4", new pawn("d2", "white pawn"));
        pieces.put("white pawn - 5", new pawn("e2", "white pawn"));
        pieces.put("white pawn - 6", new pawn("f2", "white pawn"));
        pieces.put("white pawn - 7", new pawn("g2", "white pawn"));
        pieces.put("white pawn - 8", new pawn("h2", "white pawn"));

        // black pawn create
        pieces.put("black pawn - 1", new pawn("a7", "black pawn"));
        pieces.put("black pawn - 2", new pawn("b7", "black pawn"));
        pieces.put("black pawn - 3", new pawn("c7", "black pawn"));
        pieces.put("black pawn - 4", new pawn("d7", "black pawn"));
        pieces.put("black pawn - 5", new pawn("e7", "black pawn"));
        pieces.put("black pawn - 6", new pawn("f7", "black pawn"));
        pieces.put("black pawn - 7", new pawn("g7", "black pawn"));
        pieces.put("black pawn - 8", new pawn("h7", "black pawn"));

    }

    private void createRooks() {
        pieces.put("white rook - 1", new rook("a1", "white rook"));
        pieces.put("white rook - 2", new rook("h1", "white rook"));
        pieces.put("black rook - 1", new rook("a8", "black rook"));
        pieces.put("black rook - 2", new rook("h8", "black rook"));

    }

    private void createHorse() {
        pieces.put("white horse - 1", new horse("b1", "white horse"));
        pieces.put("white horse - 2", new horse("g1", "white horse"));
        pieces.put("black horse - 1", new horse("b8", "black horse"));
        pieces.put("black horse - 2", new horse("g8", "black horse"));

    }

    private void createBishop() {
        pieces.put("white bishop - 1", new bishop("c1", "white bishop"));
        pieces.put("white bishop - 2", new bishop("f1", "white bishop"));
        pieces.put("black bishop - 1", new bishop("c8", "black bishop"));
        pieces.put("black bishop - 2", new bishop("f8", "black bishop"));
    }

    private void createQueen() {
        pieces.put("white queen", new queen("d1", "white queen"));

        pieces.put("black queen", new queen("d8", "black queen"));
    }

    private void createKing() {
        pieces.put("white king", new king("e1", "white king"));
        pieces.put("black king", new king("e8", "black king"));

    }

    {
        // public void moveThePieceWhite() {
        // for (int i = 0; i < 4; i++) {
        // try {
        // if (pieces.get("white pawn - 1").validMoves.get(i).equals(userInput)) {
        // removePiece();
        // pieces.get("white pawn - 1").setLocation(userInput);

        // break;
        // }
        // } catch (Exception e) {

        // }
        // try {
        // if (pieces.get("white pawn - 2").validMoves.get(i).equals(userInput)) {
        // removePiece();
        // pieces.get("white pawn - 2").setLocation(userInput);
        // break;
        // }
        // } catch (Exception e) {

        // }
        // try {
        // if (pieces.get("white pawn - 3").validMoves.get(i).equals(userInput)) {
        // removePiece();
        // pieces.get("white pawn - 3").setLocation(userInput);
        // break;
        // }
        // } catch (Exception e) {

        // }
        // try {
        // if (pieces.get("white pawn - 4").validMoves.get(i).equals(userInput)) {
        // removePiece();
        // pieces.get("white pawn - 4").setLocation(userInput);
        // break;
        // }
        // } catch (Exception e) {

        // }
        // try {
        // if (pieces.get("white pawn - 5").validMoves.get(i).equals(userInput)) {
        // removePiece();
        // pieces.get("white pawn - 5").setLocation(userInput);
        // break;
        // }
        // } catch (Exception e) {

        // }
        // try {
        // if (pieces.get("white pawn - 6").validMoves.get(i).equals(userInput)) {
        // removePiece();
        // pieces.get("white pawn - 6").setLocation(userInput);
        // break;
        // }
        // } catch (Exception e) {

        // }
        // try {
        // if (pieces.get("white pawn - 7").validMoves.get(i).equals(userInput)) {
        // removePiece();
        // pieces.get("white pawn - 7").setLocation(userInput);
        // break;
        // }
        // } catch (Exception e) {

        // }
        // try {
        // if (pieces.get("white pawn - 8").validMoves.get(i).equals(userInput)) {
        // removePiece();
        // pieces.get("white pawn - 8").setLocation(userInput);
        // break;
        // }
        // } catch (Exception e) {

        // }

        // }

        // }
    }
}