package com.sah.game.records;
import com.sah.game.Board;

public record PgnData(Move move, Board boardBefore, boolean isCheck, boolean isCheckMate, boolean isCastling) {
}
