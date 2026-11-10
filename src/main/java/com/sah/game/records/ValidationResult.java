package com.sah.game.records;

public record ValidationResult(Move move,
                               boolean isCapture,
                               boolean isCastling,
                               boolean isEnPassant,
                               boolean isPromotion) {
}
