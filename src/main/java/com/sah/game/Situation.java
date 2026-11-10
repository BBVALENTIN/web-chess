package com.sah.game;

import com.sah.game.exceptions.InvalidMoveException;
import com.sah.game.gameenums.ColorType;
import com.sah.game.gameenums.Type;
import com.sah.game.records.CastlingRights;
import com.sah.game.records.Move;
import com.sah.game.records.Position;
import com.sah.game.records.ValidationResult;

import java.util.Optional;


public class Situation {
    private final MoveValidator validator;

    public Situation(MoveValidator validator) {
        this.validator = validator;
    }

    public boolean isInCheck(Board board, ColorType kingColor) {
        Position kingPosition;
        if(kingColor == ColorType.BLACK)
            kingPosition = board.blackKingPosition;
        else
            kingPosition = board.whiteKingPosition;
        for(int r = 0; r < 8; r++)
        {
            for(int c = 0; c < 8; c++) {
                Piece attacker = board.at(r, c);
                if(attacker == null || attacker.color == kingColor)
                    continue;
                if(board.canAttack(r, c, kingPosition.row(), kingPosition.col())) return true;
            }
        }
        return false;
    }

    public boolean IsCheckmate(boolean isCheck, Board board, ColorType sideToMove, CastlingRights rights)
    {
        return isCheck && !hasLegalMoves(board, sideToMove, rights);
    }

//    public boolean isStalemate(Board board, ColorType kingColor) {}

    public boolean isDrawBy50Moves(int halfMove) {
        return halfMove == 50;
    }

//    public boolean isDrawByRepetition(List<Long> positionHashes) {}
//    public boolean insufficientMatingMaterial() {}

    private boolean hasLegalMoves(Board board, ColorType sideToMove, CastlingRights rights) {
        for(int r = 0; r < 8; r++) {
            for(int c = 0; c < 8; c++) {
                Piece p = board.at(r, c);
                if(p == null || p.color != sideToMove) continue;

                for(int tr = 0; tr < 8; tr++)
                    for(int tc = 0; tc < 8; tc++) {
                        Move test = new Move(r, c, tr, tc, board.at(r, c).type, sideToMove, null); // change this

                        if(p.type == Type.PAWN && (tr == 0 || tr == 7)) {
                            test = new Move(r, c, tr, tc, Type.QUEEN, sideToMove, null);
                        }

                        try {
                            Optional<ValidationResult> result = validator.validate(board, test, rights);
                            if(result.isPresent()) return true;
                        } catch(InvalidMoveException e) {
                        }
                    }
            }
        }
        return false;
    }

}
