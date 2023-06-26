package chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class board {
    /*
     * todo
     * blocking works
     * 
     * captues do not
     * i need to check if king can move out of a check
     * 
     * 
     * make code more modular
     * error handling
     */
    public HashMap<String, pieces> pieces = new HashMap<String, pieces>();
    public HashMap<String, Integer> numberOfEachPiece = new HashMap<String, Integer>();
    String[] whiteAndBlack = { "white", "black" };
    String whoseMove = "white";
    private Scanner in = new Scanner(System.in);
    private String userInput;
    public String pgn = "";
    public int moveCounter = 1;
    private boolean isKingUnderCheck = false;

    public void moveByMove() {
        // if it doesn't then we continue
        // if it does then we wont move the piece and we will inform the player they
        // cant make that move
        System.out.println("please enter the move for " + whoseMove);
        userInput = in.nextLine();
        char[] userInputArr = userInput.toCharArray();
        String[] letterArr = { "a", "b", "c", "d", "e", "f", "g", "h" };
        int[] numberArr = { 1, 2, 3, 4, 5, 6, 7, 8 };
        Boolean done = false;

        if (userInput.substring(userInput.length() - 1, userInput.length()).equals("+")) {
            userInput = userInput.substring(0, userInput.length() - 1);
        }

        {
            // its a pawn move
            if (userInput.length() == 2) {
                for (int j = 1; j <= numberOfEachPiece.get(whoseMove + "Pawns"); j++) {
                    try {
                        pawnValidMoves(whoseMove + " pawn - " + j);
                    } catch (Exception e) {

                    }

                }
                for (int j = 1; j <= numberOfEachPiece.get(whoseMove + "Pawns"); j++) {
                    try {
                        if (pieces.get(whoseMove + " pawn - " + j).validMoves.contains(userInput)) {
                            isKingUnderCheck = willKingBeUnderCheck(
                                    pieces.get(whoseMove + " pawn - " + j).getLocation(), userInput);
                            if (!isKingUnderCheck) {
                                pieces.get(whoseMove + " pawn - " + j).setLocation(userInput);
                                pieces.get(whoseMove + " pawn - " + j).moreThenOnce = true;
                            } else {
                                System.out.println("king is under Check");
                            }

                        }
                    } catch (Exception e) {

                    }

                }

            }

            // pawn capture
            if (userInput.length() == 4 && userInputArr[1] == 'x') {
                for (int whatPawn = 1; whatPawn <= 8; whatPawn++) {
                    try {
                        if (pieces.get(whoseMove + " pawn - " + whatPawn).getLocation().substring(0, 1)
                                .equals(userInput.subSequence(0, 1))) {

                            // now we know what pawn is capturing

                            // so then we need to find the pawn in the hashmap

                            pawnValidMoves(whoseMove + " pawn - " + whatPawn);
                            // match user input to the pawns valid move

                            for (int i = 0; i < pieces.get(whoseMove + " pawn - " + whatPawn).validMoves.size(); i++) {

                                if (pieces.get(whoseMove + " pawn - " + whatPawn).validMoves.get(i)
                                        .equals(userInput.substring(2))) {
                                    isKingUnderCheck = willKingBeUnderCheck(
                                            pieces.get(whoseMove + " pawn - " + whatPawn).getLocation(),
                                            userInput.substring(2));
                                    if (!isKingUnderCheck) {
                                        removePiece(userInput.substring(2));
                                        pieces.get(whoseMove + " pawn - " + whatPawn)
                                                .setLocation(userInput.substring(2));
                                        pieces.get(whoseMove + " pawn - " + whatPawn).moreThenOnce = true;
                                        break;
                                    }
                                }
                            }

                        }
                    } catch (Exception e) {

                    }

                }

            }

            // pawn Promote with move
            if (userInput.length() == 4 && userInputArr[2] == '=') {
                for (int whatPawn = 1; whatPawn <= 8; whatPawn++) {
                    try {
                        if (pieces.get(whoseMove + " pawn - " + whatPawn).getLocation().substring(0, 1)
                                .equals(userInput.subSequence(0, 1))) {

                            // now we know what pawn is capturing

                            // so then we need to find the pawn in the hashmap

                            pawnValidMoves(whoseMove + " pawn - " + whatPawn);
                            // match user input to the pawns valid move

                            for (int i = 0; i < pieces.get(whoseMove + " pawn - " + whatPawn).validMoves.size(); i++) {

                                if (pieces.get(whoseMove + " pawn - " + whatPawn).validMoves.get(i)
                                        .equals(userInput.substring(0, 2))) {
                                    isKingUnderCheck = willKingBeUnderCheck(
                                            pieces.get(whoseMove + " pawn - " + whatPawn).getLocation(),
                                            userInput.substring(0, 2));
                                    if (!isKingUnderCheck) {
                                        pawnPromote(whoseMove + " pawn - " + whatPawn, true);
                                    }
                                    break;

                                }
                            }

                        }
                    } catch (Exception e) {

                    }

                }
            }

            // pawn promote with capture
            if (userInput.length() == 6 && userInputArr[4] == '=') {
                for (int whatPawn = 1; whatPawn <= 8; whatPawn++) {
                    try {
                        if (pieces.get(whoseMove + " pawn - " + whatPawn).getLocation().substring(0, 1)
                                .equals(userInput.subSequence(0, 1))) {

                            // now we know what pawn is capturing

                            // so then we need to find the pawn in the hashmap

                            pawnValidMoves(whoseMove + " pawn - " + whatPawn);
                            // match user input to the pawns valid move

                            for (int i = 0; i < pieces.get(whoseMove + " pawn - " + whatPawn).validMoves.size(); i++) {

                                if (pieces.get(whoseMove + " pawn - " + whatPawn).validMoves.get(i)
                                        .equals(userInput.substring(2, 4))) {
                                    isKingUnderCheck = willKingBeUnderCheck(
                                            pieces.get(whoseMove + " pawn - " + whatPawn).getLocation(),
                                            userInput.substring(2, 4));
                                    if (!isKingUnderCheck) {
                                        removePiece(userInput.substring(2, 4));
                                        pawnPromote(whoseMove + " pawn - " + whatPawn, false);
                                    }

                                    break;

                                }
                            }

                        }
                    } catch (Exception e) {

                    }

                }
            }

            // big piece move
            if (userInput.length() == 3) {
                switch (userInputArr[0]) {
                    case 'K':
                        // find valid move for piece
                        kingValidMove(whoseMove + " king");
                        // check if a valid move matches user input
                        for (int i = 0; i < 8; i++) {
                            try {
                                if (pieces.get(whoseMove + " king").validMoves.get(i).equals(userInput.substring(1))) {
                                    // move the piece
                                    isKingUnderCheck = willKingBeUnderCheck(
                                            pieces.get(whoseMove + " king").getLocation(), userInput.substring(1));
                                    if (!isKingUnderCheck) {
                                        pieces.get(whoseMove + " king").setLocation(userInput.substring(1));

                                    }
                                }
                            } catch (Exception e) {

                            }

                        }

                        break;
                    case 'Q':
                        // find valid move for piece
                        for (int whatQueen = 1; whatQueen <= numberOfEachPiece.get(whoseMove + "Queens"); whatQueen++) {

                            queenValidMove(whoseMove + " queen - " + whatQueen);
                        }
                        // check if a valid move matches user input
                        for (int whatQueen = 1; whatQueen <= numberOfEachPiece.get(whoseMove + "Queens"); whatQueen++) {
                            try {
                                for (int validMove = 0; validMove < pieces
                                        .get(whoseMove + " queen - " + whatQueen).validMoves
                                        .size(); validMove++) {
                                    if (pieces.get(whoseMove + " queen - " + whatQueen).validMoves.get(validMove)
                                            .equals(userInput.substring(1))) {
                                        isKingUnderCheck = willKingBeUnderCheck(
                                                pieces.get(whoseMove + " queen - " + whatQueen).getLocation(),
                                                userInput.substring(1));
                                        if (!isKingUnderCheck) {
                                            pieces.get(whoseMove + " queen - " + whatQueen)
                                                    .setLocation(userInput.substring(1));
                                        }

                                    }
                                }
                            } catch (Exception e) {

                            }
                        }

                        break;
                    case 'N':
                        // find valid move for piece
                        for (int whatKnight = 1; whatKnight <= numberOfEachPiece
                                .get(whoseMove + "Knights"); whatKnight++) {
                            knightValidMove(whoseMove + " knight - " + whatKnight);
                        }

                        // check if a valid move matches user input

                        for (int j = 1; j <= numberOfEachPiece.get(whoseMove + "Knights"); j++) {
                            try {
                                for (int i = 0; i < pieces.get(whoseMove + " knight - " + j).validMoves.size(); i++) {
                                    if (pieces.get(whoseMove + " knight - " + j).validMoves.get(i)
                                            .equals(userInput.substring(1))) {
                                        // move the piece
                                        isKingUnderCheck = willKingBeUnderCheck(
                                                pieces.get(whoseMove + " knight - " + j).getLocation(),
                                                userInput.substring(1));
                                        if (!isKingUnderCheck) {

                                            pieces.get(whoseMove + " knight - " + j)
                                                    .setLocation(userInput.substring(1));
                                        }
                                    }
                                }
                            } catch (Exception e) {

                            }

                        }
                        break;
                    case 'B':
                        // find valid move for piece
                        for (int i = 1; i <= numberOfEachPiece.get(whoseMove + "Bishops"); i++) {
                            try {
                                bishopValidMove(whoseMove + " bishop - " + i);

                            } catch (Exception e) {

                            }
                        }

                        // check if a valid move matches user input
                        for (int whatBishop = 1; whatBishop <= numberOfEachPiece
                                .get(whoseMove + "Bishops"); whatBishop++) {
                            try {
                                for (int i = 0; i < pieces.get(whoseMove + " bishop - " + whatBishop).validMoves
                                        .size(); i++) {
                                    if (pieces.get(whoseMove + " bishop - " + whatBishop).validMoves.get(i)
                                            .equals(userInput.substring(1))) {
                                        isKingUnderCheck = willKingBeUnderCheck(
                                                pieces.get(whoseMove + " bishop - " + whatBishop).getLocation(),
                                                userInput.substring(1));
                                        if (!isKingUnderCheck) {

                                            // move the piece
                                            pieces.get(whoseMove + " bishop - " + whatBishop)
                                                    .setLocation(userInput.substring(1));

                                        }
                                    }
                                }
                            } catch (Exception e) {

                            }

                        }

                        break;
                    case 'R':
                        // find valid move for piece
                        for (int i = 0; i < numberArr.length; i++) {
                            try {
                                rookValidMove(whoseMove + " rook - " + i);
                            } catch (Exception e) {

                            }
                        }

                        // check if a valid move matches user input
                        for (int whatRook = 1; whatRook <= numberOfEachPiece.get(whoseMove + "Rooks"); whatRook++) {
                            try {
                                for (int k = 0; k < pieces.get(whoseMove + " rook - " + whatRook).validMoves
                                        .size(); k++) {
                                    if (pieces.get(whoseMove + " rook - " + whatRook).validMoves.get(k)
                                            .equals(userInput.substring(1))) {
                                        isKingUnderCheck = willKingBeUnderCheck(
                                                pieces.get(whoseMove + " rook - " + whatRook).getLocation(),
                                                userInput.substring(1));
                                        if (!isKingUnderCheck) {
                                            // move the piece
                                            pieces.get(whoseMove + " rook - " + whatRook)
                                                    .setLocation(userInput.substring(1));
                                        }
                                    }
                                }
                            } catch (Exception e) {

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
                        kingValidMove(whoseMove + " king");

                        // check if a valid move matches user input
                        for (int i = 0; i < 8; i++) {
                            try {
                                if (pieces.get(whoseMove + " king").validMoves.get(i).equals(userInput.substring(2))) {
                                    // remove the captured piece\
                                    removePiece(userInput.substring(2));

                                    isKingUnderCheck = willKingBeUnderCheck(
                                            pieces.get(whoseMove + " king").getLocation(),
                                            userInput.substring(2));
                                    if (!isKingUnderCheck) {
                                        // move the piece
                                        removePiece(userInput.substring(2));
                                        pieces.get(whoseMove + " king").setLocation(userInput.substring(2));
                                    }
                                    break;
                                }
                            } catch (Exception e) {

                            }
                        }
                        // move the piece
                        break;
                    case 'Q':
                        // find valid move for piece
                        for (int i = 1; i <= numberOfEachPiece.get(whoseMove + "Queens"); i++) {

                            queenValidMove(whoseMove + " queen - " + i);
                        }

                        // check if a valid move matches user input
                        for (int whatQueen = 0; whatQueen < numberArr.length; whatQueen++) {
                            try {
                                for (int whatValidMove = 0; whatValidMove < pieces
                                        .get(whoseMove + " queen - " + whatQueen).validMoves
                                        .size(); whatValidMove++) {
                                    try {
                                        if (pieces.get(whoseMove + " queen - " + whatQueen).validMoves
                                                .get(whatValidMove)
                                                .equals(userInput.substring(2))) {
                                            isKingUnderCheck = willKingBeUnderCheck(
                                                    pieces.get(whoseMove + " queen - " + whatQueen).getLocation(),
                                                    userInput.substring(2));
                                            if (!isKingUnderCheck) {
                                                // remove the captured piece\
                                                removePiece(userInput.substring(2));
                                                // move the piece
                                                pieces.get(whoseMove + " queen - " + whatQueen)
                                                        .setLocation(userInput.substring(2));
                                            }

                                            break;
                                        }
                                    } catch (Exception e) {

                                    }
                                }
                            } catch (Exception e) {

                            }

                        }

                        break;
                    case 'N':
                        // find valid move for piece
                        for (int whatKnight = 1; whatKnight <= numberOfEachPiece
                                .get(whoseMove + "Knights"); whatKnight++) {
                            knightValidMove(whoseMove + " knight - " + whatKnight);
                        }

                        // check if a valid move matches user input

                        for (int whatKnight = 1; whatKnight <= numberOfEachPiece
                                .get(whoseMove + "Knights"); whatKnight++) {
                            try {
                                for (int i = 0; i < pieces.get(whoseMove + " knight - " + whatKnight).validMoves
                                        .size(); i++) {
                                    if (pieces.get(whoseMove + " knight - " + whatKnight).validMoves.get(i)
                                            .equals(userInput.substring(2))) {
                                        isKingUnderCheck = willKingBeUnderCheck(
                                                pieces.get(whoseMove + " knight - " + whatKnight).getLocation(),
                                                userInput.substring(2));
                                        if (!isKingUnderCheck) {
                                            // move the piece
                                            removePiece(userInput.substring(2));
                                            pieces.get(whoseMove + " knight - " + whatKnight)
                                                    .setLocation(userInput.substring(2));

                                        }
                                    }
                                }
                            } catch (Exception e) {

                            }

                        }
                        break;
                    case 'B':
                        // find valid move for piece
                        for (int whatBishop = 1; whatBishop <= numberOfEachPiece
                                .get(whoseMove + "Bishops"); whatBishop++) {
                            try {
                                bishopValidMove(whoseMove + " bishop - " + whatBishop);

                            } catch (Exception e) {

                            }
                        }

                        // check if a valid move matches user input
                        for (int whatBishop = 1; whatBishop <= numberOfEachPiece
                                .get(whoseMove + "Bishops"); whatBishop++) {
                            try {
                                for (int validMove = 0; validMove < pieces
                                        .get(whoseMove + " bishop - " + whatBishop).validMoves
                                        .size(); validMove++) {
                                    if (pieces.get(whoseMove + " bishop - " + whatBishop).validMoves.get(validMove)
                                            .equals(userInput.substring(2))) {

                                        isKingUnderCheck = willKingBeUnderCheck(
                                                pieces.get(whoseMove + " bishop - " + whatBishop).getLocation(),
                                                userInput.substring(2));
                                        if (!isKingUnderCheck) {
                                            // move the piece
                                            removePiece(userInput.substring(2));
                                            pieces.get(whoseMove + " bishop - " + whatBishop)
                                                    .setLocation(userInput.substring(2));

                                        }

                                    }
                                }
                            } catch (Exception e) {

                            }

                        }
                        break;
                    case 'R':
                        for (int whatRook = 1; whatRook <= numberOfEachPiece.get(whoseMove + "Rooks"); whatRook++) {

                            try {
                                rookValidMove(whoseMove + " rook - " + whatRook);
                            } catch (Exception e) {

                            }
                        }

                        for (int whatRook = 1; whatRook <= numberOfEachPiece.get(whoseMove + "Rooks"); whatRook++) {
                            // check if a valid move matches user input
                            try {
                                for (int i = 0; i < pieces.get(whoseMove + " rook - " + whatRook).validMoves
                                        .size(); i++) {
                                    if (pieces.get(whoseMove + " rook - " + whatRook).validMoves.get(i)
                                            .equals(userInput.substring(2))) {
                                        isKingUnderCheck = willKingBeUnderCheck(
                                                pieces.get(whoseMove + " rook - " + whatRook).getLocation(),
                                                userInput.substring(2));
                                        if (!isKingUnderCheck) {
                                            // move the piece
                                            removePiece(userInput.substring(2));
                                            pieces.get(whoseMove + " rook - " + whatRook)
                                                    .setLocation(userInput.substring(2));
                                        }
                                    }
                                }
                            } catch (Exception e) {

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
                    case 'Q':
                        // find the witch knight

                        // letter look
                        for (int LetterLocation = 0; LetterLocation < letterArr.length && !done; LetterLocation++) {
                            if (userInput.substring(1, 2).equals(letterArr[LetterLocation])) {
                                for (int whatQueen = 1; whatQueen <= numberOfEachPiece.get(whoseMove + "Queens")
                                        && !done; whatQueen++) {
                                    try {
                                        if (pieces.get(whoseMove + " queen - " + whatQueen).getLocation()
                                                .substring(0, 1)
                                                .equals(userInput.substring(1, 2))) {
                                            queenValidMove(whoseMove + " queen - " + whatQueen);

                                            try {
                                                for (int a = 0; a < pieces
                                                        .get(whoseMove + " queen - " + whatQueen).validMoves
                                                        .size()
                                                        && !done; a++) {
                                                    if (pieces.get(whoseMove + " queen - " + whatQueen).validMoves
                                                            .get(a)
                                                            .equals(userInput.substring(2))) {
                                                        // move the piece
                                                        isKingUnderCheck = willKingBeUnderCheck(
                                                                pieces.get(whoseMove + " queen - " + whatQueen)
                                                                        .getLocation(),
                                                                userInput.substring(2));
                                                        if (!isKingUnderCheck) {

                                                            pieces.get(whoseMove + " queen - " + whatQueen)
                                                                    .setLocation(userInput.substring(2));
                                                            done = true;
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {

                                            }

                                        }
                                    } catch (Exception e) {

                                    }

                                }
                            }
                        }
                        // number look
                        done = false;
                        try {
                            for (int numberLocation = 1; numberLocation < numberArr.length && !done; numberLocation++) {
                                if (Integer.parseInt(userInput.substring(1, 2)) == numberArr[numberLocation]) {
                                    for (int whatQueen = 1; whatQueen <= numberOfEachPiece.get(whoseMove + "Queens")
                                            && !done; whatQueen++) {
                                        try {
                                            if (pieces.get(whoseMove + " queen - " + whatQueen).getLocation()
                                                    .substring(1, 2)
                                                    .equals(userInput.substring(1, 2))) {
                                                queenValidMove(whoseMove + " queen - " + whatQueen);

                                                try {
                                                    for (int a = 0; a < pieces
                                                            .get(whoseMove + " queen - " + whatQueen).validMoves
                                                            .size() && !done; a++) {
                                                        if (pieces.get(whoseMove + " queen - " + whatQueen).validMoves
                                                                .get(a)
                                                                .equals(userInput.substring(2))) {
                                                            // move the piece

                                                            pieces.get(whoseMove + " queen - " + whatQueen)
                                                                    .setLocation(userInput.substring(2));
                                                            done = true;
                                                            break;

                                                        }
                                                    }
                                                } catch (Exception e) {

                                                }

                                            }
                                        } catch (Exception e) {

                                        }

                                    }
                                }
                            }
                        } catch (Exception e) {

                        }
                        break;
                    case 'N':
                        // find the witch knight

                        // letter look
                        for (int LetterLocation = 0; LetterLocation < letterArr.length && !done; LetterLocation++) {
                            if (userInput.substring(1, 2).equals(letterArr[LetterLocation])) {
                                for (int whatKnight = 1; whatKnight <= numberOfEachPiece.get(whoseMove + "Knights")
                                        && !done; whatKnight++) {
                                    try {
                                        if (pieces.get(whoseMove + " knight - " + whatKnight).getLocation()
                                                .substring(0, 1)
                                                .equals(userInput.substring(1, 2))) {
                                            knightValidMove(whoseMove + " knight - " + whatKnight);

                                            try {
                                                for (int a = 0; a < pieces
                                                        .get(whoseMove + " knight - " + whatKnight).validMoves
                                                        .size()
                                                        && !done; a++) {
                                                    if (pieces.get(whoseMove + " knight - " + whatKnight).validMoves
                                                            .get(a)
                                                            .equals(userInput.substring(2))) {
                                                        // move the piece
                                                        isKingUnderCheck = willKingBeUnderCheck(
                                                                pieces.get(whoseMove + " knight - " + whatKnight)
                                                                        .getLocation(),
                                                                userInput.substring(2));
                                                        if (!isKingUnderCheck) {
                                                            pieces.get(whoseMove + " knight - " + whatKnight)
                                                                    .setLocation(userInput.substring(2));
                                                            done = true;
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {

                                            }

                                        }
                                    } catch (Exception e) {

                                    }

                                }
                            }
                        }
                        // number look
                        done = false;
                        try {
                            for (int numberLocation = 1; numberLocation < numberArr.length && !done; numberLocation++) {
                                if (Integer.parseInt(userInput.substring(1, 2)) == numberArr[numberLocation - 1]) {
                                    for (int whatKnight = 1; whatKnight <= numberOfEachPiece.get(whoseMove + "Knights")
                                            && !done; whatKnight++) {
                                        try {
                                            if (pieces.get(whoseMove + " knight - " + whatKnight).getLocation()
                                                    .substring(1, 2)
                                                    .equals(userInput.substring(1, 2))) {
                                                knightValidMove(whoseMove + " knight - " + whatKnight);

                                                try {
                                                    for (int a = 0; a < pieces
                                                            .get(whoseMove + " knight - " + whatKnight).validMoves
                                                            .size() && !done; a++) {
                                                        if (pieces.get(whoseMove + " knight - " + whatKnight).validMoves
                                                                .get(a)
                                                                .equals(userInput.substring(2))) {
                                                            // move the piece

                                                            pieces.get(whoseMove + " knight - " + whatKnight)
                                                                    .setLocation(userInput.substring(2));
                                                            done = true;
                                                            break;

                                                        }
                                                    }
                                                } catch (Exception e) {

                                                }

                                            }
                                        } catch (Exception e) {

                                        }

                                    }
                                }
                            }
                        } catch (Exception e) {

                        }

                        break;

                    case 'B':
                        // find the witch bishop

                        // letter look
                        for (int LetterLocation = 0; LetterLocation < letterArr.length && !done; LetterLocation++) {
                            if (userInput.substring(1, 2).equals(letterArr[LetterLocation])) {
                                for (int whatBishop = 1; whatBishop <= numberOfEachPiece.get(whoseMove + "Bishops")
                                        && !done; whatBishop++) {
                                    try {
                                        if (pieces.get(whoseMove + " bishop - " + whatBishop).getLocation()
                                                .substring(0, 1)
                                                .equals(userInput.substring(1, 2))) {
                                            bishopValidMove(whoseMove + " bishop - " + whatBishop);
                                            try {
                                                for (int a = 0; a < pieces
                                                        .get(whoseMove + " bishop - " + whatBishop).validMoves
                                                        .size()
                                                        && !done; a++) {
                                                    if (pieces.get(whoseMove + " bishop - " + whatBishop).validMoves
                                                            .get(a)
                                                            .equals(userInput.substring(2))) {
                                                        // move the piece
                                                        isKingUnderCheck = willKingBeUnderCheck(
                                                                pieces.get(whoseMove + " bishop - " + whatBishop)
                                                                        .getLocation(),
                                                                userInput.substring(2));
                                                        if (!isKingUnderCheck) {
                                                            pieces.get(whoseMove + " bishop - " + whatBishop)
                                                                    .setLocation(userInput.substring(2));
                                                            done = true;
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {

                                            }
                                        }
                                    } catch (Exception e) {

                                    }

                                }
                            }
                        }
                        // number look
                        done = false;
                        try {
                            for (int numberLocation = 1; numberLocation < numberArr.length && !done; numberLocation++) {
                                if (Integer.parseInt(userInput.substring(1, 2)) == numberArr[numberLocation - 1]) {
                                    for (int whatBishop = 1; whatBishop <= numberOfEachPiece.get(whoseMove + "Bishops")
                                            && !done; whatBishop++) {
                                        try {
                                            if (pieces.get(whoseMove + " bishop - " + whatBishop).getLocation()
                                                    .substring(1, 2)
                                                    .equals(userInput.substring(1, 2))) {
                                                bishopValidMove(whoseMove + " bishop - " + whatBishop);

                                                try {
                                                    for (int a = 0; a < pieces
                                                            .get(whoseMove + " bishop - " + whatBishop).validMoves
                                                            .size() && !done; a++) {
                                                        if (pieces.get(whoseMove + " bishop - " + whatBishop).validMoves
                                                                .get(a)
                                                                .equals(userInput.substring(2))) {
                                                            // move the piece

                                                            pieces.get(whoseMove + " bishop - " + whatBishop)
                                                                    .setLocation(userInput.substring(2));
                                                            done = true;
                                                            break;

                                                        }
                                                    }
                                                } catch (Exception e) {

                                                }

                                            }
                                        } catch (Exception e) {

                                        }

                                    }
                                }
                            }
                        } catch (Exception e) {

                        }

                        break;
                    case 'R':

                        // find the witch rook

                        // letter look
                        for (int LetterLocation = 0; LetterLocation < letterArr.length && !done; LetterLocation++) {
                            if (userInput.substring(1, 2).equals(letterArr[LetterLocation])) {
                                for (int whatRook = 1; whatRook <= numberOfEachPiece.get(whoseMove + "Bishops")
                                        && !done; whatRook++) {
                                    try {
                                        if (pieces.get(whoseMove + " rook - " + whatRook).getLocation().substring(0, 1)
                                                .equals(userInput.substring(1, 2))) {
                                            rookValidMove(whoseMove + " rook - " + whatRook);
                                            try {
                                                for (int a = 0; a < pieces
                                                        .get(whoseMove + " rook - " + whatRook).validMoves
                                                        .size()
                                                        && !done; a++) {
                                                    if (pieces.get(whoseMove + " rook - " + whatRook).validMoves.get(a)
                                                            .equals(userInput.substring(2))) {
                                                        // move the piece
                                                        isKingUnderCheck = willKingBeUnderCheck(
                                                                pieces.get(whoseMove + " rook - " + whatRook)
                                                                        .getLocation(),
                                                                userInput.substring(2));
                                                        if (!isKingUnderCheck) {
                                                            pieces.get(whoseMove + " rook - " + whatRook)
                                                                    .setLocation(userInput.substring(2));
                                                            done = true;
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {

                                            }
                                        }
                                    } catch (Exception e) {

                                    }

                                }
                            }
                        }
                        // number look
                        done = false;
                        try {
                            for (int numberLocation = 1; numberLocation < numberArr.length && !done; numberLocation++) {
                                if (Integer.parseInt(userInput.substring(1, 2)) == numberArr[numberLocation - 1]) {
                                    for (int whatRook = 1; whatRook <= numberOfEachPiece.get(whoseMove + "Rooks")
                                            && !done; whatRook++) {
                                        try {
                                            if (pieces.get(whoseMove + " rook - " + whatRook).getLocation()
                                                    .substring(1, 2)
                                                    .equals(userInput.substring(1, 2))) {
                                                rookValidMove(whoseMove + " rook - " + whatRook);

                                                try {
                                                    for (int a = 0; a < pieces
                                                            .get(whoseMove + " rook - " + whatRook).validMoves
                                                            .size() && !done; a++) {
                                                        if (pieces.get(whoseMove + " rook - " + whatRook).validMoves
                                                                .get(a)
                                                                .equals(userInput.substring(2))) {
                                                            // move the piece

                                                            pieces.get(whoseMove + " rook - " + whatRook)
                                                                    .setLocation(userInput.substring(2));
                                                            done = true;
                                                            break;

                                                        }
                                                    }
                                                } catch (Exception e) {

                                                }

                                            }
                                        } catch (Exception e) {

                                        }

                                    }
                                }
                            }
                        } catch (Exception e) {

                        }

                        break;

                    default:
                        break;
                }
            }

            // if two pieces big pieces can capture the same location
            if (userInput.length() == 5 && userInputArr[2] == 'x') {
                switch (userInputArr[0]) {
                    case 'Q':
                        // find the witch knight

                        // letter look
                        for (int LetterLocation = 0; LetterLocation < letterArr.length && !done; LetterLocation++) {
                            if (userInput.substring(1, 2).equals(letterArr[LetterLocation])) {
                                for (int whatQueen = 1; whatQueen <= numberOfEachPiece.get(whoseMove + "Queens")
                                        && !done; whatQueen++) {
                                    try {
                                        if (pieces.get(whoseMove + " queen - " + whatQueen).getLocation()
                                                .substring(0, 1)
                                                .equals(userInput.substring(1, 2))) {
                                            queenValidMove(whoseMove + " queen - " + whatQueen);

                                            try {
                                                for (int a = 0; a < pieces
                                                        .get(whoseMove + " queen - " + whatQueen).validMoves
                                                        .size()
                                                        && !done; a++) {
                                                    if (pieces.get(whoseMove + " queen - " + whatQueen).validMoves
                                                            .get(a)
                                                            .equals(userInput.substring(3))) {
                                                        // move the piece
                                                        isKingUnderCheck = willKingBeUnderCheck(
                                                                pieces.get(whoseMove + " queen - " + whatQueen)
                                                                        .getLocation(),
                                                                userInput.substring(3));
                                                        if (!isKingUnderCheck) {
                                                            pieces.get(whoseMove + " queen - " + whatQueen)
                                                                    .setLocation(userInput.substring(3));
                                                            done = true;
                                                        }

                                                    }
                                                }
                                            } catch (Exception e) {

                                            }

                                        }
                                    } catch (Exception e) {

                                    }

                                }
                            }
                        }
                        // number look
                        done = false;
                        try {
                            for (int numberLocation = 1; numberLocation < numberArr.length && !done; numberLocation++) {
                                if (Integer.parseInt(userInput.substring(1, 2)) == numberArr[numberLocation - 1]) {
                                    for (int whatQueen = 1; whatQueen <= numberOfEachPiece.get(whoseMove + "Queens")
                                            && !done; whatQueen++) {
                                        try {
                                            if (pieces.get(whoseMove + " queen - " + whatQueen).getLocation()
                                                    .substring(1, 2)
                                                    .equals(userInput.substring(1, 2))) {
                                                queenValidMove(whoseMove + " queen - " + whatQueen);

                                                try {
                                                    for (int a = 0; a < pieces
                                                            .get(whoseMove + " queen - " + whatQueen).validMoves
                                                            .size() && !done; a++) {
                                                        if (pieces.get(whoseMove + " queen - " + whatQueen).validMoves
                                                                .get(a)
                                                                .equals(userInput.substring(3))) {
                                                            // move the piece

                                                            isKingUnderCheck = willKingBeUnderCheck(
                                                                    pieces.get(whoseMove + " queen - " + whatQueen)
                                                                            .getLocation(),
                                                                    userInput.substring(3));
                                                            if (!isKingUnderCheck) {
                                                                pieces.get(whoseMove + " queen - " + whatQueen)
                                                                        .setLocation(userInput.substring(3));
                                                                done = true;
                                                            }
                                                            break;

                                                        }
                                                    }
                                                } catch (Exception e) {

                                                }

                                            }
                                        } catch (Exception e) {

                                        }

                                    }
                                }
                            }
                        } catch (Exception e) {

                        }
                        break;
                    case 'N':
                        // find the witch knight

                        // letter look
                        for (int LetterLocation = 0; LetterLocation < letterArr.length && !done; LetterLocation++) {
                            if (userInput.substring(1, 2).equals(letterArr[LetterLocation])) {
                                for (int whatKnight = 1; whatKnight <= numberOfEachPiece.get(whoseMove + "Knights")
                                        && !done; whatKnight++) {
                                    try {
                                        if (pieces.get(whoseMove + " knight - " + whatKnight).getLocation()
                                                .substring(0, 1)
                                                .equals(userInput.substring(1, 2))) {
                                            knightValidMove(whoseMove + " knight - " + whatKnight);

                                            try {
                                                for (int a = 0; a < pieces
                                                        .get(whoseMove + " knight - " + whatKnight).validMoves
                                                        .size()
                                                        && !done; a++) {
                                                    if (pieces.get(whoseMove + " knight - " + whatKnight).validMoves
                                                            .get(a)
                                                            .equals(userInput.substring(3))) {
                                                        // move the piece
                                                        isKingUnderCheck = willKingBeUnderCheck(
                                                                pieces.get(whoseMove + " knight - " + whatKnight)
                                                                        .getLocation(),
                                                                userInput.substring(3));
                                                        if (!isKingUnderCheck) {
                                                            pieces.get(whoseMove + " knight - " + whatKnight)
                                                                    .setLocation(userInput.substring(3));
                                                            done = true;
                                                        }

                                                    }
                                                }
                                            } catch (Exception e) {

                                            }

                                        }
                                    } catch (Exception e) {

                                    }

                                }
                            }
                        }
                        // number look
                        done = false;
                        try {
                            for (int numberLocation = 1; numberLocation < numberArr.length && !done; numberLocation++) {
                                if (Integer.parseInt(userInput.substring(1, 2)) == numberArr[numberLocation - 1]) {
                                    for (int whatKnight = 1; whatKnight <= numberOfEachPiece.get(whoseMove + "Pawns")
                                            && !done; whatKnight++) {
                                        try {
                                            if (pieces.get(whoseMove + " knight - " + whatKnight).getLocation()
                                                    .substring(1, 2)
                                                    .equals(userInput.substring(1, 2))) {
                                                knightValidMove(whoseMove + " knight - " + whatKnight);

                                                try {
                                                    for (int a = 0; a < pieces
                                                            .get(whoseMove + " knight - " + whatKnight).validMoves
                                                            .size() && !done; a++) {
                                                        if (pieces.get(whoseMove + " knight - " + whatKnight).validMoves
                                                                .get(a)
                                                                .equals(userInput.substring(3))) {
                                                            // move the piece
                                                            isKingUnderCheck = willKingBeUnderCheck(
                                                                    pieces.get(whoseMove + " knight - " + whatKnight)
                                                                            .getLocation(),
                                                                    userInput.substring(3));
                                                            if (!isKingUnderCheck) {
                                                                pieces.get(whoseMove + " knight - " + whatKnight)
                                                                        .setLocation(userInput.substring(3));
                                                                done = true;
                                                            }

                                                            break;

                                                        }
                                                    }
                                                } catch (Exception e) {

                                                }

                                            }
                                        } catch (Exception e) {

                                        }

                                    }
                                }
                            }
                        } catch (Exception e) {

                        }

                        break;

                    case 'B':
                        // find the witch bishop

                        // letter look
                        for (int LetterLocation = 0; LetterLocation < letterArr.length && !done; LetterLocation++) {
                            if (userInput.substring(1, 2).equals(letterArr[LetterLocation])) {
                                for (int whatBishop = 1; whatBishop <= numberOfEachPiece.get(whoseMove + "Bishops")
                                        && !done; whatBishop++) {
                                    try {
                                        if (pieces.get(whoseMove + " bishop - " + whatBishop).getLocation()
                                                .substring(0, 1)
                                                .equals(userInput.substring(1, 2))) {
                                            bishopValidMove(whoseMove + " bishop - " + whatBishop);
                                            try {
                                                for (int a = 0; a < pieces
                                                        .get(whoseMove + " bishop - " + whatBishop).validMoves
                                                        .size()
                                                        && !done; a++) {
                                                    if (pieces.get(whoseMove + " bishop - " + whatBishop).validMoves
                                                            .get(a)
                                                            .equals(userInput.substring(3))) {
                                                        // move the piece
                                                        isKingUnderCheck = willKingBeUnderCheck(
                                                                pieces.get(whoseMove + " bishop - " + whatBishop)
                                                                        .getLocation(),
                                                                userInput.substring(3));
                                                        if (!isKingUnderCheck) {
                                                            pieces.get(whoseMove + " bishop - " + whatBishop)
                                                                    .setLocation(userInput.substring(3));
                                                            done = true;
                                                        }

                                                    }
                                                }
                                            } catch (Exception e) {

                                            }
                                        }
                                    } catch (Exception e) {

                                    }

                                }
                            }
                        }
                        // number look
                        done = false;
                        try {
                            for (int numberLocation = 1; numberLocation < numberArr.length && !done; numberLocation++) {
                                if (Integer.parseInt(userInput.substring(1, 2)) == numberArr[numberLocation - 1]) {
                                    for (int whatBishop = 1; whatBishop <= numberOfEachPiece.get(whoseMove + "Bishops")
                                            && !done; whatBishop++) {
                                        try {
                                            if (pieces.get(whoseMove + " bishop - " + whatBishop).getLocation()
                                                    .substring(1, 2)
                                                    .equals(userInput.substring(1, 2))) {
                                                bishopValidMove(whoseMove + " bishop - " + whatBishop);

                                                try {
                                                    for (int a = 0; a < pieces
                                                            .get(whoseMove + " bishop - " + whatBishop).validMoves
                                                            .size() && !done; a++) {
                                                        if (pieces.get(whoseMove + " bishop - " + whatBishop).validMoves
                                                                .get(a)
                                                                .equals(userInput.substring(3))) {
                                                            // move the piece
                                                            isKingUnderCheck = willKingBeUnderCheck(
                                                                    pieces.get(whoseMove + " bishop - " + whatBishop)
                                                                            .getLocation(),
                                                                    userInput.substring(3));
                                                            if (!isKingUnderCheck) {
                                                                pieces.get(whoseMove + " bishop - " + whatBishop)
                                                                        .setLocation(userInput.substring(3));
                                                                done = true;
                                                            }
                                                            break;

                                                        }
                                                    }
                                                } catch (Exception e) {

                                                }

                                            }
                                        } catch (Exception e) {

                                        }

                                    }
                                }
                            }
                        } catch (Exception e) {

                        }

                        break;
                    case 'R':

                        // find the witch rook

                        // letter look
                        for (int LetterLocation = 0; LetterLocation < letterArr.length && !done; LetterLocation++) {
                            if (userInput.substring(1, 2).equals(letterArr[LetterLocation])) {
                                for (int whatRook = 1; whatRook <= numberOfEachPiece.get(whoseMove + "Rooks")
                                        && !done; whatRook++) {
                                    try {
                                        if (pieces.get(whoseMove + " rook - " + whatRook).getLocation().substring(0, 1)
                                                .equals(userInput.substring(1, 2))) {
                                            rookValidMove(whoseMove + " rook - " + whatRook);
                                            try {
                                                for (int a = 0; a < pieces
                                                        .get(whoseMove + " rook - " + whatRook).validMoves
                                                        .size()
                                                        && !done; a++) {
                                                    if (pieces.get(whoseMove + " rook - " + whatRook).validMoves.get(a)
                                                            .equals(userInput.substring(3))) {
                                                        // move the piece
                                                        isKingUnderCheck = willKingBeUnderCheck(
                                                                pieces.get(whoseMove + " rook - " + whatRook)
                                                                        .getLocation(),
                                                                userInput.substring(3));
                                                        if (!isKingUnderCheck) {
                                                            pieces.get(whoseMove + " rook - " + whatRook)
                                                                    .setLocation(userInput.substring(3));
                                                            done = true;
                                                        }

                                                    }
                                                }
                                            } catch (Exception e) {

                                            }
                                        }
                                    } catch (Exception e) {

                                    }

                                }
                            }
                        }
                        // number look
                        done = false;
                        try {
                            for (int numberLocation = 1; numberLocation < numberArr.length && !done; numberLocation++) {
                                if (Integer.parseInt(userInput.substring(1, 2)) == numberArr[numberLocation]) {
                                    for (int whatRook = 1; whatRook <= numberOfEachPiece.get(whoseMove + "Rooks")
                                            && !done; whatRook++) {
                                        try {
                                            if (pieces.get(whoseMove + " rook - " + whatRook).getLocation()
                                                    .substring(1, 2)
                                                    .equals(userInput.substring(1, 2))) {
                                                rookValidMove(whoseMove + " rook - " + whatRook);

                                                try {
                                                    for (int a = 0; a < pieces
                                                            .get(whoseMove + " rook - " + whatRook).validMoves
                                                            .size() && !done; a++) {
                                                        if (pieces.get(whoseMove + " rook - " + whatRook).validMoves
                                                                .get(a)
                                                                .equals(userInput.substring(3))) {
                                                            // move the piece

                                                            isKingUnderCheck = willKingBeUnderCheck(
                                                                    pieces.get(whoseMove + " rook - " + whatRook)
                                                                            .getLocation(),
                                                                    userInput.substring(3));
                                                            if (!isKingUnderCheck) {
                                                                pieces.get(whoseMove + " rook - " + whatRook)
                                                                        .setLocation(userInput.substring(3));
                                                                done = true;
                                                            }
                                                            break;

                                                        }
                                                    }
                                                } catch (Exception e) {

                                                }

                                            }
                                        } catch (Exception e) {

                                        }

                                    }
                                }
                            }
                        } catch (Exception e) {

                        }

                        break;

                    default:
                        break;
                }
            }

            // if more then two big pieces can move to the same location
            if (userInput.length() == 5 && userInputArr[2] != 'x') {
                switch (userInputArr[0]) {
                    case 'Q':
                        // letter
                        for (int letterOfQueen = 0; letterOfQueen < letterArr.length; letterOfQueen++) {
                            if (userInput.substring(1, 2).equals(letterArr[letterOfQueen])) {
                                for (int numberLocationOfQueen = 1; numberLocationOfQueen < numberArr.length; numberLocationOfQueen++) {
                                    if (Integer.parseInt(
                                            userInput.substring(2, 3)) == (numberArr[numberLocationOfQueen - 1])) {
                                        // now we know the the location of the queen we want to move
                                        for (int whatQueen = 1, foundQueens = 0; foundQueens < numberOfEachPiece
                                                .get(whoseMove + "Queens"); whatQueen++) {
                                            try {
                                                String locationOfCurrentQueen = pieces
                                                        .get(whoseMove + " queen - " + whatQueen)
                                                        .getLocation();
                                                foundQueens++;
                                                String tempLocation = letterArr[letterOfQueen] + numberLocationOfQueen;
                                                if (locationOfCurrentQueen.equals(tempLocation)) {
                                                    queenValidMove(whoseMove + " queen - " + whatQueen);
                                                    // now we need to capture and move the queen
                                                    for (int currentValidMove = 0; currentValidMove < pieces
                                                            .get(whoseMove + " queen - " + whatQueen).validMoves
                                                            .size(); currentValidMove++) {
                                                        // find witch valid move we are doing
                                                        if (userInput.substring(3)
                                                                .equals(pieces
                                                                        .get(whoseMove + " queen - "
                                                                                + whatQueen).validMoves
                                                                        .get(currentValidMove))) {
                                                            isKingUnderCheck = willKingBeUnderCheck(
                                                                    pieces.get(whoseMove + " queen - " + whatQueen)
                                                                            .getLocation(),
                                                                    userInput.substring(3));
                                                            if (!isKingUnderCheck) {
                                                                pieces.get(whoseMove + " queen - " + whatQueen)
                                                                        .setLocation(userInput.substring(3));
                                                            }

                                                            break;
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {

                                            }

                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 'R':
                        // letter
                        for (int letterLocationOfRook = 0; letterLocationOfRook < letterArr.length; letterLocationOfRook++) {
                            if (userInput.substring(2, 3) == letterArr[letterLocationOfRook]) {
                                for (int numberLocationOfRook = 1; numberLocationOfRook < numberArr.length; numberLocationOfRook++) {
                                    if (Integer
                                            .parseInt(
                                                    userInput.substring(2, 3)) == numberArr[numberLocationOfRook - 1]) {
                                        // now we know the the location of the queen we want to move
                                        for (int whatRook = 0, foundRooks = 0; foundRooks < numberOfEachPiece
                                                .get(whoseMove + "Rooks"); whatRook++) {
                                            try {
                                                String locationOfCurrentQueen = pieces
                                                        .get(whoseMove + " rook - " + whatRook)
                                                        .getLocation();
                                                foundRooks++;
                                                String tempLocation = letterArr[letterLocationOfRook]
                                                        + numberLocationOfRook;
                                                if (locationOfCurrentQueen.equals(tempLocation)) {
                                                    rookValidMove(whoseMove + " rook - " + whatRook);
                                                    // now we need to capture and move the queen
                                                    for (int currentValidMove = 0; currentValidMove < pieces
                                                            .get(whoseMove + " rook - " + whatRook).validMoves
                                                            .size(); currentValidMove++) {
                                                        // find witch valid move we are doing
                                                        if (userInput.substring(3)
                                                                .equals(pieces
                                                                        .get(whoseMove + " rook - "
                                                                                + whatRook).validMoves
                                                                        .get(currentValidMove))) {
                                                            isKingUnderCheck = willKingBeUnderCheck(
                                                                    pieces.get(whoseMove + " rook - " + whatRook)
                                                                            .getLocation(),
                                                                    userInput.substring(3));
                                                            if (!isKingUnderCheck) {
                                                                pieces.get(whoseMove + " rook - " + whatRook)
                                                                        .setLocation(userInput.substring(3));
                                                            }

                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {

                                            }

                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 'B':
                        // letter
                        for (int letterLocationOfBishop = 0; letterLocationOfBishop < letterArr.length; letterLocationOfBishop++) {
                            if (userInput.substring(2, 3) == letterArr[letterLocationOfBishop]) {
                                for (int numberLocationOfBishop = 1; numberLocationOfBishop < numberArr.length; numberLocationOfBishop++) {
                                    if (Integer
                                            .parseInt(userInput.substring(2,
                                                    3)) == numberArr[numberLocationOfBishop - 1]) {
                                        // now we know the the location of the queen we want to move
                                        for (int whatBishop = 0, foundBishops = 0; foundBishops < numberOfEachPiece
                                                .get(whoseMove + "Bishops"); whatBishop++) {
                                            try {
                                                String locationOfCurrentQueen = pieces
                                                        .get(whoseMove + " bishop - " + whatBishop)
                                                        .getLocation();
                                                foundBishops++;
                                                String tempLocation = letterArr[letterLocationOfBishop]
                                                        + numberLocationOfBishop;
                                                if (locationOfCurrentQueen.equals(tempLocation)) {
                                                    bishopValidMove(whoseMove + " bishop - " + whatBishop);
                                                    // now we need to capture and move the queen
                                                    for (int currentValidMove = 0; currentValidMove < pieces
                                                            .get(whoseMove + " bishop - " + whatBishop).validMoves
                                                            .size(); currentValidMove++) {
                                                        // find witch valid move we are doing
                                                        if (userInput.substring(3)
                                                                .equals(pieces
                                                                        .get(whoseMove + " bishop - "
                                                                                + whatBishop).validMoves
                                                                        .get(currentValidMove))) {
                                                            isKingUnderCheck = willKingBeUnderCheck(
                                                                    pieces.get(whoseMove + " bishop - " + whatBishop)
                                                                            .getLocation(),
                                                                    userInput.substring(3));
                                                            if (!isKingUnderCheck) {
                                                                pieces.get(whoseMove + " bishop - " + whatBishop)
                                                                        .setLocation(userInput.substring(3));
                                                            }

                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {

                                            }

                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 'N':
                        // letter
                        for (int letterLocationOfKnight = 0; letterLocationOfKnight < letterArr.length; letterLocationOfKnight++) {
                            if (userInput.substring(2, 3) == letterArr[letterLocationOfKnight]) {
                                for (int numberLocationOfKnight = 1; numberLocationOfKnight < numberArr.length; numberLocationOfKnight++) {
                                    if (Integer
                                            .parseInt(userInput.substring(2,
                                                    3)) == numberArr[numberLocationOfKnight - 1]) {
                                        // now we know the the location of the queen we want to move
                                        for (int whatKnight = 0, foundKnights = 0; foundKnights < numberOfEachPiece
                                                .get(whoseMove + "Knights"); whatKnight++) {
                                            try {
                                                String locationOfCurrentQueen = pieces
                                                        .get(whoseMove + " knight - " + whatKnight)
                                                        .getLocation();
                                                foundKnights++;
                                                String tempLocation = letterArr[letterLocationOfKnight]
                                                        + numberLocationOfKnight;
                                                if (locationOfCurrentQueen.equals(tempLocation)) {
                                                    knightValidMove(whoseMove + " knight - " + whatKnight);
                                                    // now we need to capture and move the queen
                                                    for (int currentValidMove = 0; currentValidMove < pieces
                                                            .get(whoseMove + " knight - " + whatKnight).validMoves
                                                            .size(); currentValidMove++) {
                                                        // find witch valid move we are doing
                                                        if (userInput.substring(3)
                                                                .equals(pieces
                                                                        .get(whoseMove + " knight - "
                                                                                + whatKnight).validMoves
                                                                        .get(currentValidMove))) {

                                                            isKingUnderCheck = willKingBeUnderCheck(
                                                                    pieces.get(whoseMove + " knight - " + whatKnight)
                                                                            .getLocation(),
                                                                    userInput.substring(3));
                                                            if (!isKingUnderCheck) {
                                                                pieces.get(whoseMove + " knight - " + whatKnight)
                                                                        .setLocation(userInput.substring(3));
                                                            }

                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {

                                            }

                                        }
                                    }
                                }
                            }
                        }
                        break;

                    default:
                        break;
                }
            }

            // if more then two big pieces can capture the same location
            if (userInput.length() == 6 && userInputArr[3] == 'x') {
                switch (userInputArr[0]) {
                    case 'Q':
                        // letter
                        for (int letterOfQueen = 0; letterOfQueen < letterArr.length; letterOfQueen++) {
                            if (userInput.substring(1, 2).equals(letterArr[letterOfQueen])) {
                                for (int numberLocationOfQueen = 1; numberLocationOfQueen < numberArr.length; numberLocationOfQueen++) {
                                    if (Integer
                                            .parseInt(userInput.substring(2, 3)) == numberArr[numberLocationOfQueen]) {
                                        // now we know the the location of the queen we want to move
                                        for (int whatQueen = 0, foundQueens = 0; foundQueens < numberOfEachPiece
                                                .get(whoseMove + "Queens"); whatQueen++) {
                                            try {
                                                String locationOfCurrentQueen = pieces
                                                        .get(whoseMove + " queen - " + whatQueen)
                                                        .getLocation();
                                                foundQueens++;
                                                String tempLocation = letterArr[letterOfQueen] + numberLocationOfQueen;
                                                if (locationOfCurrentQueen.equals(tempLocation)) {
                                                    queenValidMove(whoseMove + " queen - " + whatQueen);
                                                    // now we need to capture and move the queen
                                                    for (int currentValidMove = 0; currentValidMove < pieces
                                                            .get(whoseMove + " queen - " + whatQueen).validMoves
                                                            .size(); currentValidMove++) {
                                                        // find witch valid move we are doing
                                                        if (userInput.substring(4)
                                                                .equals(pieces
                                                                        .get(whoseMove + " queen - "
                                                                                + whatQueen).validMoves
                                                                        .get(currentValidMove))) {
                                                            isKingUnderCheck = willKingBeUnderCheck(
                                                                    pieces.get(whoseMove + " queen - " + whatQueen)
                                                                            .getLocation(),
                                                                    userInput.substring(4));
                                                            if (!isKingUnderCheck) {
                                                                removePiece(userInput.substring(4));
                                                                pieces.get(whoseMove + " queen - " + whatQueen)
                                                                        .setLocation(userInput.substring(4));
                                                            }

                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {

                                            }

                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 'R':
                        // letter
                        for (int letterLocationOfRook = 0; letterLocationOfRook < letterArr.length; letterLocationOfRook++) {
                            if (userInput.substring(1, 2) == letterArr[letterLocationOfRook]) {
                                for (int numberLocationOfRook = 1; numberLocationOfRook < numberArr.length; numberLocationOfRook++) {
                                    if (Integer
                                            .parseInt(
                                                    userInput.substring(2, 3)) == numberArr[numberLocationOfRook - 1]) {
                                        // now we know the the location of the queen we want to move
                                        for (int whatRook = 0, foundRooks = 0; foundRooks < numberOfEachPiece
                                                .get(whoseMove + "Rooks"); whatRook++) {
                                            try {
                                                String locationOfCurrentQueen = pieces
                                                        .get(whoseMove + " rook - " + whatRook)
                                                        .getLocation();
                                                foundRooks++;
                                                String tempLocation = letterArr[letterLocationOfRook]
                                                        + numberLocationOfRook;
                                                if (locationOfCurrentQueen.equals(tempLocation)) {
                                                    rookValidMove(whoseMove + " rook - " + whatRook);
                                                    // now we need to capture and move the queen
                                                    for (int currentValidMove = 0; currentValidMove < pieces
                                                            .get(whoseMove + " rook - " + whatRook).validMoves
                                                            .size(); currentValidMove++) {
                                                        // find witch valid move we are doing
                                                        if (userInput.substring(4)
                                                                .equals(pieces
                                                                        .get(whoseMove + " rook - "
                                                                                + whatRook).validMoves
                                                                        .get(currentValidMove))) {
                                                            isKingUnderCheck = willKingBeUnderCheck(
                                                                    pieces.get(whoseMove + " rook - " + whatRook)
                                                                            .getLocation(),
                                                                    userInput.substring(4));
                                                            if (!isKingUnderCheck) {
                                                                removePiece(userInput.substring(4));
                                                                pieces.get(whoseMove + " rook - " + whatRook)
                                                                        .setLocation(userInput.substring(4));
                                                            }

                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {

                                            }

                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 'B':
                        // letter
                        for (int letterLocationOfBishop = 0; letterLocationOfBishop < letterArr.length; letterLocationOfBishop++) {
                            if (userInput.substring(1, 2) == letterArr[letterLocationOfBishop]) {
                                for (int numberLocationOfBishop = 0; numberLocationOfBishop < numberArr.length; numberLocationOfBishop++) {
                                    if (Integer
                                            .parseInt(userInput.substring(2,
                                                    3)) == numberArr[numberLocationOfBishop - 1]) {
                                        // now we know the the location of the queen we want to move
                                        for (int whatBishop = 0, foundBishops = 0; foundBishops < numberOfEachPiece
                                                .get(whoseMove + "Bishops"); whatBishop++) {
                                            try {
                                                String locationOfCurrentQueen = pieces
                                                        .get(whoseMove + " bishop - " + whatBishop)
                                                        .getLocation();
                                                foundBishops++;
                                                String tempLocation = letterArr[letterLocationOfBishop]
                                                        + numberLocationOfBishop;
                                                if (locationOfCurrentQueen.equals(tempLocation)) {
                                                    bishopValidMove(whoseMove + " bishop - " + whatBishop);
                                                    // now we need to capture and move the queen
                                                    for (int currentValidMove = 0; currentValidMove < pieces
                                                            .get(whoseMove + " bishop - " + whatBishop).validMoves
                                                            .size(); currentValidMove++) {
                                                        // find witch valid move we are doing
                                                        if (userInput.substring(4)
                                                                .equals(pieces
                                                                        .get(whoseMove + " bishop - "
                                                                                + whatBishop).validMoves
                                                                        .get(currentValidMove))) {
                                                            isKingUnderCheck = willKingBeUnderCheck(
                                                                    pieces.get(whoseMove + " bishop - " + whatBishop)
                                                                            .getLocation(),
                                                                    userInput.substring(4));
                                                            if (!isKingUnderCheck) {
                                                                removePiece(userInput.substring(4));
                                                                pieces.get(whoseMove + " bishop - " + whatBishop)
                                                                        .setLocation(userInput.substring(4));
                                                            }

                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {

                                            }

                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 'N':
                        // letter
                        for (int letterLocationOfKnight = 0; letterLocationOfKnight < letterArr.length; letterLocationOfKnight++) {
                            if (userInput.substring(1, 2) == letterArr[letterLocationOfKnight]) {
                                for (int numberLocationOfKnight = 1; numberLocationOfKnight < numberArr.length; numberLocationOfKnight++) {
                                    if (Integer
                                            .parseInt(userInput.substring(2,
                                                    3)) == numberArr[numberLocationOfKnight - 1]) {
                                        // now we know the the location of the queen we want to move
                                        for (int whatRook = 0, foundRooks = 0; foundRooks < numberOfEachPiece
                                                .get(whoseMove + "Knights"); whatRook++) {
                                            try {
                                                String locationOfCurrentQueen = pieces
                                                        .get(whoseMove + " knight - " + whatRook)
                                                        .getLocation();
                                                foundRooks++;
                                                String tempLocation = letterArr[letterLocationOfKnight]
                                                        + numberLocationOfKnight;
                                                if (locationOfCurrentQueen.equals(tempLocation)) {
                                                    knightValidMove(whoseMove + " knight - " + whatRook);
                                                    // now we need to capture and move the queen
                                                    for (int currentValidMove = 0; currentValidMove < pieces
                                                            .get(whoseMove + " knight - " + whatRook).validMoves
                                                            .size(); currentValidMove++) {
                                                        // find witch valid move we are doing
                                                        if (userInput.substring(4)
                                                                .equals(pieces
                                                                        .get(whoseMove + " knight - "
                                                                                + whatRook).validMoves
                                                                        .get(currentValidMove))) {
                                                            isKingUnderCheck = willKingBeUnderCheck(
                                                                    pieces.get(whoseMove + " knight - " + whatRook)
                                                                            .getLocation(),
                                                                    userInput.substring(4));
                                                            if (!isKingUnderCheck) {
                                                                removePiece(userInput.substring(4));
                                                                pieces.get(whoseMove + " knight - " + whatRook)
                                                                        .setLocation(tempLocation);
                                                            }

                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {

                                            }

                                        }
                                    }
                                }
                            }
                        }
                        break;

                    default:
                        break;
                }
            }
            displayBoard();
            if (!isKingUnderCheck) {
                if (whoseMove.equals("white")) {
                    whoseMove = "black";
                    pgn += moveCounter + ". " + userInput;

                } else {
                    whoseMove = "white";
                    pgn += " " + userInput + " ";
                    moveCounter++;
                    System.out.println(pgn);
                }
            }

        }
    }

    private boolean willKingBeUnderCheck(String locationOfPiece, String LocationOfPieceAfterMove) {

        ArrayList<String> validMoves = new ArrayList<String>();

        String kingName = whoseMove + " king";
        String kingLocation = pieces.get(kingName).getLocation();

        /*
         * save how the piece would move if we didnt care about checks
         * we will create two variables
         * one will have the location if the piece did move and
         * the other would have the position of where it came from
         * change so it work with evey possible move
         * if it is a king move thing will work a bit differently
         * we need to check if a piece can see that location
         * change king location to be a subString of userInput Ke6 ---> e6
         */

        /*
         * 
         * see if our king can see an enemy piece
         * look thought all directions
         * if we are at the location of our two vars then we skip
         * 
         * 
         * if we see a rook but we are currently looking thought a diagnal then we can
         * say the king would not be under check
         * but if it sees a bishop on the diagonal then the king would be under check
         * if not pieces are found to be putting the king under check then return false;
         * add house moves to the check
         */
        {

            char[] locationArr = kingLocation.toCharArray();
            char[] lettersArr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
            int numberVersionOfLetter = 100;
            for (int i = 0; i < lettersArr.length; i++) {
                if (locationArr[0] == lettersArr[i]) {
                    numberVersionOfLetter = i;
                    break;
                }
            }
            int numberlocation = Integer.parseInt(Character.toString(locationArr[1]));

            // up and right
            for (int j = 1, i = numberVersionOfLetter; j <= 8; i++, j++) {
                int tempNumberLocation = numberlocation + j;

                String tempLetterLocation = increaseLetter(lettersArr[i]);

                // ensure location is on the board
                if (tempNumberLocation > 8 || tempLetterLocation.equals("")) {
                    break;
                }

                String tempLocation = tempLetterLocation + tempNumberLocation;
                if (tempLocation.equals(locationOfPiece)
                        || tempLocation.equals(LocationOfPieceAfterMove)) {
                    continue;
                }
                String whoAtTempLocation = whoIsAtLocationBackend(tempLocation);
                if (!whoAtTempLocation.equals("")) {

                    if (whoseMove.equals("white") && whoAtTempLocation.substring(0, 1).equals("2")) {
                        // we are white and we see a black piece
                        // check bishop or queen'

                        if (/* bishop */whoAtTempLocation.substring(7, 13).equals("bishop")
                                || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                            return true;
                        }

                    } else if (whoseMove.equals("black") && whoAtTempLocation.substring(0, 1).equals("1")) {
                        // we are black and we see a white piece
                        // check bishop or queen
                        if (/* bishop */whoAtTempLocation.substring(7, 13).equals("bishop")
                                || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                            return true;
                        }
                    } else {
                        // if someone is their and they are on our side
                        break;
                    }
                } else if (tempLocation.equals(locationOfPiece)) {
                    continue;
                } else if (tempLocation.equals(LocationOfPieceAfterMove)) {
                    break;
                }
            }
            // down and right
            for (int j = 1, i = numberVersionOfLetter; j <= 8; i++, j++) {
                int tempNumberLocation = numberlocation - j;

                String tempLetterLocation = increaseLetter(lettersArr[i]);

                // ensure location is on the board
                if (tempNumberLocation < 1 || tempLetterLocation.equals("")) {
                    break;
                }

                String tempLocation = tempLetterLocation + tempNumberLocation;

                String whoAtTempLocation = whoIsAtLocationBackend(tempLocation);
                if (!whoAtTempLocation.equals("")) {

                    if (whoseMove.equals("white") && whoAtTempLocation.substring(0, 1).equals("2")) {
                        // we are white and we see a black piece
                        // check bishop or queen'

                        if (/* bishop */whoAtTempLocation.substring(7, 13).equals("bishop")
                                || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                            return true;
                        }

                    } else if (whoseMove.equals("black") && whoAtTempLocation.substring(0, 1).equals("1")) {
                        // we are black and we see a white piece
                        // check bishop or queen
                        if (/* bishop */whoAtTempLocation.substring(7, 13).equals("bishop")
                                || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                            return true;
                        }
                    } else if (tempLocation.equals(locationOfPiece)) {
                        continue;
                    } else if (tempLocation.equals(LocationOfPieceAfterMove)) {
                        break;
                    } else {
                        // if someone is their and they are on our side
                        break;
                    }

                }
            }

            // down and left
            for (int j = 1, i = numberVersionOfLetter; j <= 8; i--, j++) {
                int tempNumberLocation = numberlocation - j;

                String tempLetterLocation = decreaseLetter(lettersArr[i]);

                // ensure location is on the board
                if (tempNumberLocation < 1 || tempLetterLocation.equals("")) {
                    break;
                }

                String tempLocation = tempLetterLocation + tempNumberLocation;

                String whoAtTempLocation = whoIsAtLocationBackend(tempLocation);
                if (!whoAtTempLocation.equals("")) {
                    if (whoseMove.equals("white") && whoAtTempLocation.substring(0, 1).equals("2")) {
                        // we are white and we see a black piece
                        // check bishop or queen'

                        if (/* bishop */whoAtTempLocation.substring(7, 13).equals("bishop")
                                || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                            return true;
                        }

                    } else if (whoseMove.equals("black") && whoAtTempLocation.substring(0, 1).equals("1")) {
                        // we are black and we see a white piece
                        // check bishop or queen
                        if (/* bishop */whoAtTempLocation.substring(7, 13).equals("bishop")
                                || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                            return true;
                        }
                    } else {
                        // if someone is their and they are on our side
                        break;
                    }
                } else if (tempLocation.equals(locationOfPiece)) {
                    continue;
                } else if (tempLocation.equals(LocationOfPieceAfterMove)) {
                    break;
                }
            }

            // up and left
            for (int j = 1, i = numberVersionOfLetter; j <= 8; i--, j++) {
                int tempNumberLocation = numberlocation + j;

                String tempLetterLocation = decreaseLetter(lettersArr[i]);

                // ensure location is on the board
                if (tempNumberLocation > 8 || tempLetterLocation.equals("")) {
                    break;
                }

                String tempLocation = tempLetterLocation + tempNumberLocation;
                String whoAtTempLocation = whoIsAtLocationBackend(tempLocation);
                if (!whoAtTempLocation.equals("")) {

                    if (whoseMove.equals("white") && whoAtTempLocation.substring(0, 1).equals("2")) {
                        // we are white and we see a black piece
                        // check bishop or queen'

                        if (/* bishop */whoAtTempLocation.substring(7, 13).equals("bishop")
                                || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                            return true;
                        }

                    } else if (whoseMove.equals("black") && whoAtTempLocation.substring(0, 1).equals("1")) {
                        // we are black and we see a white piece
                        // check bishop or queen
                        if (/* bishop */whoAtTempLocation.substring(7, 13).equals("bishop")
                                || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                            return true;
                        }
                    } else {
                        // if someone is their and they are on our side
                        break;
                    }
                } else if (tempLocation.equals(locationOfPiece)) {
                    continue;
                } else if (tempLocation.equals(LocationOfPieceAfterMove)) {
                    break;
                }
            }

            String letterLocation = Character.toString(locationArr[0]);

            // up

            for (int i = numberlocation; i <= 8; i++) {

                // make sure location is empty
                String tempLocation = letterLocation + i;
                if (tempLocation.equals(locationOfPiece)
                        || tempLocation.equals(LocationOfPieceAfterMove)) {
                    continue;
                }
                String whoAtTempLocation = whoIsAtLocationBackend(tempLocation);
                if (!whoAtTempLocation.equals("")) {

                    if (whoseMove.equals("white") && whoAtTempLocation.substring(0, 1).equals("2")) {
                        // we are white and we see a black piece
                        // check rook or queen'

                        if (/* rook */whoAtTempLocation.substring(7, 10).equals("rook")
                                || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                            return true;
                        }

                    } else if (whoseMove.equals("black") && whoAtTempLocation.substring(0, 1).equals("1")) {
                        // we are black and we see a white piece
                        // check rook or queen
                        if (/* rook */whoAtTempLocation.substring(7, 10).equals("rook")
                                || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                            return true;
                        }
                    } else {
                        // if someone is their and they are on our side
                        break;
                    }
                } else if (tempLocation.equals(locationOfPiece)) {
                    continue;
                } else if (tempLocation.equals(LocationOfPieceAfterMove)) {
                    break;
                }

            }

            // down
            for (int i = numberlocation; i >= 1; i--) {

                // make sure location in empty
                String tempLocation = letterLocation + i;
                if (tempLocation.equals(locationOfPiece)
                        || tempLocation.equals(LocationOfPieceAfterMove)) {
                    continue;
                }
                String whoAtTempLocation = whoIsAtLocationBackend(tempLocation);
                if (!whoAtTempLocation.equals("")) {

                    if (whoseMove.equals("white") && whoAtTempLocation.substring(0, 1).equals("2")) {
                        // we are white and we see a black piece
                        // check rook or queen'

                        if (/* rook */whoAtTempLocation.substring(7, 10).equals("rook")
                                || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                            return true;
                        }

                    } else if (whoseMove.equals("black") && whoAtTempLocation.substring(0, 1).equals("1")) {
                        // we are black and we see a white piece
                        // check rook or queen
                        if (/* rook */whoAtTempLocation.substring(7, 10).equals("rook")
                                || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                            return true;
                        }
                    } else {
                        // if someone is their and they are on our side
                        break;
                    }
                } else if (tempLocation.equals(locationOfPiece)) {
                    continue;
                } else if (tempLocation.equals(LocationOfPieceAfterMove)) {
                    break;
                }
            }

            // right
            for (int letterLocationInt = 0; letterLocationInt < 8; letterLocationInt++) {
                if (locationArr[0] == lettersArr[letterLocationInt]) {
                    for (int i = letterLocationInt; i <= 7; i++) {

                        String tempLocation = increaseLetter(lettersArr[i]) + numberlocation;
                        if (tempLocation.equals(locationOfPiece)
                                || tempLocation.equals(LocationOfPieceAfterMove)) {
                            continue;
                        }
                        String whoAtTempLocation = whoIsAtLocationBackend(tempLocation);
                        if (!whoAtTempLocation.equals("")) {

                            if (whoseMove.equals("white") && whoAtTempLocation.substring(0, 1).equals("2")) {
                                // we are white and we see a black piece
                                // check rook or queen'

                                if (/* rook */whoAtTempLocation.substring(7, 10).equals("rook")
                                        || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                                    return true;
                                }

                            } else if (whoseMove.equals("black") && whoAtTempLocation.substring(0, 1).equals("1")) {
                                // we are black and we see a white piece
                                // check rook or queen
                                if (/* rook */whoAtTempLocation.substring(7, 10).equals("rook")
                                        || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                                    return true;
                                }
                            } else if (tempLocation.equals(locationOfPiece)) {
                                continue;
                            } else {
                                // if someone is their and they are on our side
                                break;
                            }
                        }
                    }
                }
            }

            // left
            for (int letterLocationInt = 0; letterLocationInt < 8; letterLocationInt++) {
                if (locationArr[0] == lettersArr[letterLocationInt]) {
                    for (int i = letterLocationInt; i >= 1; i--) {

                        String tempLocation = decreaseLetter(lettersArr[i]) + numberlocation;
                        if (tempLocation.equals(locationOfPiece)
                                || tempLocation.equals(LocationOfPieceAfterMove)) {
                            continue;
                        }
                        String whoAtTempLocation = whoIsAtLocationBackend(tempLocation);
                        if (!whoAtTempLocation.equals("")) {

                            if (whoseMove.equals("white") && whoAtTempLocation.substring(0, 1).equals("2")) {
                                // we are white and we see a black piece
                                // check rook or queen'

                                if (/* rook */whoAtTempLocation.substring(7, 10).equals("rook")
                                        || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                                    return true;
                                }

                            } else if (whoseMove.equals("black") && whoAtTempLocation.substring(0, 1).equals("1")) {
                                // we are black and we see a white piece
                                // check rook or queen
                                if (/* rook */whoAtTempLocation.substring(7, 10).equals("rook")
                                        || /* queen */whoAtTempLocation.substring(7, 11).equals("queen")) {
                                    return true;
                                }
                            } else if (tempLocation.equals(locationOfPiece)) {
                                continue;
                            } else {
                                // if someone is their and they are on our side
                                break;
                            }
                        }

                    }
                }
            }
            // Knights
            {
                int numberLocation = Integer.parseInt(Character.toString(locationArr[1]));

                String[] tempLetterLocationArr = new String[8];
                int[] tempNumberLocationArr = new int[8];
                String[] letterArr = { "a", "b", "c", "d", "e", "f", "g", "h" };
                char[] decreasedLetter = decreaseLetter(locationArr[0]).toCharArray();
                char[] increasedLetter = increaseLetter(locationArr[0]).toCharArray();
                // number +2 letter +1
                try {
                    tempNumberLocationArr[0] = numberLocation + 2;
                    tempLetterLocationArr[0] = increaseLetter(locationArr[0]);
                } catch (Exception e) {

                }

                try {
                    // number +2 letter -1
                    tempNumberLocationArr[1] = numberLocation + 2;
                    tempLetterLocationArr[1] = decreaseLetter(locationArr[0]);
                } catch (Exception e) {

                }

                try {
                    // number -2 letter +1
                    tempNumberLocationArr[2] = numberLocation - 2;
                    tempLetterLocationArr[2] = increaseLetter(locationArr[0]);
                } catch (Exception e) {

                }
                try {
                    // number -2 letter -1
                    tempNumberLocationArr[3] = numberLocation - 2;
                    tempLetterLocationArr[3] = decreaseLetter(locationArr[0]);
                } catch (Exception e) {

                }

                try {
                    // number +1 letter +2
                    tempNumberLocationArr[4] = numberLocation + 1;
                    tempLetterLocationArr[4] = increaseLetter(increasedLetter[0]);
                } catch (Exception e) {

                }
                try {
                    // number -1 letter +2
                    tempNumberLocationArr[5] = numberLocation - 1;
                    tempLetterLocationArr[5] = increaseLetter(increasedLetter[0]);
                } catch (Exception e) {

                }

                try {
                    // number +1 letter -2
                    tempNumberLocationArr[6] = numberLocation + 1;
                    tempLetterLocationArr[6] = decreaseLetter(decreasedLetter[0]);
                } catch (Exception e) {

                }

                try {
                    // number -1 letter -2
                    tempNumberLocationArr[7] = numberLocation - 1;
                    tempLetterLocationArr[7] = decreaseLetter(decreasedLetter[0]);
                } catch (Exception e) {

                }

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

                    String whoAtTempLocation = whoIsAtLocationBackend(tempLocation);
                    if (letterIsOnBoard && !whoAtTempLocation.equals("")) {
                        if (whoseMove.equals("white") && whoAtTempLocation.substring(0, 1).equals("2")) {
                            // we are white and we see a black piece
                            // check knight

                            if (whoAtTempLocation.substring(7, 12).equals("knight")) {
                                return true;
                            }

                        } else if (whoseMove.equals("black") && whoAtTempLocation.substring(0, 1).equals("1")) {
                            // we are black and we see a white piece
                            // check knight or queen
                            if (whoAtTempLocation.substring(7, 12).equals("knight")) {
                                return true;
                            }
                        } else {
                            // if someone is their and they are on our side
                            break;
                        }

                    }
                }
            }
        }
        return false;
    }

    private void pawnPromote(String name, boolean move) {

        String whatPieceToPromoteTo;
        if (move) {
            whatPieceToPromoteTo = userInput.substring(3);
            switch (whatPieceToPromoteTo) {
                case "Q":
                    increasePieceCount(whoseMove + "Queens");
                    pieces.put(whoseMove + " queen - " + numberOfEachPiece.get(whoseMove + "Queens"),
                            new queen(userInput.substring(0, 2), whoseMove + " queen"));
                    decreasePieceCount(whoseMove + "Pawns");
                    break;
                case "R":
                    increasePieceCount(whoseMove + "Rooks");
                    pieces.put(whoseMove + " rook - " + numberOfEachPiece.get(whoseMove + "Rooks"),
                            new rook(userInput.substring(0, 2), whoseMove + " rook"));
                    decreasePieceCount(whoseMove + "Pawns");
                    break;
                case "N":
                    increasePieceCount(whoseMove + "Knights");
                    decreasePieceCount(whoseMove + "Pawns");
                    pieces.put(whoseMove + " knight - " + numberOfEachPiece.get("whatKnights"),
                            new knight(userInput.substring(0, 2), whoseMove + " knight"));
                    break;
                case "B":
                    increasePieceCount(whoseMove + "Bishops");
                    pieces.put(whoseMove + " bishop - " + numberOfEachPiece.get("whatBishops"),
                            new bishop(userInput.substring(0, 2), whoseMove + " bishop"));
                    decreasePieceCount(whoseMove + "Pawns");
                    break;
                default:
                    break;
            }
        } else {
            whatPieceToPromoteTo = userInput.substring(5);

            switch (whatPieceToPromoteTo) {
                // switch user input number
                case "Q":
                    increasePieceCount(whoseMove + "Queens");
                    pieces.put(whoseMove + " queen - " + numberOfEachPiece.get(whoseMove + "Queens"),
                            new queen(userInput.substring(2, 4), whoseMove + " queen"));
                    decreasePieceCount(whoseMove + "Pawns");
                    break;
                case "R":
                    increasePieceCount(whoseMove + "Rooks");
                    pieces.put(whoseMove + " rook - " + numberOfEachPiece.get(whoseMove + "Rooks"),
                            new rook(userInput.substring(2, 4), whoseMove + " rook"));
                    decreasePieceCount(whoseMove + "Pawns");
                    break;
                case "N":
                    increasePieceCount(whoseMove + "Knights");
                    pieces.put(whoseMove + " knight - " + numberOfEachPiece.get(whoseMove + "Knights"),
                            new knight(userInput.substring(2, 4), whoseMove + " knight"));
                    decreasePieceCount(whoseMove + "Pawns");
                    break;
                case "B":
                    increasePieceCount(whoseMove + "Bishops");
                    pieces.put(whoseMove + " bishop - " + numberOfEachPiece.get(whoseMove + "Bishops"),
                            new bishop(userInput.substring(2, 4), whoseMove + " bishop"));
                    decreasePieceCount(whoseMove + "Pawns");
                    break;
                default:
                    break;
            }
        }

        pieces.remove(name);
    }

    private void increasePieceCount(String whatPiece) {
        int tempInt = numberOfEachPiece.get(whatPiece);
        pieces.remove(whatPiece);
        numberOfEachPiece.put(whatPiece, tempInt + 1);
    }

    private void decreasePieceCount(String whatPiece) {
        int tempInt = numberOfEachPiece.get(whatPiece);
        pieces.remove(whatPiece);
        numberOfEachPiece.put(whatPiece, tempInt - 1);
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
        // number +2 letter +1
        try {
            tempNumberLocationArr[0] = numberLocation + 2;
            tempLetterLocationArr[0] = increaseLetter(locationArr[0]);
        } catch (Exception e) {

        }

        try {
            // number +2 letter -1
            tempNumberLocationArr[1] = numberLocation + 2;
            tempLetterLocationArr[1] = decreaseLetter(locationArr[0]);
        } catch (Exception e) {

        }

        try {
            // number -2 letter +1
            tempNumberLocationArr[2] = numberLocation - 2;
            tempLetterLocationArr[2] = increaseLetter(locationArr[0]);
        } catch (Exception e) {

        }
        try {
            // number -2 letter -1
            tempNumberLocationArr[3] = numberLocation - 2;
            tempLetterLocationArr[3] = decreaseLetter(locationArr[0]);
        } catch (Exception e) {

        }

        try {
            // number +1 letter +2
            tempNumberLocationArr[4] = numberLocation + 1;
            tempLetterLocationArr[4] = increaseLetter(increasedLetter[0]);
        } catch (Exception e) {

        }
        try {
            // number -1 letter +2
            tempNumberLocationArr[5] = numberLocation - 1;
            tempLetterLocationArr[5] = increaseLetter(increasedLetter[0]);
        } catch (Exception e) {

        }

        try {
            // number +1 letter -2
            tempNumberLocationArr[6] = numberLocation + 1;
            tempLetterLocationArr[6] = decreaseLetter(decreasedLetter[0]);
        } catch (Exception e) {

        }

        try {
            // number -1 letter -2
            tempNumberLocationArr[7] = numberLocation - 1;
            tempLetterLocationArr[7] = decreaseLetter(decreasedLetter[0]);
        } catch (Exception e) {

        }

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
            } // if someone is there and they can be capture
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

            // ensure location is on the board
            if (tempNumberLocation > 8 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they can be captured
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

            // ensure location is on the board
            if (tempNumberLocation < 1 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they can be captured
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

            // ensure location is on the board
            if (tempNumberLocation < 1 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they can be captured
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

            // ensure location is on the board
            if (tempNumberLocation > 8 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they can be captured
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

            // make sure location in empty
            String tempLocation = letterLocation + i;
            if (tempLocation.equals(pieces.get(name).location)) {
                continue;
            }

            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they can be captured
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

            // make sure location in empty
            String tempLocation = letterLocation + i;
            if (tempLocation.equals(pieces.get(name).location)) {
                continue;
            }
            // ensure that the location is on the bou
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they can be captured
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
                    } // if someone is there and they can be captured
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
                    } // if someone is there and they can be captured
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

            // ensure location is on the board
            if (tempNumberLocation > 8 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they can be captured
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

            // ensure location is on the board
            if (tempNumberLocation < 1 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they can be captured
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

            // ensure location is on the board
            if (tempNumberLocation < 1 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they can be captured
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

            // ensure location is on the board
            if (tempNumberLocation > 8 || tempLetterLocation.equals("")) {
                break;
            }

            String tempLocation = tempLetterLocation + tempNumberLocation;

            // make sure the location is empty
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                // add the temp location as a valid move
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they can be captured
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

            // make sure location in empty
            String tempLocation = letterLocation + i;
            if (tempLocation.equals(pieces.get(name).location)) {
                continue;
            }

            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they can be captured
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

            // make sure location in empty
            String tempLocation = letterLocation + i;
            if (tempLocation.equals(pieces.get(name).location)) {
                continue;
            }
            // ensure that the location is on the bou
            if (whoIsAtLocationBackend(tempLocation).equals("")) {
                pieces.get(name).validMoves.add(tempLocation);
            } // if someone is there and they can be captured
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
                    } // if someone is there and they can be captured
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
                    } // if someone is there and they can be captured
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
                // if someone is there and they can be captured
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
            String captureRightCheckWho = whoIsAtLocationBackend(captureRight);
            if (!captureRightCheckWho.equals("")) {
                // if the pawn is white
                if (pieces.get(name).name.substring(0, 1).equals("w")) {
                    if (captureRightCheckWho.substring(0, 1).equals("2")) {
                        checkCaptureRight = true;

                    }
                }
                // if the pawn is black
                else if (pieces.get(name).name.substring(0, 1).equals("b")) {
                    if (captureRightCheckWho.substring(0, 1).equals("1")) {
                        checkCaptureRight = true;

                    }
                }

            }
        }

        String captureLeft = decreaseLetter(locationArr[0]) + numberlocation;

        boolean checkCaptureLeft = false;

        if (userInput.length() > 2) {
            String captureLeftCheckWho = whoIsAtLocationBackend(captureLeft);
            if (!captureLeftCheckWho.equals("")) {
                // if the pawn is white
                if (pieces.get(name).name.substring(0, 1).equals("w")) {
                    if (captureLeftCheckWho.substring(0, 1).equals("2")) {
                        checkCaptureLeft = true;

                    }
                } else if (pieces.get(name).name.substring(0, 1).equals("b")) {
                    // if the pawn is black
                    if (captureLeftCheckWho.substring(0, 1).equals("1")) {
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

    public void removePiece(String location) {
        if (userInput.length() > 2) {
            String whoGotCaptured = whoIsAtLocationBackend(location);
            if (!whoGotCaptured.equals("")) {
                whoGotCaptured = whoGotCaptured.substring(1);

                String letterOfWhoGotCaptured = whoIsAtLocation(pieces.get(whoGotCaptured).getLocation()).substring(1);
                switch (letterOfWhoGotCaptured) {
                    case "Q":
                        if (whoseMove.equals("white")) {
                            decreasePieceCount("blackQueens");
                        } else if (whoseMove.equals("black")) {
                            decreasePieceCount("whiteQueens");
                        }
                        break;
                    case "R":
                        if (whoseMove.equals("white")) {
                            decreasePieceCount("blackRooks");
                        } else if (whoseMove.equals("black")) {
                            decreasePieceCount("whiteRooks");
                        }
                        break;
                    case "N":

                        if (whoseMove.equals("white")) {
                            decreasePieceCount("blackKnights");
                        } else if (whoseMove.equals("black")) {
                            decreasePieceCount("whiteKnights");
                        }
                        break;
                    case "B":
                        if (whoseMove.equals("white")) {
                            decreasePieceCount("blackBishops");
                        } else if (whoseMove.equals("black")) {
                            decreasePieceCount("whiteBishops");
                        }
                        break;
                    case "P":

                        if (whoseMove.equals("white")) {
                            decreasePieceCount("blackPawns");
                        } else if (whoseMove.equals("black")) {
                            decreasePieceCount("whitePawns");
                        }
                        break;
                    default:
                        break;
                }
                pieces.remove(whoGotCaptured);
            }
        }

    }

    public board() {
        numberOfEachPiece.put("whitePawns", 8);
        numberOfEachPiece.put("blackPawns", 8);
        numberOfEachPiece.put("whiteRooks", 2);
        numberOfEachPiece.put("blackRooks", 2);
        numberOfEachPiece.put("whiteKnights", 2);
        numberOfEachPiece.put("blackKnights", 2);
        numberOfEachPiece.put("whiteBishops", 2);
        numberOfEachPiece.put("blackBishops", 2);
        numberOfEachPiece.put("whiteQueens", 1);
        numberOfEachPiece.put("blackQueens", 1);

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
        final String RESET = "\033[0m"; // Text Reset
        final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
        final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m"; // WHITE

        for (int i = 8; i > 0; i--) {
            for (int d = 1; d < 9; d++) {
                String letterLocation = getCharForNumber(d).toString();
                String tempLocation = letterLocation + i;
                String output = whoIsAtLocation(tempLocation);

                if (output.substring(0, 1).equals("B")) {

                    System.out.print(WHITE_BACKGROUND_BRIGHT + PURPLE_BOLD_BRIGHT + output + " " + RESET);

                } else {

                    System.out.print(output + " ");
                }

            }
            System.out.print("\n");
        }

    }

    public String whoIsAtLocation(String location) {

        for (int whatRook = 1, foundRooks = 0,
                whiteOrBlack = 0; foundRooks < numberOfEachPiece.get(whiteAndBlack[whiteOrBlack] + "Rooks")
                        || whatRook <= 2; whatRook++) {
            try {
                if (whiteOrBlack == 0) {
                    if (pieces.get("white rook - " + whatRook).location.equals(location)) {
                        return "WR";
                    }
                } else if (whiteOrBlack == 1) {
                    if (pieces.get("black rook - " + whatRook).location.equals(location)) {
                        return "BR";
                    }
                }

                foundRooks++;

                if (foundRooks < numberOfEachPiece.get(whiteAndBlack[0] + "Rooks")) {
                } else if (whiteOrBlack == 0) {
                    whiteOrBlack = 1;
                    whatRook = 0;
                    foundRooks = 0;

                }
            } catch (Exception e) {

            }
        }

        for (int whatPawn = 1, foundPawns = 0,
                whiteOrBlack = 0; foundPawns < numberOfEachPiece
                        .get(whiteAndBlack[whiteOrBlack] + "Pawns"); whatPawn++) {
            try {
                if (whiteOrBlack == 0) {
                    if (pieces.get("white pawn - " + whatPawn).location.equals(location)) {
                        return "WP";
                    }
                } else if (whiteOrBlack == 1) {
                    if (pieces.get("black pawn - " + whatPawn).location.equals(location)) {
                        return "BP";
                    }
                }

                foundPawns++;

                if (foundPawns < numberOfEachPiece.get(whiteAndBlack[0] + "Pawns")) {
                } else if (whiteOrBlack == 0) {
                    whiteOrBlack = 1;
                    whatPawn = 0;
                    foundPawns = 0;

                }
            } catch (Exception e) {

            }
        }

        for (int whatKnight = 1, foundKnights = 0,
                whiteOrBlack = 0; foundKnights < numberOfEachPiece.get(whiteAndBlack[whiteOrBlack] + "Knights")
                        || whatKnight <= 2; whatKnight++) {
            try {
                if (whiteOrBlack == 0) {
                    if (pieces.get("white knight - " + whatKnight).location.equals(location)) {
                        return "WN";
                    }
                } else if (whiteOrBlack == 1) {
                    if (pieces.get("black knight - " + whatKnight).location.equals(location)) {
                        return "BN";
                    }
                }
                foundKnights++;
                if (foundKnights < numberOfEachPiece.get(whiteAndBlack[0] + "Knights")) {
                } else if (whiteOrBlack == 0) {
                    whiteOrBlack = 1;
                    whatKnight = 0;
                    foundKnights = 0;

                }
            } catch (Exception e) {

            }
        }

        for (int whatBishop = 1, foundBishops = 0,
                whiteOrBlack = 0; foundBishops < numberOfEachPiece.get(whiteAndBlack[whiteOrBlack] + "Bishops")
                        || whatBishop <= 2; whatBishop++) {
            try {
                if (whiteOrBlack == 0) {
                    if (pieces.get("white bishop - " + whatBishop).location.equals(location)) {
                        return "WB";
                    }
                } else if (whiteOrBlack == 1) {
                    if (pieces.get("black bishop - " + whatBishop).location.equals(location)) {
                        return "BB";
                    }
                }
                foundBishops++;
                if (foundBishops < numberOfEachPiece.get(whiteAndBlack[0] + "Bishops")) {
                } else if (whiteOrBlack == 0) {
                    whiteOrBlack = 1;
                    whatBishop = 0;
                    foundBishops = 0;

                }
            } catch (Exception e) {

            }
        }

        for (int whatQueen = 1, foundQueens = 0,
                whiteOrBlack = 0; foundQueens < numberOfEachPiece.get(whiteAndBlack[whiteOrBlack] + "Queens")
                        || whatQueen <= 2; whatQueen++) {
            try {
                if (whiteOrBlack == 0) {
                    if (pieces.get("white queen - " + whatQueen).location.equals(location)) {
                        return "WQ";
                    }
                } else if (whiteOrBlack == 1) {
                    if (pieces.get("black queen - " + whatQueen).location.equals(location)) {
                        return "BQ";
                    }
                }
                foundQueens++;
                if (foundQueens < numberOfEachPiece.get(whiteAndBlack[0] + "Queens")) {
                } else if (whiteOrBlack == 0) {
                    whiteOrBlack = 1;
                    whatQueen = 0;
                    foundQueens = 0;

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
        for (int whatRook = 1, foundRooks = 0,
                whiteOrBlack = 0; foundRooks < numberOfEachPiece.get(whiteAndBlack[whiteOrBlack] + "Rooks")
                        || whatRook <= 2; whatRook++) {
            try {
                if (whiteOrBlack == 0) {
                    if (pieces.get("white rook - " + whatRook).location.equals(location)) {
                        return "1white rook - " + whatRook;
                    }
                } else if (whiteOrBlack == 1) {
                    if (pieces.get("black rook - " + whatRook).location.equals(location)) {
                        return "2black rook - " + whatRook;
                    }
                }

                foundRooks++;

                if (foundRooks < numberOfEachPiece.get(whiteAndBlack[0] + "Rooks")) {
                } else if (whiteOrBlack == 0) {
                    whiteOrBlack = 1;
                    whatRook = 0;
                    foundRooks = 0;

                }
            } catch (Exception e) {

            }
        }

        for (int whatPawn = 1, foundPawns = 0,
                whiteOrBlack = 0; foundPawns < numberOfEachPiece.get(whiteAndBlack[whiteOrBlack] + "Pawns")
                        && whatPawn <= 8; whatPawn++) {
            try {
                if (whiteOrBlack == 0) {
                    if (pieces.get("white pawn - " + whatPawn).location.equals(location)) {
                        return "1white pawn - " + whatPawn;
                    }
                } else if (whiteOrBlack == 1) {
                    if (pieces.get("black pawn - " + whatPawn).location.equals(location)) {
                        return "2black pawn - " + whatPawn;
                    }
                }

                foundPawns++;

                if (foundPawns < numberOfEachPiece.get(whiteAndBlack[0] + "Pawns")) {
                } else if (whiteOrBlack == 0) {
                    whiteOrBlack = 1;
                    whatPawn = 0;
                    foundPawns = 0;

                }
            } catch (Exception e) {

            }
        }

        for (int whatKnight = 1, foundKnights = 0,
                whiteOrBlack = 0; foundKnights < numberOfEachPiece.get(whiteAndBlack[whiteOrBlack] + "Knights")
                        || whatKnight <= 2; whatKnight++) {
            try {
                if (whiteOrBlack == 0) {
                    if (pieces.get("white knight - " + whatKnight).location.equals(location)) {
                        return "1white knight - " + whatKnight;
                    }
                } else if (whiteOrBlack == 1) {
                    if (pieces.get("black knight - " + whatKnight).location.equals(location)) {
                        return "2black knight - " + whatKnight;
                    }
                }
                foundKnights++;
                if (foundKnights < numberOfEachPiece.get(whiteAndBlack[0] + "Knights")) {
                } else if (whiteOrBlack == 0) {
                    whiteOrBlack = 1;
                    whatKnight = 0;
                    foundKnights = 0;

                }
            } catch (Exception e) {

            }
        }

        for (int whatBishop = 1, foundBishops = 0,
                whiteOrBlack = 0; foundBishops < numberOfEachPiece.get(whiteAndBlack[whiteOrBlack] + "Bishops")
                        || whatBishop <= 2; whatBishop++) {
            try {
                if (whiteOrBlack == 0) {
                    if (pieces.get("white bishop - " + whatBishop).location.equals(location)) {
                        return "1white bishop - " + whatBishop;
                    }
                } else if (whiteOrBlack == 1) {
                    if (pieces.get("black bishop - " + whatBishop).location.equals(location)) {
                        return "2black bishop - " + whatBishop;
                    }
                }
                foundBishops++;
                if (foundBishops < numberOfEachPiece.get(whiteAndBlack[0] + "Bishops")) {
                } else if (whiteOrBlack == 0) {
                    whiteOrBlack = 1;
                    whatBishop = 0;
                    foundBishops = 0;

                }
            } catch (Exception e) {

            }
        }

        for (int whatQueen = 1, foundQueens = 0,
                whiteOrBlack = 0; foundQueens < numberOfEachPiece.get(whiteAndBlack[whiteOrBlack] + "Queens")
                        || whatQueen <= 2; whatQueen++) {
            try {
                if (whiteOrBlack == 0) {
                    if (pieces.get("white queen - " + whatQueen).location.equals(location)) {
                        return "1white queen - " + whatQueen;
                    }
                } else if (whiteOrBlack == 1) {
                    if (pieces.get("black queen - " + whatQueen).location.equals(location)) {
                        return "2black queen - " + whatQueen;
                    }
                }
                foundQueens++;
                if (foundQueens < numberOfEachPiece.get(whiteAndBlack[0] + "Queens")) {
                } else if (whiteOrBlack == 0) {
                    whiteOrBlack = 1;
                    whatQueen = 0;
                    foundQueens = 0;

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