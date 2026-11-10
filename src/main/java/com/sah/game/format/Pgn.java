package com.sah.game.format;

import com.sah.game.Piece;
import com.sah.game.gameenums.Type;
import com.sah.game.records.PgnData;
import com.sah.interfaces.MoveFormatterInterface;

public class Pgn implements MoveFormatterInterface<PgnData> {

    @Override
    public String formatMove(PgnData req)
    {
        if (req.isCastling()) {
            if (req.move().fromCol() < req.move().targetCol())
                return "O-O";
            else
                return "O-O-O";
        }

        char pieceChar = Character.toUpperCase(getCharFromTip(req.move().pieceType()));

        char colChar = (char) ('a' + req.move().targetCol());
        int boardRow = 8 - req.move().targetRow();

        char fromColChar = (char) ('a' + req.move().fromCol());
        int fromBoardRow = 8 - req.move().fromRow();

        String disambiguation = "";
        if (req.move().pieceType() != Type.PAWN) {
            boolean sameFile = false;
            boolean sameRank = false;
            boolean ambiguous = false;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (i == req.move().fromRow() && j == req.move().fromCol())
                        continue;

                    Piece p = req.boardBefore().at(i, j); if (p == null)
                        continue;

                    if (p.color == req.move().pieceColor() && p.type == req.move().pieceType()
                            && req.boardBefore().canMove(i, j, req.move().targetRow(), req.move().targetCol())) {
                        ambiguous = true;
                        if (j == req.move().fromCol()) sameRank = false;
                        if (j == req.move().fromCol()) sameFile = true;
                        if (i == req.move().fromRow()) sameRank = true;
                    }
                }
            }

            if (ambiguous) {
                if (!sameFile) {
                    disambiguation = "" + fromColChar;
                } else if (!sameRank) {
                    disambiguation = "" + fromBoardRow;
                } else {
                    disambiguation = "" + fromColChar + fromBoardRow;
                }
            }
        }

        boolean isPawnDiagonalMove = req.move().pieceType() == Type.PAWN && req.move().fromCol() != req.move().targetCol();
        boolean isCapture = !req.boardBefore().isEmpty(req.move().targetRow(), req.move().targetCol()) || isPawnDiagonalMove;

        String notation;

        if (req.move().pieceType() == Type.PAWN) {
            if (isCapture) {
                notation = fromColChar + "x" + colChar + boardRow;
            } else {
                notation = "" + colChar + boardRow;
            }
        } else {
            if (isCapture) {
                notation = "" + pieceChar + disambiguation + "x" + colChar + boardRow;
            } else {
                notation = "" + pieceChar + disambiguation + colChar + boardRow;
            }
        }

        if (req.move().promotion() != null) {
            notation = notation + "=" + Character.toUpperCase(getCharFromTip(req.move().promotion().toType()));
        }

        if (req.isCheck() && !req.isCheckMate())
            notation = notation + "+";
        if (req.isCheckMate())
            notation = notation + "#";

        return notation;
    }


}
