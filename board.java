package chess;

import java.util.HashMap;
import java.util.Scanner;

public class board {
    /*
     * todo
     * bugs
     *      when displaying the board if queen 1 is gone and queen 2 is alive it wont
     *      display queen 2
     *          potential solution
     *           go thought all pieces 
     *          stop using the hashmap pieces and use seprate hashmaps for each type of piece
     * 
     * if two are moving to the same position check for that
     * error handling
     * black move
     * be able to import and export PGN
     * color text?
     * see the piece as a symbol
     */
    public HashMap<String, pieces> pieces = new HashMap<String, pieces>();
    private Scanner in = new Scanner(System.in);
    private String userInput;
    public String PGN = "";
    private Integer numberOfWhitePawns = 8;
    private Integer numberOfBlackPawns = 8;

    private Integer numberOfWhiteRooks = 2;
    private Integer numberOfBlackRooks = 2;

    private Integer numberOfWhiteKnights = 2;
    private Integer numberOfBlackKnights = 2;

    private Integer numberOfWhiteBishops = 2;
    private Integer numberOfBlackBishops = 2;

    private Integer numberOfWhiteQueens = 1;
    private Integer numberOfBlackQueens = 1;

    public void moveWhite() {

        moveByMove();

        displayBoard();

    }

    public void moveByMove() {

        userInput = in.nextLine();
        char[] userInputArr = userInput.toCharArray();
        String[] letterArr = { "a", "b", "c", "d", "e", "f", "g", "h" };
        int[] numberArr = { 1, 2, 3, 4, 5, 6, 7, 8 };
        Boolean done = false;
        // its a pawn move
        if (userInput.length() == 2) {
            for (int j = 1; j <= numberOfWhitePawns; j++) {
                try {
                    pawnValidMoves("white pawn - " + j);
                } catch (Exception e) {

                }

            }
            for (int j = 1; j <= numberOfWhitePawns; j++) {
                try {
                    if (pieces.get("white pawn - " + j).validMoves.contains(userInput)) {
                        pieces.get("white pawn - " + j).setLocation(userInput);
                        pieces.get("white pawn - " + j).moreThenOnce = true;
                    }
                } catch (Exception e) {

                }

            }

        }

        // pawn capture
        if (userInput.length() == 4 && userInputArr[1] == 'x') {
            for (int whatPawn = 1; whatPawn <= 8; whatPawn++) {
                try {
                    if (pieces.get("white pawn - " + whatPawn).getLocation().substring(0, 1)
                            .equals(userInput.subSequence(0, 1))) {

                        // now we know what pawn is capturing

                        // so then we need to find the pawn in the hashmap

                        pawnValidMoves("white pawn - " + whatPawn);
                        // matcch userinput to the pawns valid move

                        for (int i = 0; i < pieces.get("white pawn - " + whatPawn).validMoves.size(); i++) {

                            if (pieces.get("white pawn - " + whatPawn).validMoves.get(i)
                                    .equals(userInput.substring(2))) {

                                removePiece();
                                pieces.get("white pawn - " + whatPawn).setLocation(userInput.substring(2));
                                pieces.get("white pawn - " + whatPawn).moreThenOnce = true;
                                break;

                            }
                        }

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }

        }

        // pawn Promote with move
        if (userInput.length() == 4 && userInputArr[2] == '=') {
            for (int whatPawn = 1; whatPawn <= 8; whatPawn++) {
                try {
                    if (pieces.get("white pawn - " + whatPawn).getLocation().substring(0, 1)
                            .equals(userInput.subSequence(0, 1))) {

                        // now we know what pawn is capturing

                        // so then we need to find the pawn in the hashmap

                        pawnValidMoves("white pawn - " + whatPawn);
                        // matcch userinput to the pawns valid move

                        for (int i = 0; i < pieces.get("white pawn - " + whatPawn).validMoves.size(); i++) {

                            if (pieces.get("white pawn - " + whatPawn).validMoves.get(i)
                                    .equals(userInput.substring(0, 2))) {

                                pawnPremote("white pawn - " + whatPawn, true);

                                break;

                            }
                        }

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        }

        // pawn premote with capture
        if (userInput.length() == 6 && userInputArr[4] == '=') {
            for (int whatPawn = 1; whatPawn <= 8; whatPawn++) {
                try {
                    if (pieces.get("white pawn - " + whatPawn).getLocation().substring(0, 1)
                            .equals(userInput.subSequence(0, 1))) {

                        // now we know what pawn is capturing

                        // so then we need to find the pawn in the hashmap

                        pawnValidMoves("white pawn - " + whatPawn);
                        // matcch userinput to the pawns valid move

                        for (int i = 0; i < pieces.get("white pawn - " + whatPawn).validMoves.size(); i++) {

                            if (pieces.get("white pawn - " + whatPawn).validMoves.get(i)
                                    .equals(userInput.substring(2, 4))) {
                                removePiece();
                                pawnPremote("white pawn - " + whatPawn, false);

                                break;

                            }
                        }

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        }

        // big peice move
        if (userInput.length() == 3) {
            switch (userInputArr[0]) {
                case 'K':
                    // find valid move for piece
                    kingValidMove("white king");
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
                    // find valid move for piece
                    for (int whatQueen = 1; whatQueen <= numberOfWhiteQueens; whatQueen++) {

                        queenValidMove("white queen - " + whatQueen);
                    }
                    // check if a valid move matches user input
                    for (int whatQueen = 1; whatQueen <= numberOfWhiteQueens; whatQueen++) {
                        try {
                            for (int validMove = 0; validMove < pieces.get("white queen - " + whatQueen).validMoves
                                    .size(); validMove++) {
                                if (pieces.get("white queen - " + whatQueen).validMoves.get(validMove)
                                        .equals(userInput.substring(1))) {
                                    // move the piece
                                    pieces.get("white queen - " + whatQueen).setLocation(userInput.substring(1));
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }

                    break;
                case 'N':
                    // find valid move for piece
                    for (int i = 1; i <= numberOfWhiteKnights; i++) {
                        knightValidMove("white knight - " + i);
                    }

                    // check if a valid move matches user input

                    for (int j = 1; j <= numberOfWhiteKnights; j++) {
                        try {
                            for (int i = 0; i < pieces.get("white knight - " + j).validMoves.size(); i++) {
                                if (pieces.get("white knight - " + j).validMoves.get(i)
                                        .equals(userInput.substring(1))) {
                                    // move the piece
                                    pieces.get("white knight - " + j).setLocation(userInput.substring(1));
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }
                    break;
                case 'B':
                    // find valid move for piece
                    for (int i = 1; i <= numberOfWhiteBishops; i++) {
                        try {
                            bishopValidMove("white bishop - " + i);

                        } catch (Exception e) {

                        }
                    }

                    // check if a valid move matches user input
                    for (int whatBishop = 1; whatBishop <= numberOfWhiteBishops; whatBishop++) {
                        try {
                            for (int i = 0; i < pieces.get("white bishop - " + whatBishop).validMoves.size(); i++) {
                                if (pieces.get("white bishop - " + whatBishop).validMoves.get(i)
                                        .equals(userInput.substring(1))) {
                                    // move the piece
                                    pieces.get("white bishop - " + whatBishop).setLocation(userInput.substring(1));
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }

                    break;
                case 'R':
                    // find valid move for piece
                    for (int i = 0; i < numberArr.length; i++) {
                        try {
                            rookValidMove("white rook - " + i);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }

                    // check if a valid move matches user input
                    for (int i = 1; i <= numberOfWhiteRooks; i++) {
                        try {
                            for (int k = 0; k < pieces.get("white rook - " + i).validMoves.size(); k++) {
                                if (pieces.get("white rook - " + i).validMoves.get(k).equals(userInput.substring(1))) {
                                    // move the piece
                                    pieces.get("white rook - " + i).setLocation(userInput.substring(1));
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
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
                    // find valid move for piece
                    kingValidMove("white king");

                    // check if a valid move matches user input
                    for (int i = 0; i < 8; i++) {
                        try {
                            if (pieces.get("white king").validMoves.get(i).equals(userInput.substring(2))) {
                                // remove the captured piece\
                                removePiece();
                                // move the piece
                                pieces.get("white king").setLocation(userInput.substring(2));
                                PGN += userInput;
                                break;
                            }
                        } catch (Exception e) {

                        }
                    }
                    // move the piece
                    break;
                case 'Q':
                    // find valid move for piece
                    for (int i = 1; i <= numberOfWhiteQueens; i++) {

                        queenValidMove("white queen - " + i);
                    }

                    // check if a valid move matches user input
                    for (int whatQueen = 0; whatQueen < numberArr.length; whatQueen++) {
                        try {
                            for (int whatValidMove = 0; whatValidMove < pieces
                                    .get("white queen - " + whatQueen).validMoves
                                    .size(); whatValidMove++) {
                                try {
                                    if (pieces.get("white queen - " + whatQueen).validMoves.get(whatValidMove)
                                            .equals(userInput.substring(2))) {
                                        // remove the captured piece\
                                        removePiece();
                                        // move the piece
                                        pieces.get("white queen - " + whatQueen).setLocation(userInput.substring(2));

                                        break;
                                    }
                                } catch (Exception e) {

                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }

                    break;
                case 'N':
                    // find valid move for piece
                    for (int whatKnight = 1; whatKnight <= numberOfWhiteKnights; whatKnight++) {
                        knightValidMove("white knight - " + whatKnight);
                    }

                    // check if a valid move matches user input

                    for (int whatKnight = 1; whatKnight <= numberOfWhiteKnights; whatKnight++) {
                        try {
                            for (int i = 0; i < pieces.get("white knight - " + whatKnight).validMoves.size(); i++) {
                                if (pieces.get("white knight - " + whatKnight).validMoves.get(i)
                                        .equals(userInput.substring(2))) {
                                    // move the piece
                                    removePiece();
                                    pieces.get("white knight - " + whatKnight).setLocation(userInput.substring(2));
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }
                    break;
                case 'B':
                    // find valid move for piece
                    for (int whatBishop = 1; whatBishop <= numberOfWhiteBishops; whatBishop++) {
                        try {
                            bishopValidMove("white bishop - " + whatBishop);

                        } catch (Exception e) {

                        }
                    }

                    // check if a valid move matches user input
                    for (int whatBishop = 1; whatBishop <= numberOfWhiteBishops; whatBishop++) {
                        try {
                            for (int validMove = 0; validMove < pieces.get("white bishop - " + whatBishop).validMoves
                                    .size(); validMove++) {
                                if (pieces.get("white bishop - " + whatBishop).validMoves.get(validMove)
                                        .equals(userInput.substring(2))) {
                                    removePiece();
                                    // move the piece
                                    pieces.get("white bishop - " + whatBishop).setLocation(userInput.substring(2));
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }
                    break;
                case 'R':
                    for (int whatRook = 1; whatRook <= numberOfWhiteRooks; whatRook++) {

                        try {
                            rookValidMove("white rook - " + whatRook);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }

                    for (int whatRook = 1; whatRook <= numberOfWhiteRooks; whatRook++) {
                        // check if a valid move matches user input
                        try {
                            for (int i = 0; i < pieces.get("white rook - " + whatRook).validMoves.size(); i++) {
                                if (pieces.get("white rook - " + whatRook).validMoves.get(i)
                                        .equals(userInput.substring(2))) {
                                    // move the piece
                                    removePiece();
                                    pieces.get("white rook - " + whatRook).setLocation(userInput.substring(2));
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }

                    break;
                default:
                    break;
            }

        }

        // if two pieces can move to the same location
        if (userInput.length() == 4 && userInputArr[1] != 'x') {
            switch (userInputArr[0]) {
                case 'N':
                    // find the witch knight

                    // letter look
                    for (int LetterLocation = 0; LetterLocation < letterArr.length && !done; LetterLocation++) {
                        if (userInput.substring(1, 2).equals(letterArr[LetterLocation])) {
                            for (int whatKnight = 1; whatKnight <= numberOfWhiteKnights && !done; whatKnight++) {
                                try {
                                    if (pieces.get("white knight - " + whatKnight).getLocation().substring(0, 1)
                                            .equals(userInput.substring(1, 2))) {
                                        knightValidMove("white knight - " + whatKnight);

                                        try {
                                            for (int a = 0; a < pieces.get("white knight - " + whatKnight).validMoves
                                                    .size()
                                                    && !done; a++) {
                                                if (pieces.get("white knight - " + whatKnight).validMoves.get(a)
                                                        .equals(userInput.substring(2))) {
                                                    // move the piece

                                                    pieces.get("white knight - " + whatKnight)
                                                            .setLocation(userInput.substring(2));
                                                    done = true;
                                                }
                                            }
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                        }

                                    }
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }

                            }
                        }
                    }
                    // number look
                    done = false;
                    try {
                        for (int numberLocation = 1; numberLocation < numberArr.length && !done; numberLocation++) {
                            if (Integer.parseInt(userInput.substring(1, 2)) == numberArr[numberLocation - 1]) {
                                for (int whatKnight = 1; whatKnight <= numberOfWhiteKnights && !done; whatKnight++) {
                                    try {
                                        if (pieces.get("white knight - " + whatKnight).getLocation().substring(1, 2)
                                                .equals(userInput.substring(1, 2))) {
                                            knightValidMove("white knight - " + whatKnight);

                                            try {
                                                for (int a = 0; a < pieces
                                                        .get("white knight - " + whatKnight).validMoves
                                                        .size() && !done; a++) {
                                                    if (pieces.get("white knight - " + whatKnight).validMoves.get(a)
                                                            .equals(userInput.substring(2))) {
                                                        // move the piece

                                                        pieces.get("white knight - " + whatKnight)
                                                                .setLocation(userInput.substring(2));
                                                        done = true;
                                                        break;

                                                    }
                                                }
                                            } catch (Exception e) {
                                                // TODO: handle exception
                                            }

                                        }
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                    }

                                }
                            }
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    break;
                
                case 'R':

                    // find the witch rook

                    // letter look
                    for (int LetterLocation = 0; LetterLocation < letterArr.length && !done; LetterLocation++) {
                        if (userInput.substring(1, 2).equals(letterArr[LetterLocation])) {
                            for (int whatRook = 1; whatRook <= numberOfWhiteRooks && !done; whatRook++) {
                                try {
                                    if (pieces.get("white rook - " + whatRook).getLocation().substring(0, 1)
                                            .equals(userInput.substring(1, 2))) {
                                        rookValidMove("white rook - " + whatRook);
                                        try {
                                            for (int a = 0; a < pieces.get("white rook - " + whatRook).validMoves
                                                    .size()
                                                    && !done; a++) {
                                                if (pieces.get("white rook - " + whatRook).validMoves.get(a)
                                                        .equals(userInput.substring(2))) {
                                                    // move the piece

                                                    pieces.get("white rook - " + whatRook)
                                                            .setLocation(userInput.substring(2));
                                                    done = true;
                                                }
                                            }
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                        }
                                    }
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }

                            }
                        }
                    }
                    // number look
                    done = false;
                    try {
                        for (int numberLocation = 1; numberLocation < numberArr.length && !done; numberLocation++) {
                            if (Integer.parseInt(userInput.substring(1, 2)) == numberArr[numberLocation - 1]) {
                                for (int whatRook = 1; whatRook <= numberOfWhiteRooks && !done; whatRook++) {
                                    try {
                                        if (pieces.get("white knight - " + whatRook).getLocation().substring(1, 2)
                                                .equals(userInput.substring(1, 2))) {
                                            knightValidMove("white knight - " + whatRook);

                                            try {
                                                for (int a = 0; a < pieces.get("white knight - " + whatRook).validMoves
                                                        .size() && !done; a++) {
                                                    if (pieces.get("white knight - " + whatRook).validMoves.get(a)
                                                            .equals(userInput.substring(2))) {
                                                        // move the piece

                                                        pieces.get("white knight - " + whatRook)
                                                                .setLocation(userInput.substring(2));
                                                        done = true;
                                                        break;

                                                    }
                                                }
                                            } catch (Exception e) {
                                                // TODO: handle exception
                                            }

                                        }
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                    }

                                }
                            }
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    break;
                default:
                    break;
            }
        }
        // //if two or more pieces can move to the same location
        // //if two or more pieces can capture the same location
    }

    private void pawnPremote(String name, boolean move) {
        String whatPieceToPremoteTo;
        if (move) {
            whatPieceToPremoteTo = userInput.substring(3);
            switch (whatPieceToPremoteTo) {
                case "Q":
                    numberOfWhiteQueens++;
                    pieces.put("white queen - " + numberOfWhiteQueens,
                            new queen(userInput.substring(0, 2), "white queen"));
                    numberOfWhitePawns--;
                    break;
                case "R":
                    numberOfWhiteRooks++;
                    pieces.put("white rook - " + numberOfWhiteRooks,
                            new rook(userInput.substring(0, 2), "white rook"));
                    numberOfWhitePawns--;
                    break;
                case "N":
                    numberOfWhiteKnights++;
                    numberOfWhitePawns--;
                    pieces.put("white knight - " + numberOfWhiteKnights,
                            new knight(userInput.substring(0, 2), "white knight"));
                    break;
                case "B":
                    numberOfWhiteBishops++;
                    pieces.put("white bishop - " + numberOfWhiteBishops,
                            new bishop(userInput.substring(0, 2), "white bishop"));
                    numberOfWhitePawns--;
                    break;
                default:
                    break;
            }
        } else {
            whatPieceToPremoteTo = userInput.substring(5);

            switch (whatPieceToPremoteTo) {
                // switch userinput number
                case "Q":
                    numberOfWhiteQueens++;
                    pieces.put("white queen - " + numberOfWhiteQueens,
                            new queen(userInput.substring(2, 4), "white queen"));
                    numberOfWhitePawns--;
                    break;
                case "R":
                    numberOfWhiteRooks++;
                    pieces.put("white rook - " + numberOfWhiteRooks,
                            new queen(userInput.substring(2, 4), "white rook"));
                    numberOfWhitePawns--;
                    break;
                case "N":
                    numberOfWhiteKnights++;
                    pieces.put("white knight - " + numberOfWhiteKnights,
                            new queen(userInput.substring(2, 4), "white knight"));
                    numberOfWhitePawns--;
                    break;
                case "B":
                    numberOfWhiteBishops++;
                    pieces.put("white bishop - " + numberOfWhiteBishops,
                            new queen(userInput.substring(2, 4), "white bishop"));
                    numberOfWhitePawns--;
                    break;
                default:
                    break;
            }
        }

        pieces.remove(name);
    }

    public void knightValidMove(String name) {
        pieces.get(name).validMoves.clear();
        char[] locationArr = pieces.get(name).location.toCharArray();
        int numberLocation = Integer.parseInt(Character.toString(locationArr[1]));

        String[] tempLetterLocationArr = new String[8];
        int[] tempNumberLocationArr = new int[8];
        String[] letterArr = { "a", "b", "c", "d", "e", "f", "g", "h" };
        char[] decreasedLetter = decreaseLetter(locationArr[0]).toCharArray();
        char[] increasedLetter = increaseLetter(locationArr[0]).toCharArray();
        // number +2
        tempNumberLocationArr[0] = numberLocation + 2;
        // letter +1
        tempLetterLocationArr[0] = increaseLetter(locationArr[0]);

        // number +2 letter -1
        tempNumberLocationArr[1] = numberLocation + 2;
        tempLetterLocationArr[1] = decreaseLetter(locationArr[0]);

        // number -2 letter +1
        tempNumberLocationArr[2] = numberLocation - 2;
        tempLetterLocationArr[2] = increaseLetter(locationArr[0]);
        // number -2 letter -1
        tempNumberLocationArr[3] = numberLocation - 2;
        tempLetterLocationArr[3] = decreaseLetter(locationArr[0]);

        // number +1 letter +2
        tempNumberLocationArr[4] = numberLocation + 1;
        tempLetterLocationArr[4] = increaseLetter(increasedLetter[0]);
        // number -1 letter +2
        tempNumberLocationArr[5] = numberLocation - 1;
        tempLetterLocationArr[5] = increaseLetter(increasedLetter[0]);

        // number +1 letter -2
        tempNumberLocationArr[6] = numberLocation + 1;
        tempLetterLocationArr[6] = decreaseLetter(decreasedLetter[0]);

        // number -1 letter -2
        tempNumberLocationArr[7] = numberLocation - 1;
        tempLetterLocationArr[7] = decreaseLetter(decreasedLetter[0]);

        boolean letterIsOnBoard = false;
        for (int i = 0; i < 8; i++) {
            String tempLocation = tempLetterLocationArr[i] + tempNumberLocationArr[i];
            // check if location is empty
            if (tempNumberLocationArr[i] < 1 || tempNumberLocationArr[i] > 8) {
                continue;
            }
            for (int j = 0; j < 8; j++) {
                if (letterArr[j].equals(tempLetterLocationArr[i])) {
                    letterIsOnBoard = true;
                    break;
                }
            }

            if (whoIsAtLocationBackend(tempLocation).equals("") && letterIsOnBoard) {
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
            }
        }
    }// end of knightValidMoves

    public void queenValidMove(String name) {
        pieces.get(name).validMoves.clear();
        char[] locationArr = pieces.get(name).location.toCharArray();
        char[] lettersArr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        int numberVersionOfLetter = 100;
        for (int i = 0; i < lettersArr.length; i++) {
            if (locationArr[0] == lettersArr[i]) {
                numberVersionOfLetter = i;
                break;
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
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else {
                // someone is there and stop looking in this direction
                break;
            }

        }
        // down and right
        for (int j = 1, i = numberVersionOfLetter; j <= 8; i++, j++) {
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
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
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
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
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
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
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
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else {
                // someone is there and stop looking in this direction
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
            // ensure that the location is on the bou
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else {
                // someone is there and stop looking in this direction
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
                    } // if someone is there and they are capturable
                    else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                            && name.substring(0, 1).equals("w")) {
                        // they are black and we are white
                        pieces.get(name).validMoves.add(tempLocation);
                        // someone is there and stop looking in this direction
                        break;
                    } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                            && name.substring(0, 1).equals("b")) {
                        // they are white and we are black
                        pieces.get(name).validMoves.add(tempLocation);
                        // someone is there and stop looking in this direction
                        break;
                    } else {
                        // someone is there and stop looking in this direction
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
                    } // if someone is there and they are capturable
                    else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                            && name.substring(0, 1).equals("w")) {
                        // they are black and we are white
                        pieces.get(name).validMoves.add(tempLocation);
                        // someone is there and stop looking in this direction
                        break;
                    } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                            && name.substring(0, 1).equals("b")) {
                        // they are white and we are black
                        pieces.get(name).validMoves.add(tempLocation);
                        // someone is there and stop looking in this direction
                        break;
                    } else {
                        // someone is there and stop looking in this direction
                        break;
                    }

                }
            }
        }

    }// end of queenValidMove

    public void bishopValidMove(String name) {
        pieces.get(name).validMoves.clear();
        char[] locationArr = pieces.get(name).location.toCharArray();
        char[] lettersArr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        int numberVersionOfLetter = 100;
        for (int i = 0; i < lettersArr.length; i++) {
            if (locationArr[0] == lettersArr[i]) {
                numberVersionOfLetter = i;
                break;
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
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else {
                // someone is there and stop looking in this direction
                break;
            }

        }
        // down and right
        for (int j = 1, i = numberVersionOfLetter; j <= 8; i++, j++) {
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
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
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
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
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
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else {
                // someone is there and stop looking in this direction
                break;
            }

        }

    }// end of bishop valid moves

    public void rookValidMove(String name) {
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
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else {
                // someone is there and stop looking in this direction
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
            // ensure that the location is on the bou
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they are capturable
            else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                    && name.substring(0, 1).equals("w")) {
                // they are black and we are white
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                    && name.substring(0, 1).equals("b")) {
                // they are white and we are black
                pieces.get(name).validMoves.add(tempLocation);
                // someone is there and stop looking in this direction
                break;
            } else {
                // someone is there and stop looking in this direction
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
                    } // if someone is there and they are capturable
                    else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                            && name.substring(0, 1).equals("w")) {
                        // they are black and we are white
                        pieces.get(name).validMoves.add(tempLocation);
                        // someone is there and stop looking in this direction
                        break;
                    } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                            && name.substring(0, 1).equals("b")) {
                        // they are white and we are black
                        pieces.get(name).validMoves.add(tempLocation);
                        // someone is there and stop looking in this direction
                        break;
                    } else {
                        // someone is there and stop looking in this direction
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
                    } // if someone is there and they are capturable
                    else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                            && name.substring(0, 1).equals("w")) {
                        // they are black and we are white
                        pieces.get(name).validMoves.add(tempLocation);
                        // someone is there and stop looking in this direction
                        break;
                    } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                            && name.substring(0, 1).equals("b")) {
                        // they are white and we are black
                        pieces.get(name).validMoves.add(tempLocation);
                        // someone is there and stop looking in this direction
                        break;
                    } else {
                        // someone is there and stop looking in this direction
                        break;
                    }

                }
            }
        }
    }// end of rook valid moves

    public void kingValidMove(String name) {
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
                }
                // if someone is there and they are capturable
                else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("2")
                        && name.substring(0, 1).equals("w")) {
                    // they are black and we are white
                    pieces.get(name).validMoves.add(tempLocation);
                } else if (whoIsAtLocationBackend(tempLocation).substring(0, 1).equals("1")
                        && name.substring(0, 1).equals("b")) {
                    // they are white and we are black
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

        // check to see if the pawn can capture anything

        // capture right

        String captureRight = increaseLetter(locationArr[0]) + numberlocation;
        boolean checkCaptureRight = false;
        if (userInput.length() > 2) {
            String captureRightCheckwho = whoIsAtLocationBackend(captureRight);
            if (!captureRightCheckwho.equals("")) {
                // if the pawn is white
                if (pieces.get(name).name.substring(0, 1).equals("w")) {
                    if (captureRightCheckwho.substring(0, 1).equals("2")) {
                        checkCaptureRight = true;

                    }
                }
                // if the pawn is black
                else if (pieces.get(name).name.substring(0, 1).equals("b")) {
                    if (captureRightCheckwho.substring(0, 1).equals("1")) {
                        checkCaptureRight = true;

                    }
                }

            }
        }

        String captureLeft = decreaseLetter(locationArr[0]) + numberlocation;

        boolean checkCaptureLeft = false;

        if (userInput.length() > 2) {
            String captureLeftCheckwho = whoIsAtLocationBackend(captureLeft);
            if (!captureLeftCheckwho.equals("")) {
                // if the pawn is white
                if (pieces.get(name).name.substring(0, 1).equals("w")) {
                    if (captureLeftCheckwho.substring(0, 1).equals("2")) {
                        checkCaptureLeft = true;

                    }
                } else if (pieces.get(name).name.substring(0, 1).equals("b")) {
                    // if the pawn is black
                    if (captureLeftCheckwho.substring(0, 1).equals("1")) {
                        checkCaptureLeft = true;

                    }
                }

            }
        }

        pieces.get(name).validMoves.clear();
        if (!pieces.get(name).moreThenOnce) {
            pieces.get(name).validMoves.add(locationForMovingTwoForwardOnFirstMove);

        }
        // check to make sure that their is not already a piece there.
        if (whoIsAtLocationBackend(tempLocation).equals("")) {

            pieces.get(name).validMoves.add(tempLocation);

        }
        if (checkCaptureLeft) {
            pieces.get(name).validMoves.add(captureLeft);
        }
        if (checkCaptureRight) {
            pieces.get(name).validMoves.add(captureRight);
        }

    }

    public void removePiece() {
        if (userInput.length() > 2) {
            String whoGotCaptured = whoIsAtLocationBackend(userInput.substring(2, 4));
            if (!whoGotCaptured.equals("")) {
                whoGotCaptured = whoGotCaptured.substring(1);

                String letterOfwhoGotCaptured = whoIsAtLocation(pieces.get(whoGotCaptured).getLocation()).substring(1);
                switch (letterOfwhoGotCaptured) {
                    case "Q":

                        numberOfBlackQueens--;
                        break;
                    case "R":

                        numberOfBlackRooks--;
                        break;
                    case "N":

                        numberOfBlackKnights--;
                        break;
                    case "B":

                        numberOfBlackBishops--;
                        break;
                    case "P":

                        numberOfBlackPawns--;
                        break;
                    default:
                        break;
                }
                pieces.remove(whoGotCaptured);
            }
        }

    }

    public board() {

        createPieces();
        displayBoard();

    }

    private void createPieces() {
        createPawns();
        createRooks();
        createKnight();
        createBishop();
        createQueen();
        createKing();
    }

    public void displayBoard() {

        for (int i = 8; i > 0; i--) {
            for (int d = 1; d < 9; d++) {
                String letterLocation = getCharForNumber(d).toString();
                String tempLocation = letterLocation + i;
                String output = whoIsAtLocation(tempLocation);

                System.out.print(output + " ");
            }
            System.out.print("\n");
        }

    }

    public String whoIsAtLocation(String location) {
        for (int whatRook = 1; whatRook <= numberOfWhiteRooks || whatRook <= 2; whatRook++) {
            try {
                if (pieces.get("white rook - " + whatRook).location.equals(location)) {
                    return "WR";
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        for (int whatRook = 1; whatRook <= numberOfBlackRooks || whatRook <= 2; whatRook++) {
            try {
                if (pieces.get("black rook - " + whatRook).location.equals(location)) {
                    return "BR";
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        for (int whatPawn = 1; whatPawn <= numberOfWhitePawns || whatPawn <= 8; whatPawn++) {
            try {

                if (pieces.get("white pawn - " + whatPawn).location.equals(location)) {
                    return "WP";
                }
            } catch (Exception e) {

            }
        }

        for (int whatPawn = 1; whatPawn <= numberOfBlackPawns || whatPawn <= 8; whatPawn++) {
            try {

                if (pieces.get("black pawn - " + whatPawn).location.equals(location)) {
                    return "BP";
                }
            } catch (Exception e) {

            }
        }
        for (int whatKnight = 1; whatKnight <= numberOfWhiteKnights || whatKnight <= 2; whatKnight++) {
            try {
                if (pieces.get("white knight - " + whatKnight).location.equals(location)) {
                    return "WN";
                }
            } catch (Exception e) {

            }
        }
        for (int whatKnight = 1; whatKnight <= numberOfBlackKnights || whatKnight <= 2; whatKnight++) {
            try {
                if (pieces.get("black knight - " + whatKnight).location.equals(location)) {
                    return "BN";
                }
            } catch (Exception e) {

            }
        }

        for (int whatBishop = 1; whatBishop <= numberOfWhiteBishops || whatBishop <= 2; whatBishop++) {
            try {
                if (pieces.get("white bishop - " + whatBishop).location.equals(location)) {
                    return "WB";
                }
            } catch (Exception e) {

            }
        }
        for (int whatBishop = 1; whatBishop <= numberOfBlackBishops || whatBishop <= 2; whatBishop++) {
            try {
                if (pieces.get("black bishop - " + whatBishop).location.equals(location)) {
                    return "BB";
                }
            } catch (Exception e) {

            }
        }
        for (int whatQueen = 1; whatQueen <= numberOfWhiteQueens || whatQueen <= 1; whatQueen++) {
            try {
                if (pieces.get("white queen - " + whatQueen).location.equals(location)) {
                    return "WQ";
                }
            } catch (Exception e) {

            }
        }
        for (int whatQueen = 1; whatQueen <= numberOfBlackQueens || whatQueen <= 1; whatQueen++) {
            try {
                if (pieces.get("black queen - " + whatQueen).location.equals(location)) {
                    return "BQ";
                }
            } catch (Exception e) {

            }
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
        for (int whatRook = 1; whatRook <= numberOfWhiteRooks || whatRook <= 2; whatRook++) {
            try {
                if (pieces.get("white rook - " + whatRook).location.equals(location)) {
                    return "1white rook - " + whatRook;
                }
            } catch (Exception e) {

            }
        }
        for (int whatRook = 1; whatRook <= numberOfBlackRooks || whatRook <= 2; whatRook++) {
            try {
                if (pieces.get("black rook - " + whatRook).location.equals(location)) {
                    return "2black rook - " + whatRook;
                }
            } catch (Exception e) {

            }
        }
        for (int whatPawn = 1; whatPawn <= numberOfWhitePawns || whatPawn <= 8; whatPawn++) {
            try {
                if (pieces.get("white pawn - " + whatPawn).location.equals(location)) {
                    return "1white pawn - " + whatPawn;
                }
            } catch (Exception e) {

            }
        }
        for (int whatPawn = 1; whatPawn <= numberOfBlackPawns || whatPawn <= 8; whatPawn++) {
            try {
                if (pieces.get("black pawn - " + whatPawn).location.equals(location)) {
                    return "2black pawn - " + whatPawn;
                }
            } catch (Exception e) {

            }
        }
        for (int whatKnight = 1; whatKnight <= numberOfWhiteKnights || whatKnight <= 2; whatKnight++) {
            try {
                if (pieces.get("white knight - " + whatKnight).location.equals(location)) {
                    return "1white knight - " + whatKnight;
                }
            } catch (Exception e) {

            }
        }
        for (int whatKnight = 1; whatKnight <= numberOfBlackKnights || whatKnight <= 2; whatKnight++) {
            try {
                if (pieces.get("black knight - " + whatKnight).location.equals(location)) {
                    return "2black knight - " + whatKnight;
                }
            } catch (Exception e) {

            }
        }
        for (int whatBishop = 1; whatBishop <= numberOfWhiteBishops || whatBishop <= 2; whatBishop++) {
            try {
                if (pieces.get("white bishop - " + whatBishop).location.equals(location)) {
                    return "1white bishop - " + whatBishop;
                }
            } catch (Exception e) {

            }
        }
        for (int whatBishop = 1; whatBishop <= numberOfBlackBishops || whatBishop <= 2; whatBishop++) {
            try {
                if (pieces.get("black bishop - " + whatBishop).location.equals(location)) {
                    return "2black bishop - " + whatBishop;
                }
            } catch (Exception e) {

            }
        }
        for (int whatQueen = 1; whatQueen <= numberOfWhiteQueens || whatQueen <= 1; whatQueen++) {
            try {
                if (pieces.get("white queen - " + whatQueen).location.equals(location)) {
                    return "1white queen - " + whatQueen;
                }
            } catch (Exception e) {

            }
        }
        for (int whatQueen = 1; whatQueen <= numberOfBlackQueens || whatQueen <= 1; whatQueen++) {
            try {
                if (pieces.get("black queen - " + whatQueen).location.equals(location)) {
                    return "2black queen - " + whatQueen;
                }
            } catch (Exception e) {

            }
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

    private void createKnight() {
        pieces.put("white knight - 1", new knight("b1", "white knight"));
        pieces.put("white knight - 2", new knight("g1", "white knight"));
        pieces.put("black knight - 1", new knight("b8", "black knight"));
        pieces.put("black knight - 2", new knight("g8", "black knight"));

    }

    private void createBishop() {
        pieces.put("white bishop - 1", new bishop("c1", "white bishop"));
        pieces.put("white bishop - 2", new bishop("f1", "white bishop"));
        pieces.put("black bishop - 1", new bishop("c8", "black bishop"));
        pieces.put("black bishop - 2", new bishop("f8", "black bishop"));
    }

    private void createQueen() {
        pieces.put("white queen - 1", new queen("d1", "white queen - 1"));

        pieces.put("black queen - 1", new queen("d8", "black queen - 1"));
    }

    private void createKing() {
        pieces.put("white king", new king("e1", "white king"));
        pieces.put("black king", new king("e8", "black king"));

    }

}