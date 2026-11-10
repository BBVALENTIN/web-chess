package com.sah.interfaces;

import com.sah.game.gameenums.Type;

public interface MoveFormatterInterface<T> {

     default char getCharFromTip(Type tip) {
        return switch (tip) {
            case PAWN -> 'p';
            case KNIGHT -> 'n';
            case BISHOP -> 'b';
            case ROOK -> 'r';
            case QUEEN -> 'q';
            case KING -> 'k';
            default -> ' ';
        };
    }

    String formatMove(T data);
}
