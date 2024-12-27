package uk.ac.nott.cs.comp2013.froggergame.model.data;

public record LevelConfig(int totalEnds, int startX, int y, int gap, double speedFactor) {
    private static final int DEFAULT_TOTAL_ENDS = 5;
    private static final int DEFAULT_START_X = 4;
    private static final int DEFAULT_Y = 96;
    private static final int DEFAULT_GAP = 133;

    public static LevelConfig createDefault(double speedFactor) {
        return new LevelConfig(DEFAULT_TOTAL_ENDS, DEFAULT_START_X, DEFAULT_Y, DEFAULT_GAP, speedFactor);
    }
}

