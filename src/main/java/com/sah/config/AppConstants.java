package com.sah.config;

import com.sah.game.records.Position;

public final class AppConstants {
    private AppConstants() {}

    public static final int DEFAULT_PAGE = 0;
    public static final int POSTS_PER_PAGE = 30;

    public static final int LOBBY_CHARACTER_LIMIT = 5;

    public static final boolean NOT_RESIGNATION = false;
    public static final boolean RESIGNATION = true;

    public static final Position whiteKingStartingPosition = new Position(7, 4);
    public static final Position blackKingStartingPosition = new Position(0, 4);

    public static final String strWHITE_WIN = "1-0";
    public static final String strBLACK_WIN = "0-1";
    public static final String strDRAW = "1/2 - 1/2";
    public static final String strABORTED = "";

    public static final String STARTING_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
}
