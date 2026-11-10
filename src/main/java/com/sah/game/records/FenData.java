package com.sah.game.records;

import com.sah.game.Board;
import com.sah.game.gameenums.ColorType;

public record FenData(Board board, ColorType turn, CastlingRights castlingRights, int halfMove, int fullMove) {}
